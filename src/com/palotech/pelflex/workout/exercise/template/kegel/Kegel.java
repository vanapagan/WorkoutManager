package com.palotech.pelflex.workout.exercise.template.kegel;

import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;
import com.palotech.pelflex.workout.measure.IncreaseDuration;
import com.palotech.pelflex.workout.measure.Measure;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

import java.util.ArrayList;
import java.util.List;

public class Kegel extends ExerciseTemplate {

    public Kegel(Variation variation) {
        super(variation);
    }

    @Override
    public ExerciseTemplate generateExerciseTemplate(Variation variation) {
        if (variation == Variation.FAST) {
            return new FastKegel(variation);
        } else {
            return new Kegel(variation);
        }
    }

    // TODO Meil oleks vaja need kolm meetodit siduda omakorda ka Variatsiooniga

    @Override
    public CycleValue createDurationIncCycleValue(double value) {
        return new CycleValue(value, 1.19d, 0.0d, 0.90d, 0.10d);
    }

    @Override
    public CycleValue createDurationIncPercentageCycleValue(double value) {
        return new CycleValue(value, 1.50d, 0.0d, 0.04d, 0.008d);
    }

    @Override
    public PercentageCycleValue createDurationDecPercentageCycleValue(double value) {
        return new PercentageCycleValue(value, 0.0d, 0.0d, 0.10d, 0.01);
    }

    @Override
    public Workout getDefaultWorkout() {
        return getDefaultNormalWorkout(this);
    }

    private static Workout getDefaultNormalWorkout(ExerciseTemplate exerciseTemplate) {
        int globalDuration = 56;
        int userId = 123;
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
        Metadata metadata = new Metadata(exerciseTemplate, difficulty, pattern);

        return new Workout(userId, metadata);
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
        return Exercise.KEGEL;
    }

    @Override
    public int calculateXpReward(Workout workout) {
        Pattern pattern = workout.getMetadata().getPattern();

        double durationReward = workout.getMetadata().getDifficulty().getDuration() * 10;

        int numberOfSteps = pattern.getCompStepList().size();
        double avgFlexOverRelax = pattern.getFlexPercentage();
        double flexesProportionReward = numberOfSteps * avgFlexOverRelax * 5;

        double variabilityCoefficientReward = pattern.getVariabilityCoefficient() * 2.5d;

        int xp = (int) (durationReward + flexesProportionReward + variabilityCoefficientReward);

        return xp;
    }

    @Override
    public List<Measure> getMeasures() {
        List<Measure> list = new ArrayList<>();
        // Measure(String name, Cumulator code, int priority, boolean increasing, double value1, double value2, double value3, int ttl)
        list.add(new IncreaseDuration("Increase duration", Measure.Cumulator.INCREASE_DURATION, 100, true, 1.19d, 0, 0, 1));

        return list;
    }
}
