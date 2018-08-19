package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.burner.Transitory;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.exercise.value.Accumulator;
import com.palotech.pelflex.workout.measure.Measure;

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

    public Ledger(ExerciseTemplate exerciseTemplate, List<Transitory> transitoryList, double userFeedbackCoef) {
        this.id = ++idCount;
        this.accumulator = exerciseTemplate.getDefaultAccumulator();
        this.exercise = exerciseTemplate.getExercise();
        this.variation = exerciseTemplate.getVariation();
        this.measureList = exerciseTemplate.getMeasureList();
        // TODO implement the the clip
        this.measureClipList = exerciseTemplate.getMeasureClipList(userFeedbackCoef);
        this.transitoryList = transitoryList;
        this.userFeedbackCoef = userFeedbackCoef;
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
