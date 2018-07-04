package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.builder.Builder;
import com.palotech.pelflex.workout.exercise.template.kegel.KegelTemplate;
import com.palotech.pelflex.workout.measure.Measure;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;

import java.util.ArrayList;
import java.util.List;

public abstract class ExerciseTemplate implements Accumulative {

    protected Variation variation;

    public ExerciseTemplate(Variation variation) {
        this.variation = variation;
    }

    public static List<Variation> getAvailableVariations() {
        List<Variation> list = new ArrayList();
        list.add(Variation.NORMAL);
        list.add(Variation.FAST);

        return list;
    }

    public static List<Exercise> getAvailableExerices() {
        List<Exercise> list = new ArrayList();
        list.add(Exercise.KEGEL);
        // TODO not implemented yet -> list.add(Exercise.REVERSE_KEGEL);
        // TODO not implemented yet -> list.add(Exercise.STRETCH);

        return list;
    }

    public static ExerciseTemplate generateExerciseTemplate(Exercise exercise, Variation variation) {
        ExerciseTemplate exerciseTemplate;
        if (exercise == Exercise.REVERSE_KEGEL) {
            exerciseTemplate = new KegelTemplate(variation);
        } else if (exercise == Exercise.STRETCH) {
            exerciseTemplate = new KegelTemplate(variation);
        } else if (exercise == Exercise.CUSTOM) {
            exerciseTemplate = new KegelTemplate(variation);
        } else {
            exerciseTemplate = new KegelTemplate(variation);
        }

        return exerciseTemplate.generateExerciseTemplate(variation);
    }

    public abstract Builder createBuilder(ExerciseTemplate template, Ledger ledger, Metadata lastMetadata);

    public abstract ExerciseTemplate generateExerciseTemplate(Variation variation);

    public Variation getVariation() {
        return variation;
    }

    public abstract Workout getDefaultWorkout();

    public abstract List<Variation> getVariationsList();

    public abstract Exercise getExercise();

    public abstract List<Measure> getMeasureList();

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
