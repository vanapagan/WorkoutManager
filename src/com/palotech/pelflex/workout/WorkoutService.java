package com.palotech.pelflex.workout;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByValue;

public class WorkoutService {

    private static final Random RANDOM_GENERATOR = new Random();

    private static List<Workout> workoutList = new ArrayList<>();

    public static Workout getNewWorkoutThatIsNotPrevious(int userId, Workout.Variation previousVariation) {
        Predicate<Place> notPreviousVariation = p -> previousVariation != p.getName();
        return getNewWorkout(userId, notPreviousVariation);
    }

    public static Workout getNewWorkout(int userId, Predicate<Place>... filters) {
        Map<Workout.Variation, Long> countedWorkoutsMap = workoutList.stream().collect(Collectors.groupingBy(w -> w.getVariation(), Collectors.counting()));

        List<Place> sortedLeaderboard = new ArrayList<>();
        countedWorkoutsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> sortedLeaderboard.add(new Place(e.getKey(), e.getValue())));

        Predicate<Place> superFilter = combineFilters(filters);

        Optional<Place> nextPlaceOptional = sortedLeaderboard.stream().filter(superFilter).findFirst();
        Workout.Variation nextWorkoutVariation = nextPlaceOptional.isPresent() ? nextPlaceOptional.get().getName() : Workout.Variation.NORMAL;

        Workout w = composeNewWorkout(userId, nextWorkoutVariation);
        workoutList.add(w);

        return w;
    }

    public static Workout composeNewWorkout(int userId, Workout.Variation variation) {
        Workout lastWorkoutOpt = !workoutList.isEmpty() ? workoutList.stream().filter(w -> variation == w.getVariation()).collect(Collectors.toList()).get(workoutList.stream().filter(w -> variation == w.getVariation()).collect(Collectors.toList()).size() - 1) : null;
        Workout lastWorkout = lastWorkoutOpt != null ? lastWorkoutOpt : new Workout(userId, Workout.Variation.NORMAL, 56, 0.0d, 0.0d, 1, 56);

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

        System.out.println(lastWorkout.getId() + " " + maxDuration + " - " + duration + " - " + handicap + " percentage: " + incPercentage + " --- " + variation);
        return new Workout(userId, variation, duration, handicap, incPercentage, decPercentage, maxDuration);
    }

    public static <T> Predicate<T> combineFilters(Predicate<T>... predicates) {
        return predicates.length > 0 ? Stream.of(predicates).reduce(x -> true, Predicate::and) : x -> true;
    }

    public static int generateRandomInteger(int min, int max, int lastRandom) {
        int random = RANDOM_GENERATOR.nextInt((max - min) + 1) + min;
        return random != lastRandom ? random : generateRandomInteger(min, max, lastRandom);
    }

    public static List<Workout> getWorkoutList() {
        return workoutList;
    }

}
