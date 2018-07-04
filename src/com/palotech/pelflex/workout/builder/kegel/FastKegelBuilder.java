package com.palotech.pelflex.workout.builder.kegel;

import com.palotech.pelflex.workout.FastKegel;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;

public class FastKegelBuilder extends KegelBuilder {

    public FastKegelBuilder(ExerciseTemplate template, Ledger ledger, Metadata lastMetadata) {
        super(template, ledger, lastMetadata);
    }

    @Override
    public Workout createWorkout() {
        difficulty = createDifficulty();
        patternMetadata = createPatternMetadata();
        pattern = createPattern();
        metadata = createMetadata();

        return new FastKegel(metadata);
    }

    @Override
    protected Metadata createMetadata() {
        return new Metadata(ExerciseTemplate.Exercise.KEGEL, ExerciseTemplate.Variation.FAST, difficulty, pattern);
    }

}
