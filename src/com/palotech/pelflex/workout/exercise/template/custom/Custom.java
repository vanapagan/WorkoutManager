package com.palotech.pelflex.workout.exercise.template.custom;

import com.palotech.pelflex.progress.Progress;
import com.palotech.pelflex.progress.reward.Reward;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;

import java.util.List;

public class Custom extends ExerciseTemplate {

    public Custom(Variation variation) {
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
        return null;
    }

    @Override
    public Exercise getExercise() {
        return null;
    }

    @Override
    public Reward calculateReward(Progress progress) {
        return null;
    }
}
