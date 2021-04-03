package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Mark;
import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Round {
    private static final int GUESSINGLIMIT = 6;
    private Word solution;
    private List<Feedback> feedback = new ArrayList<>();

    public Round(Word solution) {
        this.solution = solution;
    }

    public Feedback addGuess(Word guess) {
        Feedback guessFeedback = Feedback.generateFeedback(solution, guess);
        this.feedback.add(guessFeedback);
        return guessFeedback;
    }

    public String visibleSolution() {
        return Feedback.giveHint(solution, feedback);
    }

    public boolean isFailed() {
        return feedback.stream()
                .filter(feedbackPart -> !feedbackPart.stream()
                        .anyMatch(feedbackParts -> feedbackParts.getMark() == Mark.INVALID)
                ).count() >= GUESSINGLIMIT;
    }



    public boolean isOngoing() {
        if (feedback.contains(Feedback.correct(solution))) return false;
        return !isFailed();
    }
}
