package com.palotech.pelflex.workout.measure;

public class Measure {

    private Group group;
    private Remedy incRemedy;
    private Remedy decRemedy;
    private Remedy balRemedy;

    public Measure(Group group, Remedy incRemedy, Remedy decRemedy, Remedy balRemedy) {
        this.group = group;
        this.incRemedy = incRemedy;
        this.decRemedy = decRemedy;
        this.balRemedy = balRemedy;
    }

    public Group getGroup() {
        return group;
    }

    public Remedy getIncRemedy() {
        return incRemedy;
    }

    public Remedy getDecRemedy() {
        return decRemedy;
    }

    public Remedy getBalRemedy() {
        return balRemedy;
    }

    public enum RemedyType {
        INCREASE_DURATION_LENGTH,
        DECREASE_DURATION_LENGTH,
        INCREASE_MAX_FLEX_PROPORTION,
        DECREASE_MAX_FLEX_PROPORTION,
        INCREASE_STEP_FLEX_PROPORTION,
        DECREASE_STEP_FLEX_PROPORTION,
        INCREASE_NUMBER_OF_STEPS,
        DECREASE_NUMBER_OF_STEPS,
        SET_FLEX_LESSER_THAN_EQUAL_RELAX_STEP_PROPORTION,
        SET_MAX_FLEX_PROPORTION_30_40_PERCENTAGE
    }

    public enum Group {
        DURATION_LENGTH,
        STEP_FLEX_PROPORTION,
        NUMBER_OF_STEPS,
        STEP_MAX_FLEX_QUANTITY
    }

    public enum BEHAVIOUR {
        DECREASING,
        BALANCING,
        INCREASING
    }

}
