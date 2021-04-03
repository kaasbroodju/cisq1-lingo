package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;
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
        return "";
    }

    public boolean isFailed() {
        return false;
    }

    public boolean isOngoing() {
        return false;
    }
}
