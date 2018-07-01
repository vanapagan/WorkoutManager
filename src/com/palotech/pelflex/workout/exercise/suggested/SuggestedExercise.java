package com.palotech.pelflex.workout.exercise.suggested;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

public class SuggestedExercise {

    private ExerciseTemplate.Exercise exercise;
    private long noOfOccurs;

    public SuggestedExercise(ExerciseTemplate.Exercise exercise, long noOfOccurs) {
        this.exercise = exercise;
        this.noOfOccurs = noOfOccurs;
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public long getNoOfOccurs() {
        return noOfOccurs;
    }

}