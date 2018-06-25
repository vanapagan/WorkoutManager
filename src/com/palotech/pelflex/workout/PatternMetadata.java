package com.palotech.pelflex.workout;

public class PatternMetadata {

    private int duration;
    private int denominator;
    private int min;
    private int max;

    public PatternMetadata(int duration, int defaultDenominator, int min, int max) {
        this.duration = duration;
        this.denominator = defaultDenominator;
        this.min = min;
        this.max = max;
    }

    public int getDuration() {
        return duration;
    }

    public int getDenominator() {
        return calculateSufficientDenominator(duration, denominator, min);
    }

    private int calculateSufficientDenominator(int duration, int denominator, int minStepSize) {
        return denominator > 0 ? duration / denominator >= minStepSize ? denominator : calculateSufficientDenominator(duration, --denominator, minStepSize) : 1;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
