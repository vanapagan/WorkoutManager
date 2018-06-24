package com.palotech.pelflex.workout;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Pattern {

    private List<ComplexStep> compStepList;

    public Pattern(List<ComplexStep> compStepList) {
        this.compStepList = compStepList;
    }

    public List<ComplexStep> getCompStepList() {
        return compStepList;
    }

    public double getFlexPercentage() {
        double flexTimeSeconds = compStepList.stream().mapToInt(s -> s.getStepsList().get(0).getDuration()).sum();
        double totalTimeSeconds = getDuration();

        return (flexTimeSeconds / totalTimeSeconds);
    }

    public double getAvgMaxFlexOverRelax() {
        OptionalDouble avgMaxFlexSeconds = compStepList.stream().filter(c -> c.getType() == ComplexStep.Type.MAX).mapToInt(c -> c.getStepsList().get(0).getDuration()).average();
        OptionalDouble avgMaxRelaxSeconds = compStepList.stream().filter(c -> c.getType() == ComplexStep.Type.MAX).mapToInt(c -> c.getStepsList().get(1).getDuration()).average();

        return (avgMaxFlexSeconds.isPresent() ? avgMaxFlexSeconds.getAsDouble() : 1.0d) / (avgMaxRelaxSeconds.isPresent() ? avgMaxRelaxSeconds.getAsDouble() : 1.0d);
    }

    public double getVariabilityCoefficient() {
        OptionalDouble avgFlexDurationOpt = compStepList
                .stream()
                .mapToInt(c -> c.getStepsList().get(0).getDuration())
                .average();

        double deviation = 0;
        if (avgFlexDurationOpt.isPresent()) {
            double avgFlexDuration = avgFlexDurationOpt.getAsDouble();
            for (ComplexStep cs : compStepList) {
                double flexDuration = cs.getStepsList().get(0).getDuration();
                double absDiffValue = Math.abs(Math.sqrt(flexDuration*flexDuration) - Math.sqrt(avgFlexDuration*avgFlexDuration));
                deviation += absDiffValue;
            }
        }
        return deviation / compStepList.size();
    }

    public int getDuration() {
        return compStepList.stream().mapToInt(s -> s.getDuration()).sum();
    }

    @Override
    public String toString() {
        return "Duration: " + compStepList.stream().mapToInt(s -> s.getDuration()).sum() + " Pattern: " + compStepList.stream().map(ComplexStep::toString).collect(Collectors.joining(" "));
    }

}
