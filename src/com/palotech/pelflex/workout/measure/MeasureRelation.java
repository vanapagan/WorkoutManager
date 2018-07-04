package com.palotech.pelflex.workout.measure;

public class MeasureRelation {

    private Remedy remedy;
    private Remedy counterRemedy;
    private Remedy resetRemedy;

    public MeasureRelation(Remedy remedy, Remedy counterRemedy, Remedy resetRemedy) {
        this.remedy = remedy;
        this.counterRemedy = counterRemedy;
        this.resetRemedy = resetRemedy;
    }

}
