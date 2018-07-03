package com.palotech.pelflex.workout.measure.cumulate;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.measure.Measure;

public abstract class Cumulation {

    private Measure measure;

    public Cumulation(Measure measure) {
        this.measure = measure;
    }

    public abstract void cumulate(Workout workout);

}
