package com.palotech.pelflex.workout.exercise.template.kegel;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.template.Incrementable;
import com.palotech.pelflex.workout.exercise.template.value.CycleValue;
import com.palotech.pelflex.workout.exercise.template.value.PercentageCycleValue;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

public class FastKegel extends Kegel implements Incrementable {

    public FastKegel(Variation variation) {
        super(variation);
    }

    private static Workout getDefaultFastWorkout(ExerciseTemplate exerciseTemplate) {
        int globalDuration = 30;
        int userId = 123;
        double duration = globalDuration;
        double handicap = 0.0d;
        double incPercentage = 0.0d;
        double decPercentage = 0.01d;
        double maxDuration = globalDuration;
        int denominator = 8;
        int min = 4;
        int max = 4;

        Difficulty difficulty = new Difficulty(duration, maxDuration, handicap, incPercentage, decPercentage);

        int durationAsInt = new Double(duration).intValue();
        PatternMetadata patternMetadata = new PatternMetadata(durationAsInt, denominator, min, max);
        Pattern pattern = PatternManager.generatePattern(patternMetadata);
        Metadata metadata = new Metadata(exerciseTemplate, difficulty, pattern);

        return new Workout(userId, metadata);
    }

    @Override
    public CycleValue createDurationIncCycleValue(double value) {
        return new CycleValue(value, 1.10d, 0.0d, 0.95d, 0.10d);
    }

    @Override
    public CycleValue createDurationIncPercentageCycleValue(double value) {
        return new CycleValue(value, 1.20d, 0.0d, 0.04d, 0.008d);
    }

    @Override
    public PercentageCycleValue createDurationDecPercentageCycleValue(double value) {
        return new PercentageCycleValue(value, 0.0d, 0.0d, 0.10d, 0.01);
    }

    @Override
    public Workout getDefaultWorkout() {
        return getDefaultFastWorkout(this);
    }

}
