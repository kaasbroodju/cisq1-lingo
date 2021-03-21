package nl.hu.cisq1.lingo.trainer.domain.pointcalculator;

import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;

import java.util.List;

public interface PointCalculatorStrategy {
    int CalculatePoints(List<Feedback> guesses);
}
