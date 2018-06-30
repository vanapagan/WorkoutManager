package com.palotech.pelflex.workout.exercise.template.suggested;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

public class SuggestedVariation {

    private ExerciseTemplate.Variation variation;
    private long noOfOccurs;

    public SuggestedVariation(ExerciseTemplate.Variation variation, long noOfOccurs) {
        this.variation = variation;
        this.noOfOccurs = noOfOccurs;
    }

    public ExerciseTemplate.Variation getVariation() {
        return variation;
    }

    public long getNoOfOccurs() {
        return noOfOccurs;
    }

}
