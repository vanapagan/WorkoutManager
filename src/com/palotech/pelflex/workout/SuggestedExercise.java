package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

public class SuggestedExercise {

    private ExerciseTemplate.Exercise exercise;
    private ExerciseTemplate.Variation variation;

    public SuggestedExercise(ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation) {
        this.exercise = exercise;
        this.variation = variation;
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

}
