package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.pointcalculator.PointCalculatorStrategy;
import nl.hu.cisq1.lingo.trainer.domain.pointcalculator.TraditionalPointCalculatorStrategy;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {
    private int points;
    private static PointCalculatorStrategy pointCalculator = new TraditionalPointCalculatorStrategy();
    private List<Round> rounds = new ArrayList<>();

    public boolean addRound(Round round) {
        return rounds.add(round);
    }

    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }
}
