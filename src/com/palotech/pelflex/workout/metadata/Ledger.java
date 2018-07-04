package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.measure.Measure;

import java.util.List;

public class Ledger {

    private static int idCount;

    private int id;
    private Accumulator accumulator;
    private ExerciseTemplate.Exercise exercise;
    private ExerciseTemplate.Variation variation;
    private List<Measure> measureList;

    public Ledger(ExerciseTemplate exerciseTemplate) {
        this.id = ++idCount;
        this.accumulator = exerciseTemplate.getDefaultAccumulator();
        this.exercise = exerciseTemplate.getExercise();
        this.variation = exerciseTemplate.getVariation();
        this.measureList = exerciseTemplate.getMeasureList();
    }

    public int getId() {
        return id;
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public Accumulator getAccumulator() {
        return accumulator;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }

    public boolean contains(Measure.Group group) {
        return measureList.stream().anyMatch(m -> m.getGroup() == group);
    }

}
