package com.palotech.pelflex;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.WorkoutService;

/**
 * Created by Kristo on 08.06.2018.
 */
public class Runner {

    public static void main(String[] args) {

        for (int i = 0; i < 376; i++) {
            Workout w = WorkoutService.composeNewWorkout(123);
            WorkoutService.getWorkoutList().add(w);
            System.out.println(w);
        }

        // System.out.println(new ComplexStep(ComplexStep.Type.MAX, 3, 0.50d));

        // TODO Mustri genereerimine
        //for (int i = 57; i < 147; i++) {
        //PatternManager.generatePattern(57, ExerciseTemplate.Variation.NORMAL);
        //}

        //ComplexStep cs = new ComplexStep(ComplexStep.Type.MIN, 8, 0.5d);
        //System.out.println(cs);

    }



}
