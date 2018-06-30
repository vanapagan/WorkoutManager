package com.palotech.pelflex.workout.metadata.feedback;

import java.time.LocalDateTime;

public class Feedback {

    private static int idCount;

    private int Id;
    private int userId;
    private int workoutId;
    private double satCo;
    private LocalDateTime date;

    public Feedback(int userId, double satCo) {
        this.Id = ++idCount;
        this.userId = userId;
        this.satCo = satCo;
        this.date = LocalDateTime.now();
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public static int getIdCount() {
        return idCount;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return userId;
    }

    public double getSatCo() {
        return satCo;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
