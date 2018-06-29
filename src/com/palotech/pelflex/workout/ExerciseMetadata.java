package com.palotech.pelflex.workout;

public class ExerciseMetadata {

    private Workout.Variation variation;
    private double duration;
    private double maxDuration;
    private Pattern pattern;
    private double handicap;
    private double incPercentage;
    private double decPercentage;

    public ExerciseMetadata(Workout.Variation variation, double duration, double maxDuration, double handicap, double incPercentage, double decPercentage, int defaultDenominator, int min, int max) {
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
}
