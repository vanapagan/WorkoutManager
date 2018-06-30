package com.palotech.pelflex.workout;

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

    public static Workout getNewWorkoutThatIsNotPrevious(int userId, Workout.Variation previousVariation) {
        Predicate<SuggestedWorkout> notPreviousVariation = p -> previousVariation != p.getName();
        return composeNewWorkout(userId, notPreviousVariation);
    }

    public static Workout composeNewWorkout(int userId, Predicate<SuggestedWorkout>... filters) {
        Map<Workout.Variation, Long> countedWorkoutsMap = workoutList.stream().filter(w -> w.getUserId() == userId).collect(Collectors.groupingBy(w -> w.getMetadata().getVariation(), Collectors.counting()));

        List<SuggestedWorkout> sortedLeaderboard = new ArrayList<>();
        countedWorkoutsMap.entrySet().stream().forEachOrdered(e -> sortedLeaderboard.add(new SuggestedWorkout(e.getKey(), e.getValue())));

        List<Workout.Variation> variationList = getAvailableVariations(userId);
        List<SuggestedWorkout> missingVariationsList = new ArrayList<>();
        for (Workout.Variation v : variationList) {
            if (sortedLeaderboard.stream().noneMatch(p -> p.getName() == v)) {
                missingVariationsList.add(new SuggestedWorkout(v, 0));
            }
        }

        sortedLeaderboard.addAll(missingVariationsList);
        sortedLeaderboard.sort(Comparator.comparing(SuggestedWorkout::getNoOfOccurs));

        Predicate<SuggestedWorkout> superFilter = combineFilters(filters);

        Optional<SuggestedWorkout> nextPlaceOptional = sortedLeaderboard.stream().filter(superFilter).findFirst();
        Workout.Variation nextWorkoutVariation = nextPlaceOptional.isPresent() ? nextPlaceOptional.get().getName() : Workout.Variation.NORMAL;

        return composeWorkout(userId, nextWorkoutVariation);
    }

    public static Workout composeWorkout(int userId, Workout.Variation variation) {
        Difficulty difficulty = composeDifficulty(userId, variation);
        PatternMetadata patternMetadata = composePatternMetadata(userId, variation, difficulty);
        Pattern pattern = composePattern(patternMetadata);
        Metadata metadata = composeMetadata(variation, difficulty, pattern);

        return new Workout(userId, metadata);
    }

    public static Difficulty composeDifficulty(int userId, Workout.Variation variation) {
        Workout lastWorkout = getWorkout(userId, variation);
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

    public static PatternMetadata composePatternMetadata(int userId, Workout.Variation variation, Difficulty difficulty) {
        Workout lastWorkout = getWorkout(userId, variation);
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

    public static Metadata composeMetadata(Workout.Variation variation, Difficulty difficulty, Pattern pattern) {
        return new Metadata(variation, difficulty, pattern);
    }

    public static <T> Predicate<T> combineFilters(Predicate<T>... predicates) {
        return predicates.length > 0 ? Stream.of(predicates).reduce(x -> true, Predicate::and) : x -> true;
    }

    public static int generateRandomInteger(int min, int max, int lastRandom) {
        int random = RANDOM_GENERATOR.nextInt((max - min) + 1) + min;
        return random != lastRandom ? random : generateRandomInteger(min, max, lastRandom);
    }

    public static List<Workout.Variation> getAvailableVariations(int userId) {
        List<Workout.Variation> list = new ArrayList();
        list.add(Workout.Variation.NORMAL);
        list.add(Workout.Variation.FAST);

        return list;
    }

    public static Workout getWorkout(int userId, Workout.Variation variation) {
        Optional<Workout> lastWorkoutOpt = workoutList
                .stream()
                .filter(w -> variation == w.getMetadata().getVariation())
                .sorted(Comparator.comparing(Workout::getDate).reversed())
                .findFirst();
        return lastWorkoutOpt.isPresent() ? lastWorkoutOpt.get() : getDefaultWorkout(userId, variation);
    }

    public static Workout getDefaultWorkout(int userId, Workout.Variation variation) {
        return variation == Workout.Variation.NORMAL ? getDefaultNormalWorkout() : getDefaultFastWorkout();
    }

    private static Workout getDefaultNormalWorkout() {
        int globalDuration = 56;
        int userId = 123;
        Workout.Variation variation = Workout.Variation.NORMAL;
        double duration = globalDuration;
        double handicap = 0.0d;
        double incPercentage = 0.0d;
        double decPercentage = 0.01d;
        double maxDuration = globalDuration;
        int denominator = 8;
        int min = 4;
        int max = 10;

        Difficulty difficulty = new Difficulty(duration, maxDuration, handicap, incPercentage, decPercentage);

        int durationAsInt = new Double(duration).intValue();
        PatternMetadata patternMetadata = new PatternMetadata(durationAsInt, denominator, min, max);
        Pattern pattern = PatternManager.generatePattern(patternMetadata);
        Metadata metadata = new Metadata(variation, difficulty, pattern);

        return new Workout(userId, metadata);
    }

    private static Workout getDefaultFastWorkout() {
        int globalDuration = 30;
        int userId = 123;
        Workout.Variation variation = Workout.Variation.FAST;
        double duration = globalDuration;
        double handicap = 0.0d;
        double incPercentage = 0.0d;
        double decPercentage = 0.01d;
        double maxDuration = globalDuration;
        int denominator = 8;
        int min = 4;
        int max = 4;

        Difficulty difficulty = new Difficulty(duration, maxDuration, handicap, incPercentage, decPercentage);

        int durationAsInt = new Double(duration).intValue();
        PatternMetadata patternMetadata = new PatternMetadata(durationAsInt, denominator, min, max);
        Pattern pattern = PatternManager.generatePattern(patternMetadata);
        Metadata metadata = new Metadata(variation, difficulty, pattern);

        return new Workout(userId, metadata);
    }

    public static List<Workout> getWorkoutList() {
        return workoutList;
    }

}
