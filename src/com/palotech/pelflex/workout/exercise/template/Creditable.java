package com.palotech.pelflex.workout.exercise.template;

import com.palotech.pelflex.progress.Progress;
import com.palotech.pelflex.progress.reward.Reward;

public interface Creditable {

    public Reward calculateReward(Progress progress);

}
