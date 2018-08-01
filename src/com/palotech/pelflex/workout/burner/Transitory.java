package com.palotech.pelflex.workout.burner;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

public class Transitory {

    private static int idCount;

    private int id;
    private String name;
    private ExerciseTemplate.Exercise exercise;
    private ExerciseTemplate.Variation variation;
    private double doubleValue1;
    private double doubleValue2;
    private double doubleValue3;
    private double doubleValue4;
    private double doubleValue5;
    private double doubleValue6;

    public Transitory(String name, ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation, double doubleValue1, double doubleValue2, double doubleValue3, double doubleValue4, double doubleValue5, double doubleValue6) {
        this.id = idCount++;
        this.name = name;
        this.exercise = exercise;
        this.variation = variation;
        this.doubleValue1 = doubleValue1;
        this.doubleValue2 = doubleValue2;
        this.doubleValue3 = doubleValue3;
        this.doubleValue4 = doubleValue4;
        this.doubleValue5 = doubleValue5;
        this.doubleValue6 = doubleValue6;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

    public double getDoubleValue1() {
        return doubleValue1;
    }

    public double getDoubleValue2() {
        return doubleValue2;
    }

    public double getDoubleValue3() {
        return doubleValue3;
    }

    public double getDoubleValue4() {
        return doubleValue4;
    }

    public double getDoubleValue5() {
        return doubleValue5;
    }

    public double getDoubleValue6() {
        return doubleValue6;
    }
}


