package com.palotech.pelflex;

import com.palotech.pelflex.workout.*;

/**
 * Created by Kristo on 08.06.2018.
 */
public class Runner {

    public static void main(String[] args) {
        /*
        for (int i = 0; i < 375; i++) {
            Workout w = WorkoutService.getNewWorkout(123);
            WorkoutService.getWorkoutList().add(w);
        }
        */

        System.out.println(new ComplexStep(ComplexStep.Type.MAX, 3, 0.50d));

        // TODO Mustri genereerimine
        PatternManager.generatePattern(57);

    }



}
