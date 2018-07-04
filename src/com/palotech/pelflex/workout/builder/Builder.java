package com.palotech.pelflex.workout.builder;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

public abstract class Builder {

    protected ExerciseTemplate template;
    protected Ledger ledger;
    protected Metadata lastMetadata;

    protected Difficulty difficulty;
    protected PatternMetadata patternMetadata;
    protected Pattern pattern;
    protected Metadata metadata;

    public Builder(ExerciseTemplate template, Ledger ledger, Metadata lastMetadata) {
        this.template = template;
        this.ledger = ledger;
        this.lastMetadata = lastMetadata;
    }

    public abstract Workout createWorkout();

    protected abstract Difficulty createDifficulty();

    protected abstract PatternMetadata createPatternMetadata();

    protected abstract Pattern createPattern();

    protected abstract Metadata createMetadata();

}
