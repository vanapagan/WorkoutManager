package com.palotech.pelflex.workout;

import java.util.List;
import java.util.stream.Collectors;

public class Pattern {

    private List<ComplexStep> compStepList;

    public Pattern(List<ComplexStep> compStepList) {
        this.compStepList = compStepList;
    }

    public List<ComplexStep> getCompStepList() {
        return compStepList;
    }

    @Override
    public String toString() {
        return "Duration: " + compStepList.stream().mapToInt(s -> s.getDuration()).sum() + " Pattern: " + compStepList.stream().map(ComplexStep::toString).collect(Collectors.joining(" "));
    }
}
