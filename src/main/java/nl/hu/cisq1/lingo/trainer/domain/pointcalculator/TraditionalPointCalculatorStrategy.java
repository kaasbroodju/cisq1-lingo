package nl.hu.cisq1.lingo.trainer.domain.pointcalculator;

import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;

import java.util.List;

public class TraditionalPointCalculatorStrategy implements PointCalculatorStrategy {
    @Override
    public int CalculatePoints(List<Feedback> guesses) {
        return 0;
    }
}
