package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.workout.SuggestedExercise;
import com.palotech.pelflex.workout.SuggestedWorkout;
import com.palotech.pelflex.workout.Workout;

import java.util.ArrayList;
import java.util.List;

public abstract class ExerciseTemplate {

    protected Variation variation;

    public ExerciseTemplate(Variation variation) {
        this.variation = variation;
    }

    public static List<SuggestedWorkout> getAvailableExerices(int userId) {
        List<SuggestedWorkout> list = new ArrayList();
        list.add(new SuggestedWorkout(new SuggestedExercise(ExerciseTemplate.Exercise.KEGEL, Variation.NORMAL), 0));
        list.add(new SuggestedWorkout(new SuggestedExercise(ExerciseTemplate.Exercise.KEGEL, Variation.FAST), 0));
        // TODO not implemented yet -> list.add(new SuggestedWorkout(new SuggestedExercise(Exercise.REVERSE_KEGEL, Variation.NORMAL), 0));
        // TODO not implemented yet -> list.add(new SuggestedWorkout(new SuggestedExercise(Exercise.STRETCH, Variation.NORMAL), 0));

        return list;
    }

    public static ExerciseTemplate generateExerciseTemplate(SuggestedExercise suggestedExercise) {
        Exercise exercise = suggestedExercise.getExercise();
        Variation variation = suggestedExercise.getVariation();
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
