package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.workout.Workout;

import java.util.ArrayList;
import java.util.List;

public class ReverseKegel extends ExerciseTemplate {

    public ReverseKegel(Variation variation) {
        super(variation);
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

}
