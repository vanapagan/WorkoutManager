package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.metadata.Metadata;

import java.time.LocalDateTime;

public class Workout {

    private static int idCount;

    private int Id;
    private int userId;
    private Metadata metadata;
    private LocalDateTime date;

    public Workout(int userId, Metadata metadata) {
        this.Id = ++idCount;
        this.userId = userId;
        this.metadata = metadata;
        this.date = LocalDateTime.now();
    }

    public Metadata getMetadata() {
        return metadata;
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

}
