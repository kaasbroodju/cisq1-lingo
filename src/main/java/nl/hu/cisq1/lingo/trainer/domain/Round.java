package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;
import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.List;

public class Round {
    private static final int GUESSINGLIMIT = 5;
    private Word solution;
    private List<Feedback> feedback;

    public Round(Word solution) {
        this.solution = solution;
    }

    public Feedback addGuess(Word guess) {
        return null;
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
