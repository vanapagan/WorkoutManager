package com.palotech.pelflex.workout;

import java.time.LocalDateTime;

public class Workout {

    private static int idCount;

    private int Id;
    private int userId;
    private ExerciseMetadata exerciseMetadata;
    private Pattern pattern;
    private LocalDateTime date;

    public Workout(int userId, ExerciseMetadata exerciseMetadata) {
        this.Id = ++idCount;
        this.userId = userId;
        this.exerciseMetadata = exerciseMetadata;
        this.date = LocalDateTime.now();
    }

    public ExerciseMetadata getExerciseMetadata() {
        return exerciseMetadata;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public enum Variation {
        NORMAL,
        FAST;
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
