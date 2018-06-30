package com.palotech.pelflex.workout.metadata.feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeedbackService {

    private static final double WHOLE = 1.0d;

    private static List<Feedback> feedbackList = new ArrayList<>();

    public static double getUserFeedbackCoefficient(int workoutId) {
        // TODO Me peame arvestama, millise Workout-iga meil tegemist on
        // TODO Kui kasutaja polnud viimase seda tüüpi Workout-iga rahul, siis me rakendame feedbacki süsteemi, muidu tagastame 1-e
        Optional<Feedback> feedbackOpt = getWorkoutAssociatedFeedback(workoutId);

        return feedbackOpt.isPresent() ? feedbackOpt.get().getSatCo() : 0.0d;
    }

    private static Optional<Feedback> getWorkoutAssociatedFeedback(int workoutId) {
        return feedbackList.stream().filter(f -> f.getWorkoutId() == workoutId).findFirst();
    }

    public static void addNewFeedback(int userId, double satCo) {
        feedbackList.add(new Feedback(userId, satCo));
    }

}
