package com.palotech.pelflex.workout;

import com.palotech.pelflex.workout.metadata.Difficulty;
import com.palotech.pelflex.workout.metadata.Ledger;
import com.palotech.pelflex.workout.metadata.Metadata;
import com.palotech.pelflex.workout.metadata.pattern.Pattern;
import com.palotech.pelflex.workout.metadata.pattern.PatternMetadata;

public class Kegel extends Workout {

    public Kegel(Metadata metadata) {
        super(metadata);
    }

    @Override
    public int getXpReward() {
        Pattern pattern = getMetadata().getPattern();

        double durationReward = getMetadata().getDifficulty().getDuration() * 10;

        int numberOfSteps = pattern.getCompStepList().size();
        double avgFlexOverRelax = pattern.getFlexPercentage();
        double flexesProportionReward = numberOfSteps * avgFlexOverRelax * 5;

        double variabilityCoefficientReward = pattern.getVariabilityCoefficient() * 2.5d;

        int xp = (int) (durationReward + flexesProportionReward + variabilityCoefficientReward);

        return xp;
    }

}
