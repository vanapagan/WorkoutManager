package com.palotech.pelflex.progress.reward;

import com.palotech.pelflex.progress.Progress;
import com.palotech.pelflex.workout.Workout;

public class RewardManager {

    public Reward createReward(Progress progress) {
        Workout workout = progress.getWorkout();

        // TODO Hiljem peaksime ilmselt Rewardi sealt seest v2lja tooma ja ainult integer-i tagastama
        return workout.getMetadata().getExerciseTemplate().calculateReward(progress);
    }

}
