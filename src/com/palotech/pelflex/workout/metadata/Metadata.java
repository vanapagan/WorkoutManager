package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;

public class Metadata {

    private ExerciseTemplate exerciseTemplate;
    private Difficulty difficulty;
    private Pattern pattern;

    public Metadata(ExerciseTemplate template, Difficulty difficulty, Pattern pattern) {
        this.exerciseTemplate = template;
        this.difficulty = difficulty;
        this.pattern = pattern;
    }

    public ExerciseTemplate getExerciseTemplate() {
        return exerciseTemplate;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return exerciseTemplate + " " + difficulty.toString() + " " + pattern.toStringCompact();
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
