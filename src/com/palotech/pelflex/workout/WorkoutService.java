package com.palotech.pelflex.workout;

import com.sun.deploy.security.ValidationState;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByValue;

public class WorkoutService {

    private static final Random RANDOM_GENERATOR = new Random();

    private static List<Workout> workoutList = new ArrayList<>();

    public static Workout getNewWorkoutThatIsNotPrevious(int userId, Workout.Variation previousVariation) {
        Predicate<Exercise> notPreviousVariation = p -> previousVariation != p.getName();
        return getNewWorkout(userId, notPreviousVariation);
    }

    public static Workout getNewWorkout(int userId, Predicate<Exercise>... filters) {
        Map<Workout.Variation, Long> countedWorkoutsMap = workoutList.stream().filter(w -> w.getUserId() == userId).collect(Collectors.groupingBy(w -> w.getVariation(), Collectors.counting()));

        List<Exercise> sortedLeaderboard = new ArrayList<>();
        countedWorkoutsMap.entrySet().stream().forEachOrdered(e -> sortedLeaderboard.add(new Exercise(e.getKey(), e.getValue())));

        List<Workout.Variation> variationList = getAvailableVariations(userId);
        List<Exercise> missingVariationsList = new ArrayList<>();
        for (Workout.Variation v : variationList) {
            if (sortedLeaderboard.stream().noneMatch(p -> p.getName() == v)) {
                missingVariationsList.add(new Exercise(v, 0));
            }
        }

        sortedLeaderboard.addAll(missingVariationsList);
        sortedLeaderboard.sort(Comparator.comparing(Exercise::getNoOfOccurs));

        Predicate<Exercise> superFilter = combineFilters(filters);

        Optional<Exercise> nextPlaceOptional = sortedLeaderboard.stream().filter(superFilter).findFirst();
        Workout.Variation nextWorkoutVariation = nextPlaceOptional.isPresent() ? nextPlaceOptional.get().getName() : Workout.Variation.NORMAL;

        PatternMetadata metadata = getDefaultPatternMetadata(userId, nextWorkoutVariation);
        Workout w = composeNewWorkout(userId, nextWorkoutVariation, metadata);

        return w;
    }

    public static Workout composeNewWorkout(int userId, Workout.Variation variation, PatternMetadata metadata) {
        Workout lastWorkout = getWorkout(userId, variation);

        double lastHandicap = lastWorkout.getHandicap();
        double lastIncPercentage = lastWorkout.getIncPercentage();
        double lastDecPercentage = lastWorkout.getDecPercentage();

        double maxDuration = lastWorkout.getMaxDuration();
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

        PatternManager patternManager = new PatternManager(new Double(duration).intValue(), metadata);
        Pattern pattern = patternManager.generatePattern();

        System.out.println(lastWorkout.getId() + " " + maxDuration + " - " + duration + " - " + handicap + " percentage: " + incPercentage + " --- " + variation);
        return new Workout(userId, variation, duration, pattern, handicap, incPercentage, decPercentage, maxDuration);
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
                .filter(w -> variation == w.getVariation())
                .sorted(Comparator.comparing(Workout::getDate).reversed())
                .findFirst();
        return lastWorkoutOpt.isPresent() ? lastWorkoutOpt.get() : getDefaultWorkout(userId, variation);
    }

    public static Workout getDefaultWorkout(int userId, Workout.Variation variation) {
        return variation == Workout.Variation.NORMAL ?
                new Workout(userId, Workout.Variation.NORMAL, 56, new PatternManager(56, getDefaultPatternMetadata(userId, variation)).generatePattern(),0.0d, 0.0d, 1, 56)
                :
                new Workout(userId, Workout.Variation.FAST, 30, new PatternManager(28, getDefaultPatternMetadata(userId, variation)).generatePattern(), 0.0, 0.0, 0.01d, 30);
    }

    public static PatternMetadata getDefaultPatternMetadata(int userId, Workout.Variation variation) {
        return variation == Workout.Variation.NORMAL ?
                new PatternMetadata(8, 4, 10)
                :
                new PatternMetadata(5, 3, 4);
    }

    public static List<Workout> getWorkoutList() {
        return workoutList;
    }

}
