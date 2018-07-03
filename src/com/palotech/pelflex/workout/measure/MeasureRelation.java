package com.palotech.pelflex.workout.measure;

public class MeasureRelation {

    private Measure measure;
    private Measure counterMeasure;
    private Measure resetMeasure;

    public MeasureRelation(Measure measure, Measure counterMeasure, Measure resetMeasure) {
        this.measure = measure;
        this.counterMeasure = counterMeasure;
        this.resetMeasure = resetMeasure;
    }

}
