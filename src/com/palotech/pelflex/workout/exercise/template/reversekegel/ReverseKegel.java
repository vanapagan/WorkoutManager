package com.palotech.pelflex.workout.exercise.template.reversekegel;

import com.palotech.pelflex.progress.Progress;
import com.palotech.pelflex.progress.reward.Reward;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;

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
    public Reward calculateReward(Progress progress) {
        return null;
    }
}
