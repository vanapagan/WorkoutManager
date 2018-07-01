package com.palotech.pelflex.workout.exercise.template;

import java.util.Random;

public class PercentageCycleValue extends CycleValue {

    private static final Random RANDOM_GENERATOR = new Random();

    public PercentageCycleValue(double value, double multiplier, double floor, double ceiling, double initialValue) {
        super(value, multiplier, floor, ceiling, initialValue);
    }

    public static double generateRandomDouble(double min, double max, double lastRandom) {
        double random = min + (max - min) * RANDOM_GENERATOR.nextDouble();
        return random != lastRandom ? random : generateRandomDouble(min, max, lastRandom);
    }

    @Override
    public double setAndReturnNewValue() {
        setNewValue();
        return value;
    }

    @Override
    public void setNewValue() {
        value = generateRandomDouble(floor, ceiling, value);
    }

}
