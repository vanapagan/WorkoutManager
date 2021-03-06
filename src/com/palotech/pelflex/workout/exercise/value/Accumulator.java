package com.palotech.pelflex.workout.exercise.value;

public class Accumulator {

    private double value;
    private double initalValue;
    private double floorValue;
    private double ceilingValue;
    private double multiplier;

    public Accumulator(double value, double initalValue, double floorValue, double ceilingValue, double multiplier) {
        this.value = value != 0 ? value : floorValue + initalValue;
        this.initalValue = initalValue;
        this.floorValue = floorValue;
        this.ceilingValue = ceilingValue;
        this.multiplier = multiplier;
    }

    public boolean isCeilingReached() {
        return value >= ceilingValue;
    }

    public void accumulate() {
        if (isCeilingReached()) {
            reset();
        } else {
            setValue(value * multiplier);
        }
    }

    public void reset() {
        setValue(floorValue + initalValue);
    }

    private void setValue(double newValue) {
        this.value = newValue;
    }

    public double getValue() {
        return value;
    }
}

