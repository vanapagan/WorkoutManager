package com.palotech.pelflex;

import com.palotech.pelflex.workout.ComplexStep;
import com.palotech.pelflex.workout.FeedbackService;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.WorkoutService;

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

        System.out.print(new ComplexStep(ComplexStep.Type.MAX, 5, 0.50d));

    }



}
