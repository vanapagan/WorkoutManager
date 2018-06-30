package com.palotech.pelflex.workout.pattern.step;

public class Step {

    private Mode mode;
    private int duration;

    public Step(Mode mode, int duration) {
        this.mode = mode;
        this.duration = duration;
    }

    public Mode getMode() {
        return mode;
    }

    public int getDuration() {
        return duration;
    }

    public enum Mode {
        FLEX,
        RELAX;
    }

}
