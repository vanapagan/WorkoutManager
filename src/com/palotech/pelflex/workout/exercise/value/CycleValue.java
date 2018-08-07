package com.palotech.pelflex.workout.exercise.value;

public class CycleValue {

    protected String name;
    protected double value;
    protected double floor;
    protected double ceiling;
    private double multiplier;
    private double initialValue;

    public CycleValue(String name, double value, double multiplier, double floor, double ceiling, double initialValue) {
        this.name = name;
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
        return (value > 0 ? value : (floor + initialValue)) * multiplier;
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

    public String getName() {
        return name;
    }

    public double getFloor() {
        return floor;
    }

    public double getCeiling() {
        return ceiling;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public double getInitialValue() {
        return initialValue;
    }
}
