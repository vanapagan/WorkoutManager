package com.palotech.pelflex.workout;

import java.time.LocalDateTime;

public class Workout {

    private static int idCount;

    private int Id;
    private int userId;
    private Variation variation;
    private PatternMetadata patternMetadata;
    private Pattern pattern;
    private double duration;
    private double handicap;
    private double incPercentage;
    private double decPercentage;
    private double maxDuration;
    private LocalDateTime date;

    public Workout(int userId, Variation variation, double duration, Pattern pattern, double handicap, double incPercentage, double decPercentage, double maxDuration) {
        this.Id = ++idCount;
        this.userId = userId;
        this.variation = variation;
        this.duration = duration;
        this.pattern = pattern;
        this.handicap = handicap;
        this.incPercentage = incPercentage;
        this.decPercentage = decPercentage;
        this.maxDuration = maxDuration;
        this.date = LocalDateTime.now();
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public Variation getVariation() {
        return variation;
    }

    public double getDecPercentage() {
        return decPercentage;
    }

    public double getIncPercentage() {
        return incPercentage;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return userId;
    }

    public double getDuration() {
        return duration;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public double getHandicap() {
        return handicap;
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
