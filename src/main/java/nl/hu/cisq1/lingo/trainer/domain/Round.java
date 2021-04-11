package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Mark;
import nl.hu.cisq1.lingo.words.domain.Word;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @Entity @NoArgsConstructor
public class Round {
    @Id
    @GeneratedValue
    private long id;
    private static final int GUESSINGLIMIT = 6;
    @OneToOne
    private Word solution;
    @ElementCollection
    private List<Feedback> feedback = new ArrayList<>();

    public Round(Word solution) {
        this.solution = solution;
    }

    public Feedback addGuess(Word guess) {
        if (!isOngoing()) throw new GameOverException();
        Feedback guessFeedback = Feedback.generateFeedback(solution, guess);
        this.feedback.add(guessFeedback);
        return guessFeedback;
    }

    public String visibleSolution() {
        return Feedback.giveHint(solution, feedback);
    }

    public boolean isFailed() {
        return feedback.stream()
                .filter(feedbackPart -> feedbackPart.stream()
                        .noneMatch(feedbackParts -> feedbackParts.getMark() == Mark.INVALID)
                ).count() >= GUESSINGLIMIT;
    }

    public boolean isOngoing() {
        if (feedback.contains(Feedback.correct(solution))) return false;
        return !isFailed();
    }
}
