package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.workout.Workout;

import java.util.ArrayList;
import java.util.List;

public class Stretch extends ExerciseTemplate {

    public Stretch(Variation variation) {
        super(variation);
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

}
