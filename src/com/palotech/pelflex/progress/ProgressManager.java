package com.palotech.pelflex.progress;

import com.palotech.pelflex.progress.level.Level;
import com.palotech.pelflex.progress.level.LevelManager;
import com.palotech.pelflex.progress.target.Target;
import com.palotech.pelflex.workout.Workout;

public class ProgressManager {

    public static Progress getProgress(Workout workout) {
        // TODO template is null here
        int xp = workout.getXpReward();
        LevelManager.addXp(xp);
        Level level = LevelManager.getLevel();

        return new Progress(workout, new Target(), level);
    }

}
