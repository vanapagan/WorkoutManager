package com.palotech.pelflex.workout.builder.kegel;

import com.palotech.pelflex.workout.Kegel;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.builder.Builder;
import com.palotech.pelflex.workout.burner.Transitory;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.measure.Measure;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

import java.util.List;
import java.util.Optional;

public class KegelBuilder extends Builder {


    public KegelBuilder(ExerciseTemplate template, Ledger ledger, Metadata lastMetadata) {
        super(template, ledger, lastMetadata);
    }

    @Override
    public Workout createWorkout() {
        difficulty = createDifficulty();
        patternMetadata = createPatternMetadata();
        pattern = createPattern();
        metadata = createMetadata();

        accumulate();

        return new Kegel(metadata);
    }

    private void accumulate() {
        // TODO lisame Ledgeri kylge ka j2rgmise Measure-i, v6i kui tal juba on k6ik olemas, siis ei tee midagi
        ledger.progressLedger();
        // TODO also lähtesta k6ik Measure-id, kui Workout-i duration-it on tõstetud
    }

    @Override
    protected Difficulty createDifficulty() {
        double maxDuration = lastMetadata.getDifficulty().getMaxDuration();

        maxDuration = maxDuration * (1.0d + ledger.getUserFeedbackCoef());

        List<Transitory> transitoryList = ledger.getTransitoryList();

        CycleValue durIncValue = template.convertTransitoryToCycleValue(transitoryList, "DurationPeriodicInc");
        CycleValue durSkimValue = template.convertTransitoryToCycleValue(transitoryList, "DurationPercentageSkim");

        boolean isItTimeToAccumulate = ledger.isItTimeToAccumulate(Measure.Group.DURATION_LENGTH);
        double cycleValue = isItTimeToAccumulate ? durIncValue.setAndReturnNewValue() : durSkimValue.setAndReturnNewValue();

        // TODO Kui me runtime-i ajal juba siia kohta j6uame, siis peaks measureClipList juba j2rgmisi rakendatavaid Measure-id sisaldama
        if (isItTimeToAccumulate) {
            List<Measure> measureClipList = ledger.getMeasureClipList();
            Optional<Measure> measureOptional = measureClipList.stream().filter(m -> m.getGroup() == Measure.Group.DURATION_LENGTH).findAny();
            if (measureOptional.isPresent()) {
                Measure measure = measureOptional.get();
                measureClipList.remove(measure);
                ledger.getMeasureList().add(measure);
            }
        }

        Transitory durIncTransitory = template.convertCycleValueToTransitory(durIncValue);
        Transitory durSkimTransitory = template.convertCycleValueToTransitory(durSkimValue);

        transitoryList.add(durIncTransitory);
        transitoryList.add(durSkimTransitory);

        int raiseOrLowerMultiplier = ledger.isCeilingReached() ? 1 : -1;
        double duration = maxDuration * (1.0d + raiseOrLowerMultiplier * cycleValue);

        maxDuration = duration > maxDuration ? duration : maxDuration;

        return new Difficulty(duration, maxDuration);
    }

    @Override
    protected PatternMetadata createPatternMetadata() {
        int durationAsInt = new Double(difficulty.getDuration()).intValue();

        // TODO Me kysime Ledgeri k2est, kas tal on 'PATTERN' grupiga midagi oma nimistus

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
