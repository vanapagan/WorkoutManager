package com.palotech.pelflex.workout.builder.kegel;

import com.palotech.pelflex.workout.FastKegel;
import com.palotech.pelflex.workout.Kegel;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;

public class FastKegelBuilder extends KegelBuilder {

    public FastKegelBuilder(Ledger ledger, Metadata lastMetadata) {
        super(ledger, lastMetadata);
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
