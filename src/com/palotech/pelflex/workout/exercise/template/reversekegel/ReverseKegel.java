package com.palotech.pelflex.workout.exercise.template.reversekegel;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;
import com.palotech.pelflex.workout.measure.Measure;

import java.util.ArrayList;
import java.util.List;

public class ReverseKegel extends ExerciseTemplate {

    public ReverseKegel(Variation variation) {
        super(variation);
    }

    @Override
    public ExerciseTemplate generateExerciseTemplate(Variation variation) {
        return null;
    }

    @Override
    public CycleValue createDurationIncCycleValue(double value) {
        return null;
    }

    @Override
    public CycleValue createDurationIncPercentageCycleValue(double value) {
        return null;
    }

    @Override
    public PercentageCycleValue createDurationDecPercentageCycleValue(double value) {
        return null;
    }

    @Override
    public Workout getDefaultWorkout() {
        return null;
    }

    @Override
    public List<ExerciseTemplate.Variation> getVariationsList() {
        return new ArrayList<>();
    }

    @Override
    public Exercise getExercise() {
        return Exercise.REVERSE_KEGEL;
    }

    @Override
    public List<Measure> getMeasures() {
        return null;
    }

    @Override
    public Accumulator getDefaultAccumulator() {
        return null;
    }
}
