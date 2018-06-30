package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;

public class Metadata {

    private Workout.Variation variation;
    private Difficulty difficulty;
    private Pattern pattern;

    public Metadata(Workout.Variation variation, Difficulty difficulty, Pattern pattern) {
        this.variation = variation;
        this.difficulty = difficulty;
        this.pattern = pattern;
    }

    public Workout.Variation getVariation() {
        return variation;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public enum Accumulator {
        INCREASE_WORKOUT_DURATION,
        DECREASE_WORKOUT_DURATION,
        INCREASE_FLEX_TIME,
        DECREASE_FLEX_TIME,
        INCREASE_FLEX_MAX_STEP_SIZE,
        DECREASE_FLEX_MAX_STEP_SIZE,
        INCREASE_MAX_FLEX_QUANTITY,
        DECREASE_MAX_FLEX_QUANTITY,
        INCREASE_STEP_DENOMINATOR,
        DECREASE_STEP_DENOMINATOR
    }

}
