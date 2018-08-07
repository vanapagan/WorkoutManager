package com.palotech.pelflex.workout.metadata;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Difficulty {

    private double duration;
    private double maxDuration;

    public Difficulty(double duration, double maxDuration) {
        this.duration = duration;
        this.maxDuration = maxDuration;
    }

    public double getDuration() {
        return duration;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "Difficulty:" +
                " duration=" + formatter.format(duration) +
                " maxDuration=" + formatter.format(maxDuration);
    }

}
