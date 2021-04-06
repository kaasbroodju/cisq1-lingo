package nl.hu.cisq1.lingo.trainer.domain.pointcalculator;

import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Mark;

import java.util.List;

public class TraditionalPointCalculatorStrategy implements PointCalculatorStrategy {
    @Override
    public int CalculatePoints(List<Feedback> guesses) {
        return (int) ((5 * (5 - guesses.stream().filter(g -> !g.stream().anyMatch(fp -> fp.getMark() == Mark.INVALID)).count())) + 5);
    }
}
