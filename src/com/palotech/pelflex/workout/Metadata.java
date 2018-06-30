package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.pattern.Pattern;
import com.palotech.pelflex.workout.pattern.PatternManager;
import com.palotech.pelflex.workout.pattern.PatternMetadata;

public class Metadata {

    private Workout.Variation variation;
    private double duration;
    private double maxDuration;
    private Pattern pattern;
    private double handicap;
    private double incPercentage;
    private double decPercentage;

    public Metadata(Workout.Variation variation, double duration, double maxDuration, double handicap, double incPercentage, double decPercentage, int defaultDenominator, int min, int max) {
        this.variation = variation;
        this.duration = duration;
        this.maxDuration = maxDuration;
        this.pattern = PatternManager.generatePattern(new PatternMetadata(new Double(duration).intValue(), defaultDenominator, min, max));
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
