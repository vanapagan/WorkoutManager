package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.template.Kegel;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.feedback.FeedbackService;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorkoutService {

    private static final Random RANDOM_GENERATOR = new Random();

    private static List<Workout> workoutList = new ArrayList<>();

    public static Workout getNewWorkoutThatIsNotPrevious(int userId, ExerciseTemplate previousExerciseTemplate) {
        Predicate<ExerciseTemplate> notPreviousExerciseTemplate = p -> previousExerciseTemplate.getExercise() != p.getExercise() && previousExerciseTemplate.getVariation() != p.getVariation();
        return composeNewWorkout(userId, notPreviousExerciseTemplate);
    }

    public static Workout composeNewWorkout(int userId, Predicate<ExerciseTemplate>... filters) {
        // TODO 1) Esmalt leiame k6ige v2hem sooritatud harjutuse
        ExerciseTemplate nextExercise = getNextSuggestedExercise(userId, filters);

        return composeWorkout(userId, nextExercise);
    }

    private static ExerciseTemplate getNextSuggestedExercise(int userId, Predicate<ExerciseTemplate>... filters) {
        List<SuggestedWorkout> sortedExercisesLeaderboard = new ArrayList<>();
        Map<SuggestedExercise, Long> countedWorkoutsMap = workoutList
                .stream()
                .filter(w -> w.getUserId() == userId)
                .map(w -> new SuggestedExercise(w.getMetadata().getExerciseTemplate().getExercise(), w.getMetadata().getExerciseTemplate().getVariation()))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        countedWorkoutsMap.entrySet().stream().forEachOrdered(e -> sortedExercisesLeaderboard.add(new SuggestedWorkout(e.getKey(), e.getValue())));

        List<SuggestedWorkout> exerciseList = ExerciseTemplate.getAvailableExerices(userId);
        List<SuggestedWorkout> missingExercisesList = new ArrayList<>();
        for (SuggestedWorkout s : exerciseList) {
            if (sortedExercisesLeaderboard.stream().noneMatch(e -> e.getSuggestedExercise().getExercise() == s.getSuggestedExercise().getExercise() && e.getSuggestedExercise().getVariation() == s.getSuggestedExercise().getVariation())) {
                missingExercisesList.add(s);
            }
        }

        sortedExercisesLeaderboard.addAll(missingExercisesList);
        sortedExercisesLeaderboard.sort(Comparator.comparing(SuggestedWorkout::getNoOfOccurs));

        Predicate<SuggestedWorkout> superFilter = combineFilters(filters);
        Optional<SuggestedWorkout> nextExerciseOptional = sortedExercisesLeaderboard.stream().filter(superFilter).findFirst();

        return nextExerciseOptional.isPresent() ? ExerciseTemplate.generateExerciseTemplate(nextExerciseOptional.get().getSuggestedExercise()) : new Kegel(ExerciseTemplate.Variation.NORMAL);
    }

    public static Workout composeWorkout(int userId, ExerciseTemplate exerciseTemplate) {
        Difficulty difficulty = composeDifficulty(userId, exerciseTemplate);
        PatternMetadata patternMetadata = composePatternMetadata(userId, exerciseTemplate, difficulty);
        Pattern pattern = composePattern(patternMetadata);
        Metadata metadata = composeMetadata(exerciseTemplate, difficulty, pattern);

        return new Workout(userId, metadata);
    }

    public static Difficulty composeDifficulty(int userId, ExerciseTemplate exerciseTemplate) {
        Workout lastWorkout = getWorkout(userId, exerciseTemplate);
        Metadata lastMetadata = lastWorkout.getMetadata();

        double lastHandicap = lastMetadata.getDifficulty().getHandicap();
        double lastIncPercentage = lastMetadata.getDifficulty().getIncPercentage();
        double lastDecPercentage = lastMetadata.getDifficulty().getDecPercentage();
        double maxDuration = lastMetadata.getDifficulty().getMaxDuration();
        double handicap = lastHandicap;

        double incPercentage = lastIncPercentage;
        double decPercentage = lastDecPercentage;
        double userFeedbackCoef = FeedbackService.getUserFeedbackCoefficient(lastWorkout.getId());
        maxDuration = maxDuration * (1.0d + userFeedbackCoef);

        double duration;
        if (lastHandicap >= 0.90d) {
            incPercentage = lastIncPercentage >= 0.04d ? 0.0d : (lastIncPercentage == 0.0d ? 0.008d : lastIncPercentage) * 1.50d;
            duration = maxDuration * (1.0d + incPercentage);
            handicap = 0;
        } else {
            decPercentage = generateRandomInteger(1, 10, (int) (lastDecPercentage * 100));
            duration = maxDuration * (1.0d - decPercentage / 100);
            handicap = handicap == 0.0d ? 0.10d : handicap;
            handicap *= 1.19d;
        }

        maxDuration = duration > maxDuration ? duration : maxDuration;
        return new Difficulty(duration, maxDuration, handicap, incPercentage, decPercentage);
    }

    public static PatternMetadata composePatternMetadata(int userId, ExerciseTemplate exerciseTemplate, Difficulty difficulty) {
        Workout lastWorkout = getWorkout(userId, exerciseTemplate);
        int durationAsInt = new Double(difficulty.getDuration()).intValue();

        // TODO Kuna meil on nyyd loodava Workout-i oodatav raskusaste teada, siis me saame oma mustrit ka vastavalt selle j2rgi korrigeerida
        // TODO -> vastavalt siis nt denominaatorit suurendada v6i v2hendada, v6i hoopiski min/max-i suurendada/v2hendada
        PatternMetadata lastPatternMetadata = lastWorkout.getMetadata().getPattern().getPatternMetadata();
        int lastDenominator = lastPatternMetadata.getDenominator();
        int lastMin = lastPatternMetadata.getMin();
        int lastMax = lastPatternMetadata.getMax();

        return new PatternMetadata(durationAsInt, lastDenominator, lastMin, lastMax);
    }

    public static Pattern composePattern(PatternMetadata patternMetadata) {
        return PatternManager.generatePattern(patternMetadata);
    }

    public static Metadata composeMetadata(ExerciseTemplate exerciseTemplate, Difficulty difficulty, Pattern pattern) {
        return new Metadata(exerciseTemplate, difficulty, pattern);
    }

    public static <T> Predicate<T> combineFilters(Predicate<ExerciseTemplate>[] predicates) {
        return predicates.length > 0 ? (Predicate<T>) Stream.of(predicates).reduce(x -> true, Predicate::and) : x -> true;
    }

    public static int generateRandomInteger(int min, int max, int lastRandom) {
        int random = RANDOM_GENERATOR.nextInt((max - min) + 1) + min;
        return random != lastRandom ? random : generateRandomInteger(min, max, lastRandom);
    }

    public static Workout getWorkout(int userId, ExerciseTemplate exerciseTemplate) {
        Optional<Workout> lastWorkoutOpt = workoutList
                .stream()
                .filter(w -> w.getUserId() == userId)
                .filter(w -> w.getMetadata().getExerciseTemplate().getExercise() == exerciseTemplate.getExercise())
                .filter(w -> w.getMetadata().getExerciseTemplate().getVariation() == exerciseTemplate.getVariation())
                .sorted(Comparator.comparing(Workout::getDate).reversed())
                .findFirst();
        return lastWorkoutOpt.isPresent() ? lastWorkoutOpt.get() : exerciseTemplate.getDefaultWorkout();
    }

    public static List<Workout> getWorkoutList() {
        return workoutList;
    }

}
