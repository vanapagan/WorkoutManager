package com.palotech.pelflex.progress;

import com.palotech.pelflex.progress.level.Level;
import com.palotech.pelflex.progress.target.Target;
import com.palotech.pelflex.workout.Workout;

public class Progress {

    private Workout workout;
    private Target target;
    private Level level;

    public Progress(Workout workout, Target target, Level level) {
        this.workout = workout;
        this.target = target;
        this.level = level;
    }

    public Workout getWorkout() {
        return workout;
    }

    public Target getTarget() {
        return target;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Level: " + level.getId() + " XP: " + level.getExperiencePoints() + "p NextLevel: " + level.getGoal() + "p Workout reward: " + workout.getMetadata().getExerciseTemplate().calculateXpReward(workout) + "p";
    }
}
