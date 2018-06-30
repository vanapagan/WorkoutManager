package com.palotech.pelflex.workout;

public class SuggestedWorkout {

    private SuggestedExercise suggestedExercise;
    private long noOfOccurs;

    public SuggestedWorkout(SuggestedExercise suggestedExercise, long noOfOccurs) {
        this.suggestedExercise = suggestedExercise;
        this.noOfOccurs = noOfOccurs;
    }

    public SuggestedExercise getSuggestedExercise() {
        return suggestedExercise;
    }

    public long getNoOfOccurs() {
        return noOfOccurs;
    }
}