package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.workout.exercise.template.value.CycleValue;
import com.palotech.pelflex.workout.exercise.template.value.PercentageCycleValue;

public interface Incrementable {

    CycleValue createDurationIncCycleValue(double value);

    CycleValue createDurationIncPercentageCycleValue(double value);

    PercentageCycleValue createDurationDecPercentageCycleValue(double value);

}
