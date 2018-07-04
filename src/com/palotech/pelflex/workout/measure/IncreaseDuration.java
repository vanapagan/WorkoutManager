package com.palotech.pelflex.workout.measure;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.feedback.FeedbackService;

public class IncreaseDuration extends Measure {

    public IncreaseDuration(String name, Cumulator code, int priority, boolean increasing, double value1, double value2, double value3, int ttl) {
        super(name, code, priority, increasing, value1, value2, value3, ttl);
    }

    @Override
    public void execute(Workout lastWorkout, Workout newWorkout, Accumulator accumulator) {

        // TODO add Difficulty to the Workout
        /*
        boolean ceilingReached = accumulator.isCeilingReached();

        if (ceilingReached) {

        } else {
            accumulator.accumulate();
        }

        Metadata lastMetadata = lastWorkout.getMetadata();

        double lastHandicap = lastMetadata.getDifficulty().getHandicap();
        double lastIncPercentage = lastMetadata.getDifficulty().getIncPercentage();
        double lastDecPercentage = lastMetadata.getDifficulty().getDecPercentage();
        double maxDuration = lastMetadata.getDifficulty().getMaxDuration();
        double increaseEdge = lastHandicap;

        double increasePercentage = lastIncPercentage;
        double handicapPercentage = lastDecPercentage;
        double userFeedbackCoef = FeedbackService.getUserFeedbackCoefficient(lastWorkout.getId());
        maxDuration = maxDuration * (1.0d + userFeedbackCoef);

        CycleValue durationIncCycleValue = exerciseTemplate.createDurationIncCycleValue(lastHandicap);
        CycleValue durationIncPercentageCycleValue = exerciseTemplate.createDurationIncPercentageCycleValue(lastIncPercentage);
        PercentageCycleValue durationDecPercentageCycleValue = exerciseTemplate.createDurationDecPercentageCycleValue(lastDecPercentage);
        */


        /*
        double cycleValue;
        boolean ceilingReached = durationIncCycleValue.isCeilingReached();
        if (ceilingReached) {
            cycleValue = durationIncPercentageCycleValue.setAndReturnNewValue();
            durationIncCycleValue.resetValue();
            increasePercentage = cycleValue;
        } else {
            durationIncCycleValue.setAndReturnNewValue();
            cycleValue = durationDecPercentageCycleValue.setAndReturnNewValue();
            handicapPercentage = cycleValue;
        }
        */
        /*
        increaseEdge = durationIncCycleValue.getValue();

        int raiseOrLowerMultiplier = ceilingReached ? 1 : -1;
        double duration = maxDuration * (1.0d + raiseOrLowerMultiplier * cycleValue);

        maxDuration = duration > maxDuration ? duration : maxDuration;

        return new Difficulty(duration, maxDuration, increaseEdge, increasePercentage, handicapPercentage);
        */
    }


}
