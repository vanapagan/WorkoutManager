package com.palotech.pelflex.workout;

public class Place {

    private Workout.Variation name;
    private long noOfOccurs;

    public Place(Workout.Variation name, long noOfOccurs) {
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