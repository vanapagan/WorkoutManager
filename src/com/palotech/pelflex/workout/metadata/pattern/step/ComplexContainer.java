package com.palotech.pelflex.workout.metadata.pattern.step;

public class ComplexContainer {

    private ComplexStep.Type type;
    private int duration;

    public ComplexContainer(ComplexStep.Type type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    public boolean tryToIncrease(int add, int max) {
        return (duration + add) <= max;
    }

    public boolean increase(int add, int max) {
        boolean greenLight = tryToIncrease(add, max);
        duration += greenLight ? add : 0;
        return greenLight;
    }

    public boolean tryToDecrease(int add, int min) {
        return (duration + add) >= min;
    }

    public boolean decrease(int add, int min) {
        boolean greenLight = tryToDecrease(add, min);
        duration -= greenLight ? add : 0;
        return greenLight;
    }

    public ComplexStep.Type getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ComplexContainer getCopyOf() {
        return new ComplexContainer(this.type, this.duration);
    }

    public void setType(ComplexStep.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.valueOf(duration);
    }
}
