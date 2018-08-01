package com.palotech.pelflex.workout.burner;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TransitoryManager {

    private static List<Transitory> transitoryList = new ArrayList<>();

    public static List<Transitory> getTransitoryList(ExerciseTemplate template) {
        List<Transitory> resultList = new ArrayList<>();

        List<String> keyList = template.getBurnerKeysList();
        for (String key : keyList) {
            Optional<Transitory> optTransitory = transitoryList
                    .stream()
                    .filter(t -> t.getExercise() == template.getExercise())
                    .filter(t -> t.getVariation() == template.getVariation())
                    .filter(t -> key.equals(t.getName()))
                    .sorted(Comparator.comparing(Transitory::getId).reversed())
                    .findFirst();

            Transitory transitory = optTransitory.isPresent() ? optTransitory.get() : template.getTransitoryDefault(key);
            resultList.add(transitory);
        }

        return resultList;
    }

}
