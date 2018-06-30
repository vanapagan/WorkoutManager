package com.palotech.pelflex.workout.metadata;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Difficulty {

    private double duration;
    private double maxDuration;
    private double handicap;
    private double incPercentage;
    private double decPercentage;

    public Difficulty(double duration, double maxDuration, double handicap, double incPercentage, double decPercentage) {
        this.duration = duration;
        this.maxDuration = maxDuration;
        this.handicap = handicap;
        this.incPercentage = incPercentage;
        this.decPercentage = decPercentage;
    }

    public double getDuration() {
        return duration;
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

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "Difficulty:" +
                " duration=" + formatter.format(duration) +
                " maxDuration=" + formatter.format(maxDuration) +
                " handicap=" + formatter.format(handicap) +
                " incPercentage=" + formatter.format(incPercentage) +
                " decPercentage=" + formatter.format(decPercentage);
    }

}
