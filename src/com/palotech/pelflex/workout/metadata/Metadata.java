package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;

public class Metadata {

    private ExerciseTemplate.Exercise exercise;
    private ExerciseTemplate.Variation variation;
    private Difficulty difficulty;
    private Pattern pattern;

    public Metadata(ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation, Difficulty difficulty, Pattern pattern) {
        this.exercise = exercise;
        this.variation = variation;
        this.difficulty = difficulty;
        this.pattern = pattern;
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return exercise + " " + variation + " " + difficulty + " " + pattern.toStringCompact();
    }

}
