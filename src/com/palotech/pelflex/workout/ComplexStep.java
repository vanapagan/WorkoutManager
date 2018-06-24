package com.palotech.pelflex.workout;

import java.util.ArrayList;
import java.util.List;

public class ComplexStep {

    private Type type;
    private int duration;
    private double balance;
    private List<Step> stepsList ;

    public ComplexStep(Type type, int duration, double balance) {
        this.type = type;
        this.duration = duration;
        this.balance = balance;
        initSteps();
    }

    private void initSteps() {
        stepsList = new ArrayList<>();

        int smallerDiv;
        int largerDiv;
        if (duration % 2 == 0) {
            int stepSize = duration / 2;
            smallerDiv = stepSize;
            largerDiv = stepSize;
        } else {
            smallerDiv = duration / 2;
            largerDiv = duration - smallerDiv;
        }

        // TODO siia on mingit parameetrit vaja, mis ytleks kumb pool peab suurem olema,
        // TODO kas FLEX v6i RELAX osa

        int flexSize = 1==1 ? largerDiv : smallerDiv;
        int relaxSize = 2==2 ? smallerDiv : largerDiv;

        stepsList.add(new Step(Step.Mode.FLEX, flexSize));
        stepsList.add(new Step(Step.Mode.RELAX, relaxSize));

    }

    public Type getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public double getBalance() {
        return balance;
    }

    public List<Step> getStepsList() {
        return stepsList;
    }

    public enum Type {
        MAX,
        MID,
        MIN,
        UNKNOWN;
    }

    @Override
    public String toString() {
        return stepsList.get(0).getDuration() + " " + stepsList.get(1).getDuration();
    }
}
