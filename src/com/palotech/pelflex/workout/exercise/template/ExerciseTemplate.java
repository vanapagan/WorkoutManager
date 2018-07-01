package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.custom.Custom;
import com.palotech.pelflex.workout.exercise.template.kegel.Kegel;
import com.palotech.pelflex.workout.exercise.template.reversekegel.ReverseKegel;
import com.palotech.pelflex.workout.exercise.template.stretch.Stretch;
import com.palotech.pelflex.workout.exercise.template.value.CycleValue;

import java.util.ArrayList;
import java.util.List;

public abstract class ExerciseTemplate implements Incrementable {

    protected Variation variation;
    protected CycleValue durationIncCycleValue;
    protected CycleValue durationIncPercentageCycleValue;
    protected double durationIncMultiplier;
    protected double durationDecPercentage;

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
        ExerciseTemplate exerciseTemplate;
        if (exercise == Exercise.REVERSE_KEGEL) {
            exerciseTemplate = new ReverseKegel(variation);
        } else if (exercise == Exercise.STRETCH) {
            exerciseTemplate = new Stretch(variation);
        } else if (exercise == Exercise.CUSTOM) {
            exerciseTemplate = new Custom(variation);
        } else {
            exerciseTemplate = new Kegel(variation);
        }

        return exerciseTemplate.generateExerciseTemplate(variation);
    }

    public abstract ExerciseTemplate generateExerciseTemplate(Variation variation);

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
        STRETCH,
        CUSTOM
    }

    public enum Variation {
        NORMAL,
        FAST
    }
}
