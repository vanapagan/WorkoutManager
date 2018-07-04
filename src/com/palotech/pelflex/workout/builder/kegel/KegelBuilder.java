package com.palotech.pelflex.workout.builder.kegel;

import com.palotech.pelflex.workout.Kegel;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.builder.Builder;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.feedback.FeedbackService;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

public class KegelBuilder extends Builder {

    public KegelBuilder(Ledger ledger, Metadata lastMetadata) {
        super(ledger, lastMetadata);
    }

    @Override
    public Workout createWorkout() {
        difficulty = createDifficulty();
        patternMetadata = createPatternMetadata();
        pattern = createPattern();
        metadata = createMetadata();

        return new Kegel(metadata);
    }

    @Override
    protected Difficulty createDifficulty() {
        double lastHandicap = lastMetadata.getDifficulty().getHandicap();
        double lastIncPercentage = lastMetadata.getDifficulty().getIncPercentage();
        double lastDecPercentage = lastMetadata.getDifficulty().getDecPercentage();
        double maxDuration = lastMetadata.getDifficulty().getMaxDuration();
        double increaseEdge = lastHandicap;

        double increasePercentage = lastIncPercentage;
        double handicapPercentage = lastDecPercentage;
        // TODO double userFeedbackCoef = FeedbackService.getUserFeedbackCoefficient(lastWorkout.getId());
        // maxDuration = maxDuration * (1.0d + userFeedbackCoef);
        maxDuration = maxDuration * (1.0d + 0.0d);

        ledger.getMeasureList();

        Accumulator accumulator = ledger.getAccumulator();
        boolean ceilingReached = accumulator.isCeilingReached();

        CycleValue durIncValue = new CycleValue(lastIncPercentage, 1.50d, 0.0d, 0.04d, 0.008d);
        CycleValue durSkimValue = new PercentageCycleValue(lastDecPercentage, 0.0d, 0.0d, 0.10d, 0.01);

        // If ceiling is reached and there is a INCREMENT_DURATION in ledger's measures
        double cycleValue;
        if (ceilingReached) {
            cycleValue = durIncValue.setAndReturnNewValue();
            increasePercentage = cycleValue;
        } else {
            cycleValue = durSkimValue.setAndReturnNewValue();
            handicapPercentage = cycleValue;
        }

        accumulator.accumulate();

        increaseEdge = accumulator.getValue();

        int raiseOrLowerMultiplier = ceilingReached ? 1 : -1;
        double duration = maxDuration * (1.0d + raiseOrLowerMultiplier * cycleValue);

        maxDuration = duration > maxDuration ? duration : maxDuration;

        return new Difficulty(duration, maxDuration, increaseEdge, increasePercentage, handicapPercentage);
    }

    @Override
    protected PatternMetadata createPatternMetadata() {
        int durationAsInt = new Double(difficulty.getDuration()).intValue();

        // TODO Kuna meil on nyyd loodava Workout-i oodatav raskusaste teada, siis me saame oma mustrit ka vastavalt selle j2rgi korrigeerida
        // TODO -> vastavalt siis nt denominaatorit suurendada v6i v2hendada, v6i hoopiski min/max-i suurendada/v2hendada
        PatternMetadata lastPatternMetadata = lastMetadata.getPattern().getPatternMetadata();
        int lastDenominator = lastPatternMetadata.getDenominator();
        int lastMin = lastPatternMetadata.getMin();
        int lastMax = lastPatternMetadata.getMax();

        return new PatternMetadata(durationAsInt, lastDenominator, lastMin, lastMax);
    }

    @Override
    protected Pattern createPattern() {
        return PatternManager.generatePattern(patternMetadata);
    }

    @Override
    protected Metadata createMetadata() {
        return new Metadata(ExerciseTemplate.Exercise.KEGEL, ExerciseTemplate.Variation.NORMAL, difficulty, pattern);
    }

}
