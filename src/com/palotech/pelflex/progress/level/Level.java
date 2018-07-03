package com.palotech.pelflex.progress.level;

import com.palotech.pelflex.progress.level.bonus.Bonus;

import java.util.List;

public class Level {

    private static int idCount;

    private int id;
    private int xp;
    private int goal;
    private List<Bonus> bonusList;

    public Level(int experiencePoints, int goal, List<Bonus> bonusList) {
        this.id = ++idCount;
        this.xp = experiencePoints;
        this.goal = goal;
        this.bonusList = bonusList;
    }

    public int getId() {
        return id;
    }

    public int getExperiencePoints() {
        return xp;
    }

    public int getGoal() {
        return goal;
    }

    public List<Bonus> getBonusList() {
        return bonusList;
    }

    public int addXp(int addXp) {
        xp += addXp;
        int overspill = xp - goal;
        return overspill > 0 ? overspill : 0;
    }

}
