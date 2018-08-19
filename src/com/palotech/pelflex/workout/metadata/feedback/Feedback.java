package com.palotech.pelflex.workout.metadata.feedback;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

import java.time.LocalDateTime;

public class Feedback {

    private static int idCount;

    private int Id;
    ExerciseTemplate.Exercise exercise;
    ExerciseTemplate.Variation variation;
    private double satCo;
    private LocalDateTime date;

    public Feedback(ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation, double satCo) {
        this.Id = ++idCount;
        this.exercise = exercise;
        this.variation = variation;
        this.satCo = satCo;
        this.date = LocalDateTime.now();
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

    public static int getIdCount() {
        return idCount;
    }

    public int getId() {
        return Id;
    }

    public double getSatCo() {
        return satCo;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
