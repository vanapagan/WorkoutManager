package com.palotech.pelflex.workout.metadata;

public class Difficulty {

    private double maxDuration;
    private double handicap;
    private double incPercentage;
    private double decPercentage;

    public Difficulty(double maxDuration, double handicap, double incPercentage, double decPercentage) {
        this.maxDuration = maxDuration;
        this.handicap = handicap;
        this.incPercentage = incPercentage;
        this.decPercentage = decPercentage;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public double getHandicap() {
        return handicap;
    }

    public double getIncPercentage() {
        return incPercentage;
    }

    public double getDecPercentage() {
        return decPercentage;
    }
}
