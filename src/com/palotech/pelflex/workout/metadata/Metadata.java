package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;

public class Metadata {

    private Workout.Variation variation;
    private double maxDuration;
    private Pattern pattern;
    private double handicap;
    private double incPercentage;
    private double decPercentage;

    public Metadata(Workout.Variation variation, double maxDuration, double handicap, double incPercentage, double decPercentage, Pattern pattern) {
        this.variation = variation;
        this.maxDuration = maxDuration;
        this.pattern = pattern;
        this.handicap = handicap;
        this.incPercentage = incPercentage;
        this.decPercentage = decPercentage;
    }

    public Workout.Variation getVariation() {
        return variation;
    }

    public Pattern getPattern() {
        return pattern;
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

    public enum Accumulator {
        INCREASE_WORKOUT_DURATION,
        DECREASE_WORKOUT_DURATION,
        INCREASE_FLEX_TIME,
        DECREASE_FLEX_TIME,
        INCREASE_FLEX_MAX_STEP_SIZE,
        DECREASE_FLEX_MAX_STEP_SIZE,
        INCREASE_MAX_FLEX_QUANTITY,
        DECREASE_MAX_FLEX_QUANTITY,
        INCREASE_STEP_DENOMINATOR,
        DECREASE_STEP_DENOMINATOR
    }

}
