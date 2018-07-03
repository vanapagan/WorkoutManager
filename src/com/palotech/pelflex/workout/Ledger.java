package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.measure.Measure;

import java.util.List;

public class Ledger {

    private static int idCount;

    private int id;
    private ExerciseTemplate.Exercise exercise;
    private ExerciseTemplate.Variation variation;
    private List<Measure> measureList;

    public Ledger(ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation, List<Measure> measureList) {
        this.id = ++idCount;
        this.exercise = exercise;
        this.variation = variation;
        this.measureList = measureList;
    }

    public int getId() {
        return id;
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }

}
