package com.palotech.pelflex.workout.exercise.template.kegel;

import com.palotech.pelflex.workout.Kegel;
import com.palotech.pelflex.workout.Workout;
import com.palotech.pelflex.workout.builder.Builder;
import com.palotech.pelflex.workout.builder.kegel.KegelBuilder;
import com.palotech.pelflex.workout.burner.Transitory;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.exercise.value.CycleValue;
import com.palotech.pelflex.workout.exercise.value.PercentageCycleValue;
import com.palotech.pelflex.workout.measure.Measure;
import com.palotech.pelflex.workout.measure.Remedy;
import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternManager;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.palotech.pelflex.workout.exercise.template.ExerciseTemplate.Exercise.KEGEL;

public class KegelTemplate extends ExerciseTemplate {

    public KegelTemplate(Variation variation) {
        super(variation);
    }

    private static Workout getDefaultNormalWorkout(ExerciseTemplate exerciseTemplate) {
        int globalDuration = 56;
        double duration = globalDuration;
        double maxDuration = globalDuration;
        int denominator = 8;
        int min = 4;
        int max = 10;

        Difficulty difficulty = new Difficulty(duration, maxDuration);

        int durationAsInt = new Double(duration).intValue();
        PatternMetadata patternMetadata = new PatternMetadata(durationAsInt, denominator, min, max);
        Pattern pattern = PatternManager.generatePattern(patternMetadata);
        Metadata metadata = new Metadata(KEGEL, Variation.NORMAL, difficulty, pattern);

        return new Kegel(metadata);
    }

    @Override
    public CycleValue convertTransitoryToCycleValue(List<Transitory> transitoryList, String key) {
        Optional<Transitory> transitoryOptional = transitoryList.stream().filter(t -> key.equals(t.getName())).findFirst();
        CycleValue cycleValue = null;
        if (transitoryOptional.isPresent()) {
            Transitory transitory = transitoryOptional.get();
            if ("DurationPeriodicInc".equals(key)) {
                cycleValue = new CycleValue("DurationPeriodicInc", transitory.getDoubleValue1(), transitory.getDoubleValue2(), transitory.getDoubleValue3(), transitory.getDoubleValue4(), transitory.getDoubleValue5());
            } else if ("DurationPercentageSkim".equals(key)) {
                cycleValue = new PercentageCycleValue("DurationPercentageSkim", transitory.getDoubleValue1(), transitory.getDoubleValue2(), transitory.getDoubleValue3(), transitory.getDoubleValue4(), transitory.getDoubleValue5());
            } else if ("StepFlexProportion".equals(key)) {
                // 0.0d, 0.1d, 0.3, 0.5, 0.0d, 0.0d
                cycleValue = new CycleValue("StepFlexProportion", transitory.getDoubleValue1(), transitory.getDoubleValue2(), transitory.getDoubleValue3(), transitory.getDoubleValue4(), transitory.getDoubleValue5());
            }
        }

        return cycleValue;
    }

    @Override
    public List<String> getBurnerKeysList() {
        List<String> list = new ArrayList();
        list.add("DurationPeriodicInc");
        list.add("DurationPercentageSkim");
        //list.add("StepFlexProportion");

        return list;
    }


    @Override
    public Transitory getTransitoryDefault(String key) {
        Transitory transitory;
        if (key.equals("DurationPeriodicInc")) {
            transitory = new Transitory("DurationPeriodicInc", getExercise(), getVariation(), 0.0d, 1.50d, 0.0d, 0.04d, 0.008d, 0.0d);
        } else if (key.equals("StepFlexProportion")) {
            transitory = new Transitory("StepFlexProportion", getExercise(), getVariation(), 0.0d, 0.1d, 0.3, 0.5, 0.0d, 0.0d);
        } else {
            transitory = new Transitory("DurationPercentageSkim", getExercise(), getVariation(), 0.0d, 0.0d, 0.10d, 0.01d, 0.0d, 0.0d);
        }

        return transitory;
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

    @Override
    public Transitory convertCycleValueToTransitory(CycleValue cv) {
        Transitory transitory = null;
        if (cv.getName().equals("DurationPeriodicInc")) {
            // CycleValue(String name, double value, double multiplier, double floor, double ceiling, double initialValue)
            transitory = new Transitory(cv.getName(), getExercise(), getVariation(), cv.getValue(), cv.getMultiplier(), cv.getFloor(), cv.getCeiling(), cv.getInitialValue(), 0.0d);
        } else if (cv.getName().equals("StepFlexProportion")) {
            transitory = new Transitory(cv.getName(), getExercise(), getVariation(), cv.getValue(), cv.getMultiplier(), cv.getFloor(), cv.getCeiling(), cv.getInitialValue(), 0.0d);
        } else {
            transitory = new Transitory(cv.getName(), getExercise(), getVariation(), cv.getValue(), cv.getMultiplier(), cv.getFloor(), cv.getCeiling(), 0.0d, 0.0d);
        }

        return transitory;
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
    public void initMeasureMap() {
        this.measureMap = new HashMap<>();
        // measureMap.put(getDurationMeasure().getGroup(), getStepFlexProportionMeasure());
        // measureMap.put(getDurationMeasure().getGroup(), getStepFlexProportionMeasure());
        measureMap.put(getDurationMeasure().getGroup(), getDurationMeasure());
        // measureMap.put(getStepFlexProportionMeasure().getGroup(), getDurationMeasure());
        // measureMap.put(getStepFlexProportionMeasure().getGroup(), getDurationMeasure());
    }

    @Override
    public List<Measure> getMeasureList() {
        List<Measure> list = new ArrayList<>();
        //Measure stepFlexProp = getStepFlexProportionMeasure();
        Measure duIncMes = getDurationMeasure();

        //list.add(stepFlexProp);
        list.add(duIncMes);

        return list;
    }

    @Override
    public Measure getNextMeasure(Measure measure) {
        return measure != null ? measureMap.get(measure.getGroup()) : measureMap.get(Measure.Group.DURATION_LENGTH);
    }

    @Override
    public List<Measure> getMeasureClipList(double userFeedbackCoef, Measure lastMeasure) {
        List<Measure> measureClipList = new ArrayList<>();
        measureClipList.add(getNextMeasure(lastMeasure));
        return getMeasureList();
    }

    @Override
    public int getLedgerMaxLevel() {
        return 3;
    }

    private Measure getDurationMeasure() {
        // String name, double value1, double value2, double value3, int ttl
        Remedy incRem = new Remedy(1.19, 0, 0);
        Remedy decRem = new Remedy(1.19, 0, 0);
        Optional<Remedy> balRem = Optional.empty();
        int ttl = 1;

        return new Measure(Measure.Group.DURATION_LENGTH, incRem, decRem, balRem, 1);
    }

    private Measure getStepFlexProportionMeasure() {
        Remedy incRem = new Remedy(1.19, 0, 0);
        Remedy decRem = new Remedy(1.19, 0, 0);
        Optional<Remedy> balRem = Optional.empty();
        int ttl = 1;

        return new Measure(Measure.Group.STEP_FLEX_PROPORTION, incRem, decRem, balRem, 1);
    }

    @Override
    public Accumulator getDefaultAccumulator() {
        return new Accumulator(0, 10, 0, 90, 1.19);
    }

}
