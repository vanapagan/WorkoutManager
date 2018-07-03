package com.palotech.pelflex.progress;

import com.palotech.pelflex.progress.level.Level;
import com.palotech.pelflex.progress.level.LevelManager;
import com.palotech.pelflex.progress.target.Target;
import com.palotech.pelflex.workout.Workout;

public class ProgressManager {

    public static Progress getProgress(Workout workout) {
        int xp = workout.getMetadata().getExerciseTemplate().calculateXpReward(workout);
        LevelManager.addXp(xp);
        Level level = LevelManager.getLevel();

        return new Progress(workout, new Target(), level);
    }

}
