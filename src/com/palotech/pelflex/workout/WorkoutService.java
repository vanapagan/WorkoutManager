package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.builder.Builder;
import com.palotech.pelflex.workout.exercise.suggested.SuggestedExercise;
import com.palotech.pelflex.workout.exercise.suggested.SuggestedVariation;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.LedgerManager;
import com.palotech.pelflex.workout.metadata.Metadata;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorkoutService {

    private static List<Workout> workoutList = new ArrayList<>();

    public static Workout composeNewWorkout() {
        ExerciseTemplate template = getNextSuggestedWorkoutTemplate();

        Ledger ledger = LedgerManager.getLedger(template);
        Workout lastWorkout = getWorkout(template);
        Metadata lastMetadata = lastWorkout.getMetadata();

        Builder builder = template.createBuilder(template, ledger, lastMetadata);

        return builder.createWorkout();
    }

    private static ExerciseTemplate getNextSuggestedWorkoutTemplate() {
        ExerciseTemplate.Exercise nextExercise = getNextSuggestedExercise();
        // TODO We dont want FAST kegels at the moment - only NORMAL, for debugging purposes
        // ExerciseTemplate.Variation nextVariation = getNextSuggestedVariation(nextExercise);
        ExerciseTemplate.Variation nextVariation = ExerciseTemplate.Variation.NORMAL;

        return ExerciseTemplate.generateExerciseTemplate(nextExercise, nextVariation);
    }

    private static ExerciseTemplate.Variation getNextSuggestedVariation(ExerciseTemplate.Exercise exercise) {
        List<SuggestedVariation> sortedVariationsLeaderboard = new ArrayList<>();

        Map<ExerciseTemplate.Variation, Long> countedVariationsMap = workoutList
                .stream()
                .filter(w -> w.getMetadata().getExercise() == exercise)
                .map(w -> w.getMetadata().getVariation())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        countedVariationsMap.entrySet().stream().forEachOrdered(e -> sortedVariationsLeaderboard.add(new SuggestedVariation(e.getKey(), e.getValue())));

        List<ExerciseTemplate.Variation> variationsList = ExerciseTemplate.getAvailableVariations();
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

    private static ExerciseTemplate.Exercise getNextSuggestedExercise() {
        List<SuggestedExercise> sortedExercisesLeaderboard = new ArrayList<>();

        // TODO grupperimine ei toimi 6igesti ja annab mitu korda j2rjest sama Workout-i
        Map<ExerciseTemplate.Exercise, Long> countedExercisesMap = workoutList
                .stream()
                .map(w -> w.getMetadata().getExercise())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        countedExercisesMap.entrySet().stream().forEachOrdered(e -> sortedExercisesLeaderboard.add(new SuggestedExercise(e.getKey(), e.getValue())));

        List<ExerciseTemplate.Exercise> exerciseList = ExerciseTemplate.getAvailableExerices();
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

    public static <T> Predicate<T> combineFilters(Predicate<ExerciseTemplate>[] predicates) {
        return predicates.length > 0 ? (Predicate<T>) Stream.of(predicates).reduce(x -> true, Predicate::and) : x -> true;
    }

    public static Workout getWorkout(ExerciseTemplate exerciseTemplate) {
        Optional<Workout> lastWorkoutOpt = workoutList
                .stream()
                .filter(w -> w.getMetadata().getExercise() == exerciseTemplate.getExercise())
                .filter(w -> w.getMetadata().getVariation() == exerciseTemplate.getVariation())
                //.sorted(Comparator.comparing(Workout::getDate).reversed()) // TODO Kui teha j2rjest palju objekte, siis ta loeb kuup2evad v6rdseks ja esitleb mitu korda yhte Workouti, kui viimast
                .sorted(Comparator.comparing(Workout::getId).reversed())
                .findFirst();
        return lastWorkoutOpt.isPresent() ? lastWorkoutOpt.get() : exerciseTemplate.getDefaultWorkout();
    }

    public static List<Workout> getWorkoutList() {
        return workoutList;
    }

}
