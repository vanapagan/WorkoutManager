package com.palotech.pelflex.workout.exercise.template.kegel;

import com.palotech.pelflex.workout.Kegel;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.builder.Builder;
import com.palotech.pelflex.workout.builder.kegel.KegelBuilder;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.measure.Measure;
import com.palotech.pelflex.workout.measure.Remedy;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.palotech.pelflex.workout.exercise.template.ExerciseTemplate.Exercise.KEGEL;

public class KegelTemplate extends ExerciseTemplate {

    public KegelTemplate(Variation variation) {
        super(variation);
    }

    @Override
    public Builder createBuilder(ExerciseTemplate template, Ledger ledger, Metadata lastMetadata) {
        return new KegelBuilder(template, ledger, lastMetadata);
    }

    @Override
    public ExerciseTemplate generateExerciseTemplate(Variation variation) {
        if (variation == Variation.FAST) {
            return new FastKegelTemplate(variation);
        } else {
            return new KegelTemplate(variation);
        }
    }

    @Override
    public Workout getDefaultWorkout() {
        return getDefaultNormalWorkout(this);
    }

    private static Workout getDefaultNormalWorkout(ExerciseTemplate exerciseTemplate) {
        int globalDuration = 56;
        double duration = globalDuration;
        double handicap = 0.0d;
        double incPercentage = 0.0d;
        double decPercentage = 0.01d;
        double maxDuration = globalDuration;
        int denominator = 8;
        int min = 4;
        int max = 10;

        Difficulty difficulty = new Difficulty(duration, maxDuration, handicap, incPercentage, decPercentage);

        int durationAsInt = new Double(duration).intValue();
        PatternMetadata patternMetadata = new PatternMetadata(durationAsInt, denominator, min, max);
        Pattern pattern = PatternManager.generatePattern(patternMetadata);
        Metadata metadata = new Metadata(KEGEL, Variation.NORMAL, difficulty, pattern);

        return new Kegel(metadata);
    }

    @Override
    public List<Variation> getVariationsList() {
        List<Variation> list = new ArrayList<>();
        list.add(Variation.NORMAL);
        list.add(Variation.FAST);

        return list;
    }

    @Override
    public Exercise getExercise() {
        return KEGEL;
    }

    @Override
    public List<Measure> getMeasureList() {
        List<Measure> list = new ArrayList<>();
        Measure duIncMes = getDurationMeasure();

        list.add(duIncMes);

        return list;
    }

    private Measure getDurationMeasure() {
        // String name, double value1, double value2, double value3, int ttl
        Remedy incRem = new Remedy(1.19, 0, 0);
        Remedy decRem = new Remedy(1.19, 0, 0);
        Optional<Remedy> balRem = Optional.empty();
        int ttl = 1;

        return new Measure(Measure.Group.DURATION_LENGTH, incRem, decRem, balRem, 1);
    }

    @Override
    public Accumulator getDefaultAccumulator() {
        return new Accumulator(0, 10, 0, 90, 1.19);
    }

}
