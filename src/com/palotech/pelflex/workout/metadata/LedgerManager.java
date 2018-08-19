package com.palotech.pelflex.workout.metadata;

import com.palotech.pelflex.workout.burner.Transitory;
import com.palotech.pelflex.workout.burner.TransitoryManager;
import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;
import com.palotech.pelflex.workout.metadata.feedback.FeedbackService;

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
                .filter(l -> !l.isCompleted())
                //.filter(l -> l.getMeasureClipList().stream().anyMatch(m -> m.getGroup() == Measure.Group.DURATION_LENGTH))
                .collect(Collectors.toList()).isEmpty()) {

            // TODO acquire list of keys for Transitories, so that we can fetch their latest values from db
            List<String> transitoryKeysList = exerciseTemplate.getBurnerKeysList();

            // TODO fetch their latest values from the db
            List<Transitory> transitoryList = TransitoryManager.getTransitoryList(exerciseTemplate);

            ledgerList.add(new Ledger(exerciseTemplate, transitoryList));
            System.out.println("Created new Ledger instance");
        }

        // filter(l -> l.getMeasureList().stream().anyMatch(m -> m.getGroup() == Measure.Group.DURATION_LENGTH))

        Optional<Ledger> ledgerOptional = ledgerList
                .stream()
                .filter(l -> l.getExercise() == exercise)
                .filter(l -> l.getVariation() == variation)
                .filter(l -> !l.isCompleted())
                // TODO not implemented yet -> .filter(l -> l.getMeasureList().stream().anyMatch(m -> m.isAlive()))
                // .filter(l -> l.getMeasureClipList().stream().anyMatch(m -> m.getGroup() == Measure.Group.DURATION_LENGTH))
                .sorted(Comparator.comparing(Ledger::getId).reversed())
                .findFirst();


        Ledger ledger = null;
        if (ledgerOptional.isPresent()) {
            ledger = ledgerOptional.get();
            double userFeedbackCoef = FeedbackService.getUserFeedbackCoefficient(exercise, variation);
            ledger.loadMeasuresToClip(userFeedbackCoef);
        }

        return ledger;
    }

}
