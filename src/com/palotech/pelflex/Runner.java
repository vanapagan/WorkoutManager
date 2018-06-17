package com.palotech.pelflex;

import com.palotech.pelflex.workout.FeedbackService;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.WorkoutService;

/**
 * Created by Kristo on 08.06.2018.
 */
public class Runner {

    public static void main(String[] args) {

        WorkoutService.getWorkoutList().add(new Workout(123, Workout.Variation.NORMAL, 56,0.0, 0.0, 0.01d, 56));
        WorkoutService.getWorkoutList().add(new Workout(123, Workout.Variation.FAST, 30,0.0, 0.0, 0.01d, 30));

        for (int i = 0; i < 375; i++) {
            WorkoutService.getNewWorkout(123);
        }

    }



}
