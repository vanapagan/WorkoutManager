package com.palotech.pelflex.workout;

public class SuggestedWorkout {

    private Workout.Variation name;
    private long noOfOccurs;

    public SuggestedWorkout(Workout.Variation name, long noOfOccurs) {
        this.name = name;
        this.noOfOccurs = noOfOccurs;
    }

    public Workout.Variation getName() {
        return name;
    }

    public long getNoOfOccurs() {
        return noOfOccurs;
    }
}