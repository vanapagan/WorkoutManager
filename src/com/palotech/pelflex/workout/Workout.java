package com.palotech.pelflex.workout;

import java.time.LocalDateTime;

public class Workout {

    private static int idCount;

    private int Id;
    private int userId;
    private Variation variation;
    private double duration;
    private double handicap;
    private double incPercentage;
    private int decPercentage;
    private double maxDuration;
    private LocalDateTime date;

    public Workout(int userId, Variation variation, double duration, double handicap, double incPercentage, int decPercentage, double maxDuration) {
        this.Id = ++idCount;
        this.userId = userId;
        this.variation = variation;
        this.duration = duration;
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

    public int getDecPercentage() {
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

}
