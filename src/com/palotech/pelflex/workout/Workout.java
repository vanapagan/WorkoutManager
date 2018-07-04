package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

import java.time.LocalDateTime;

public abstract class Workout {

    private static int idCount;

    private int Id;
    private Metadata metadata;
    private LocalDateTime date;
    protected Accumulator accumulator;

    protected Workout(Metadata metadata) {
        this.Id = idCount++;
        this.metadata = metadata;
        this.date = LocalDateTime.now();
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public int getId() {
        return Id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public abstract int getXpReward();

    @Override
    public String toString() {
        return Id + " " + metadata.toString();
    }

}
