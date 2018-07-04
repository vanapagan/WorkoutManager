package com.palotech.pelflex.workout.measure;

public class Remedy {

    private static int idCount;

    private int id;
    private double value1;
    private double value2;
    private double value3;

    public Remedy(double value1, double value2, double value3) {
        this.id = ++idCount;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public int getId() {
        return id;
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

}
