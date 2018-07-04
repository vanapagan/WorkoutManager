package com.palotech.pelflex.workout.exercise.template.kegel;

import com.palotech.pelflex.workout.FastKegel;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

import static com.palotech.pelflex.workout.exercise.template.ExerciseTemplate.Exercise.KEGEL;

public class FastKegelTemplateTemplate extends KegelTemplate {

    public FastKegelTemplateTemplate(Variation variation) {
        super(variation);
    }

    private static Workout getDefaultFastWorkout(ExerciseTemplate exerciseTemplate) {
        int globalDuration = 30;
        double duration = globalDuration;
        double handicap = 0.0d;
        double incPercentage = 0.0d;
        double decPercentage = 0.01d;
        double maxDuration = globalDuration;
        int denominator = 8;
        int min = 4;
        int max = 4;

        Difficulty difficulty = new Difficulty(duration, maxDuration, handicap, incPercentage, decPercentage);

        int durationAsInt = new Double(duration).intValue();
        PatternMetadata patternMetadata = new PatternMetadata(durationAsInt, denominator, min, max);
        Pattern pattern = PatternManager.generatePattern(patternMetadata);
        Metadata metadata = new Metadata(KEGEL, Variation.FAST, difficulty, pattern);

        return new FastKegel(metadata);
    }

    @Override
    public Workout getDefaultWorkout() {
        return getDefaultFastWorkout(this);
    }

}
