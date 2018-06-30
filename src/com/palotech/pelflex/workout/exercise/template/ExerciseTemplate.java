package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.workout.Workout;

import java.util.ArrayList;
import java.util.List;

public abstract class ExerciseTemplate {

    protected Variation variation;

    public ExerciseTemplate(Variation variation) {
        this.variation = variation;
    }

    public static List<Variation> getAvailableVariations(int userId) {
        List<Variation> list = new ArrayList();
        list.add(Variation.NORMAL);
        list.add(Variation.FAST);

        return list;
    }

    public static List<Exercise> getAvailableExerices(int userId) {
        List<Exercise> list = new ArrayList();
        list.add(Exercise.KEGEL);
        // TODO not implemented yet -> list.add(Exercise.REVERSE_KEGEL);
        // TODO not implemented yet -> list.add(Exercise.STRETCH);

        return list;
    }

    public static ExerciseTemplate generateExerciseTemplate(Exercise exercise, Variation variation) {
        if (exercise == Exercise.REVERSE_KEGEL) {
            return new ReverseKegel(variation);
        } else if (exercise == Exercise.STRETCH) {
            return new Stretch(variation);
        } else {
            return new Kegel(variation);
        }
    }

    public Variation getVariation() {
        return variation;
    }

    public abstract Workout getDefaultWorkout();

    public abstract List<Variation> getVariationsList();

    public abstract Exercise getExercise();

    @Override
    public String toString() {
        return getExercise() + " " + variation;
    }

    public enum Exercise {
        KEGEL,
        REVERSE_KEGEL,
        STRETCH
    }

    public enum Variation {
        NORMAL,
        FAST
    }
}
