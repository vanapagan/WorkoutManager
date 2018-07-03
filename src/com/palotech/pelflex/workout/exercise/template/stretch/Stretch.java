package com.palotech.pelflex.workout.exercise.template.stretch;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;
import com.palotech.pelflex.workout.measure.Measure;

import java.util.ArrayList;
import java.util.List;

public class Stretch extends ExerciseTemplate {

    public Stretch(Variation variation) {
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
    public List<Variation> getVariationsList() {
        return new ArrayList<>();
    }

    @Override
    public Exercise getExercise() {
        return Exercise.STRETCH;
    }

    @Override
    public int calculateXpReward(Workout workout) {
        return 0;
    }

    @Override
    public List<Measure> getMeasures() {
        return null;
    }
}
