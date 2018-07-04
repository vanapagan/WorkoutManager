package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LedgerManager {

    private static List<Ledger> ledgerList = new ArrayList<>();

    public static Ledger getLedger(ExerciseTemplate exerciseTemplate) {
        ExerciseTemplate.Exercise exercise = exerciseTemplate.getExercise();
        ExerciseTemplate.Variation variation = exerciseTemplate.getVariation();

        if (ledgerList
                .stream()
                .filter(l -> l.getExercise() == exercise)
                .filter(l -> l.getVariation() == variation)
                //.filter(l -> l.getMeasureList().stream().anyMatch(m -> m.isAlive()))
                .collect(Collectors.toList()).isEmpty()) {
            ledgerList.add(new Ledger(exerciseTemplate));
        }

        Optional<Ledger> ledgerOptional = ledgerList
                .stream()
                .filter(l -> l.getExercise() == exercise)
                .filter(l -> l.getVariation() == variation)
                // TODO not implemented yet -> .filter(l -> l.getMeasureList().stream().anyMatch(m -> m.isAlive()))
                .sorted(Comparator.comparing(Ledger::getId).reversed())
                .findFirst();

        return ledgerOptional.isPresent() ? ledgerOptional.get() : null;
    }

}
