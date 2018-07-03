package com.palotech.pelflex.workout.measure;

import com.palotech.pelflex.workout.Workout;

public abstract class Measure {

    private static int idCount;

    private int id;
    private String name;
    private Cumulator code;
    private int priority;
    private boolean increasing;
    private double value1;
    private double value2;
    private double value3;
    private int ttl;

    public Measure(String name, Cumulator code, int priority, boolean increasing, double value1, double value2, double value3, int ttl) {
        this.priority = priority;
        this.id = ++idCount;
        this.name = name;
        this.code = code;
        this.priority = priority;
        this.increasing = increasing;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.ttl = ttl;
    }

    public abstract void execute(Workout workout);

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cumulator getCode() {
        return code;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isIncreasing() {
        return increasing;
    }

    public double getValue1() {
        return value1;
    }

    public double getValue2() {
        return value2;
    }

    public double getValue3() {
        return value3;
    }

    public int getTtl() {
        return ttl;
    }

    public boolean isAlive() {
        return ttl > 0;
    }

    public void decreaseTtl() {
        if (isAlive()) {
            ttl--;
        }
    }

    private void setTtl(int ttl) {
        this.ttl = ttl;
    }



    public enum Cumulator {
        INCREASE_DURATION,
        DECREASE_DURATION,
        INCREASE_MAX_FLEX_PROPORTION,
        DECREASE_MAX_FLEX_PROPORTION,
        INCREASE_STEP_FLEX_PROPORTION,
        DECREASE_STEP_FLEX_PROPORTION,
        INCREASE_NUMBER_OF_STEPS,
        DECREASE_NUMBER_OF_STEPS,
        SET_FLEX_LESSER_THAN_EQUAL_RELAX_STEP_PROPORTION,
        SET_MAX_FLEX_PROPORTION_30_40_PERCENTAGE
    }

}
