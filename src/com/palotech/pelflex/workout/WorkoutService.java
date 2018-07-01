package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.template.suggested.SuggestedExercise;
import com.palotech.pelflex.workout.exercise.template.suggested.SuggestedVariation;
import com.palotech.pelflex.workout.exercise.template.value.CycleValue;
import com.palotech.pelflex.workout.exercise.template.value.PercentageCycleValue;
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

    private static List<Workout> workoutList = new ArrayList<>();

    public static Workout composeNewWorkout(int userId) {
        // TODO See siin on t2ielik spagett, et lausa h2bi on:
        ExerciseTemplate.Exercise nextExercise = getNextSuggestedExercise(userId);
        ExerciseTemplate.Variation nextVariation = getNextSuggestedVariation(userId, nextExercise);
        ExerciseTemplate exerciseTemplate = ExerciseTemplate.generateExerciseTemplate(nextExercise, nextVariation);

        return composeWorkout(userId, exerciseTemplate);
    }

    private static ExerciseTemplate.Variation getNextSuggestedVariation(int userId, ExerciseTemplate.Exercise exercise) {
        List<SuggestedVariation> sortedVariationsLeaderboard = new ArrayList<>();

        Map<ExerciseTemplate.Variation, Long> countedVariationsMap = workoutList
                .stream()
                .filter(w -> w.getUserId() == userId)
                .filter(w -> w.getMetadata().getExerciseTemplate().getExercise() == exercise)
                .map(w -> w.getMetadata().getExerciseTemplate().getVariation())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        countedVariationsMap.entrySet().stream().forEachOrdered(e -> sortedVariationsLeaderboard.add(new SuggestedVariation(e.getKey(), e.getValue())));

        List<ExerciseTemplate.Variation> variationsList = ExerciseTemplate.getAvailableVariations(userId);
        List<SuggestedVariation> missingVariationsList = new ArrayList<>();
        for (ExerciseTemplate.Variation v : variationsList) {
            if (sortedVariationsLeaderboard.stream().noneMatch(e -> e.getVariation() == v)) {
                missingVariationsList.add(new SuggestedVariation(v, 0));
            }
        }

        sortedVariationsLeaderboard.addAll(missingVariationsList);
        sortedVariationsLeaderboard.sort(Comparator.comparing(SuggestedVariation::getNoOfOccurs));

        // TODO not implemented yet -> Predicate<SuggestedExercise> superFilter = combineFilters(filters);
        Optional<SuggestedVariation> nextVariationOptional = sortedVariationsLeaderboard.stream().findFirst();

        return nextVariationOptional.isPresent() ? nextVariationOptional.get().getVariation() : ExerciseTemplate.Variation.NORMAL;
    }

    private static ExerciseTemplate.Exercise getNextSuggestedExercise(int userId) {
        List<SuggestedExercise> sortedExercisesLeaderboard = new ArrayList<>();

        // TODO grupperimine ei toimi 6igesti ja annab mitu korda j2rjest sama Workout-i
        Map<ExerciseTemplate.Exercise, Long> countedExercisesMap = workoutList
                .stream()
                .filter(w -> w.getUserId() == userId)
                .map(w -> w.getMetadata().getExerciseTemplate().getExercise())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        countedExercisesMap.entrySet().stream().forEachOrdered(e -> sortedExercisesLeaderboard.add(new SuggestedExercise(e.getKey(), e.getValue())));

        List<ExerciseTemplate.Exercise> exerciseList = ExerciseTemplate.getAvailableExerices(userId);
        List<SuggestedExercise> missingExercisesList = new ArrayList<>();
        for (ExerciseTemplate.Exercise s : exerciseList) {
            if (sortedExercisesLeaderboard.stream().noneMatch(e -> e.getExercise() == s)) {
                missingExercisesList.add(new SuggestedExercise(s, 0));
            }
        }

        sortedExercisesLeaderboard.addAll(missingExercisesList);
        sortedExercisesLeaderboard.sort(Comparator.comparing(SuggestedExercise::getNoOfOccurs));

        // TODO not implemented yet -> Predicate<SuggestedExercise> superFilter = combineFilters(filters);
        Optional<SuggestedExercise> nextExerciseOptional = sortedExercisesLeaderboard.stream().findFirst();

        return nextExerciseOptional.isPresent() ? nextExerciseOptional.get().getExercise() : ExerciseTemplate.Exercise.KEGEL;
    }

    public static Workout composeWorkout(int userId, ExerciseTemplate exerciseTemplate) {
        Difficulty difficulty = composeDifficulty(userId, exerciseTemplate);
        PatternMetadata patternMetadata = composePatternMetadata(userId, exerciseTemplate, difficulty);
        Pattern pattern = composePattern(patternMetadata);
        Metadata metadata = composeMetadata(exerciseTemplate, difficulty, pattern);

        return new Workout(userId, metadata);
    }

    public static Difficulty composeDifficulty(int userId, ExerciseTemplate exerciseTemplate) {
        // TODO getWorkout annab j2lle vale tulemuse ma arvan...
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

        CycleValue durationIncCycleValue = exerciseTemplate.createDurationIncCycleValue(lastHandicap);
        CycleValue durationIncPercentageCycleValue = exerciseTemplate.createDurationIncPercentageCycleValue(lastIncPercentage);
        PercentageCycleValue durationDecPercentageCycleValue = exerciseTemplate.createDurationDecPercentageCycleValue(lastDecPercentage);

        double cycleValue;
        boolean ceilingReached = durationIncCycleValue.isCeilingReached();
        if (ceilingReached) {
            cycleValue = durationIncPercentageCycleValue.setAndReturnNewValue();
            durationIncCycleValue.resetValue();
            incPercentage = cycleValue;
        } else {
            durationIncCycleValue.setAndReturnNewValue();
            cycleValue = durationDecPercentageCycleValue.setAndReturnNewValue();
            decPercentage = cycleValue;
        }

        handicap = durationIncCycleValue.getValue();

        int raiseOrLowerMultiplier = ceilingReached ? 1 : -1;
        double duration = maxDuration * (1.0d + raiseOrLowerMultiplier * cycleValue);

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

    public static Workout getWorkout(int userId, ExerciseTemplate exerciseTemplate) {
        Optional<Workout> lastWorkoutOpt = workoutList
                .stream()
                .filter(w -> w.getUserId() == userId)
                .filter(w -> w.getMetadata().getExerciseTemplate().getExercise() == exerciseTemplate.getExercise())
                .filter(w -> w.getMetadata().getExerciseTemplate().getVariation() == exerciseTemplate.getVariation())
                //.sorted(Comparator.comparing(Workout::getDate).reversed()) // TODO Kui teha j2rjest palju objekte, siis ta loeb kuup2evad v6rdseks ja esitleb mitu korda yhte Workouti, kui viimast
                .sorted(Comparator.comparing(Workout::getId).reversed())
                .findFirst();
        return lastWorkoutOpt.isPresent() ? lastWorkoutOpt.get() : exerciseTemplate.getDefaultWorkout();
    }

    public static List<Workout> getWorkoutList() {
        return workoutList;
    }

}
