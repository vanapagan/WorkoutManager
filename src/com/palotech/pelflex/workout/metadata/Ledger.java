package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.burner.Transitory;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.measure.Measure;

import java.util.ArrayList;
import java.util.List;

public class Ledger {

    private static int idCount;

    private int id;
    private Accumulator accumulator;
    private ExerciseTemplate.Exercise exercise;
    private ExerciseTemplate.Variation variation;
    private List<Measure> measureList;
    private List<Measure> measureClipList;
    private List<Transitory> transitoryList;
    private double userFeedbackCoef;
    private ExerciseTemplate exerciseTemplate;
    private int level;

    // TODO Paneme Ledgeri külge ka level-i, siis me teame, mitu korda me neid Measure-id peame rakendama, enne kui uue Ledgeri võtame
    public Ledger(ExerciseTemplate exerciseTemplate, List<Transitory> transitoryList, double userFeedbackCoef) {
        this.id = ++idCount;
        this.accumulator = exerciseTemplate.getDefaultAccumulator();
        this.exercise = exerciseTemplate.getExercise();
        this.variation = exerciseTemplate.getVariation();
        this.measureList = new ArrayList<>();
        // TODO implement the clip
        this.measureClipList = exerciseTemplate.getMeasureClipList(userFeedbackCoef);
        this.transitoryList = transitoryList;
        this.userFeedbackCoef = userFeedbackCoef;
        this.level = 1;
        this.exerciseTemplate = exerciseTemplate;
    }

    public boolean isCompleted() {
        return level > exerciseTemplate.getLedgerMaxLevel();
    }

    public int getLevel() {
        return level;
    }

    public void progressLedger() {
        accumulator.accumulate();
        if (haveAllMeasuresBeenApplied()) {
            measureList = new ArrayList<>();
            measureClipList = exerciseTemplate.getMeasureClipList(userFeedbackCoef);
            accumulator.reset();
            levelUp();
        }
    }

    private boolean haveAllMeasuresBeenApplied() {
        return measureList.size() == exerciseTemplate.getMeasureList().size();
    }

    private void levelUp() {
        level++;
        System.out.println("Ledger level up, now: " + level);
    }

    public double getUserFeedbackCoef() {
        return userFeedbackCoef;
    }

    public int getId() {
        return id;
    }

    public ExerciseTemplate.Exercise getExercise() {
        return exercise;
    }

    public void accumulate() {
        accumulator.accumulate();
    }

    public Accumulator getAccumulator() {
        return accumulator;
    }

    public List<Transitory> getTransitoryList() {
        return transitoryList;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }

    public boolean isItTimeToAccumulate(Measure.Group group) {
        return isCeilingReached() && measureClipList.stream().anyMatch(m -> m.getGroup() == group);
    }

    public List<Measure> getMeasureClipList() {
        return measureClipList;
    }

    public boolean isCeilingReached() {
        return accumulator.isCeilingReached();
    }

}
