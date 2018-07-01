package com.palotech.pelflex.workout.exercise.template;

public class CycleValue {

    protected double value;
    protected double floor;
    protected double ceiling;
    private double multiplier;
    private double initialValue;

    public CycleValue(double value, double multiplier, double floor, double ceiling, double initialValue) {
        this.value = value;
        this.multiplier = multiplier;
        this.floor = floor;
        this.ceiling = ceiling;
        this.initialValue = initialValue;
    }

    public double setAndReturnNewValue() {
        setNewValue();
        return value;
    }

    public void setNewValue() {
        value = isCeilingReached() ? initialValue : getIncreasedValue();
    }

    public double getIncreasedValue() {
        return (value != 0 ? value : floor + initialValue) * multiplier;
    }

    public boolean isCeilingReached() {
        return value >= ceiling;
    }

    public void resetValue() {
        this.value = floor + initialValue;
    }

    public double getValue() {
        return value;
    }
}
