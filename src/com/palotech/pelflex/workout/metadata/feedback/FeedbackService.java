package com.palotech.pelflex.workout.metadata.feedback;

import com.palotech.pelflex.workout.exercise.template.ExerciseTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeedbackService {

    private static final double WHOLE = 1.0d;

    private static List<Feedback> feedbackList = new ArrayList<>();

    public static double getUserFeedbackCoefficient(ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation) {
        // TODO Me peame arvestama, millise Workout-iga meil tegemist on
        // TODO Kui kasutaja polnud viimase seda tüüpi Workout-iga rahul, siis me rakendame feedbacki süsteemi, muidu tagastame 1-e
        Optional<Feedback> feedbackOpt = getWorkoutAssociatedFeedback(exercise, variation);

        return feedbackOpt.isPresent() ? feedbackOpt.get().getSatCo() : 0.0d;
    }

    private static Optional<Feedback> getWorkoutAssociatedFeedback(ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation) {
        return feedbackList.stream().filter(f -> f.getExercise() == exercise && f.getVariation() == variation).findFirst();
    }

    public static void addNewFeedback(ExerciseTemplate.Exercise exercise, ExerciseTemplate.Variation variation, double satCo) {
        feedbackList.add(new Feedback(exercise, variation, satCo));
    }

}
