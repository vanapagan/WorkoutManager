package com.palotech.pelflex.workout.measure;

import com.palotech.pelflex.workout.Workout;

public class IncreaseDuration extends Measure {

    public IncreaseDuration(String name, Cumulator code, int priority, boolean increasing, double value1, double value2, double value3, int ttl) {
        super(name, code, priority, increasing, value1, value2, value3, ttl);
    }

    @Override
    public void execute(Workout workout) {
        // TODO do sth on the workout
    }


}
