package com.palotech.pelflex.workout;

public class PatternMetadata {

    private int denominator;
    private int min;
    private int max;

    public PatternMetadata(int denominator, int min, int max) {
        this.denominator = denominator;
        this.min = min;
        this.max = max;
    }

    public int getDenominator() {
        return denominator;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
