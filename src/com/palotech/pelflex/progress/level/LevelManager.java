package com.palotech.pelflex.progress.level;

import com.palotech.pelflex.progress.level.bonus.Bonus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LevelManager {

    private static List<Level> levelList = new ArrayList<>();

    public static Level getLevel() {
        if (levelList.isEmpty()) {
            addLevel(getDefaultLevel());
        }
        Optional<Level> level = levelList.stream().sorted(Comparator.comparing(Level::getId).reversed()).findFirst();
        return level.isPresent() ? level.get() : getDefaultLevel();
    }

    public static void addXp(int addXp) {
        Level level = getLevel();
        int overspill = level.addXp(addXp);
        if (overspill > 0) {
            levelUp(level, overspill);
        }
    }

    public static void levelUp(Level previousLevel, int initialValue) {
        int newLevelCap = (int) (Math.round((previousLevel.getGoal() * 1.25d)/10.0) * 10);
        addLevel(new Level(initialValue, newLevelCap, new ArrayList<>()));
        System.out.println("Level up! Congratulations, You have now reached Level " + getLevel().getId() + "! Overspill: " + initialValue);
    }

    private static Level getDefaultLevel() {
        return new Level(0, 1200, new ArrayList<>());
    }

    private static void addLevel(Level level) {
        levelList.add(level);
    }

}
