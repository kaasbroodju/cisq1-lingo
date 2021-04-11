package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.RoundStillOngoingException;
import nl.hu.cisq1.lingo.trainer.domain.pointcalculator.PointCalculatorStrategy;
import nl.hu.cisq1.lingo.trainer.domain.pointcalculator.TraditionalPointCalculatorStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Entity @NoArgsConstructor
public class Game {
    @Id @GeneratedValue
    private Long id;
    private static PointCalculatorStrategy pointCalculator = new TraditionalPointCalculatorStrategy();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Round> rounds = new ArrayList<>();
    private static int startWordSize = 5;
    private static int amountOfWordSizeIncreases = 3;

    public boolean addRound(Round round) throws GameOverException, RoundStillOngoingException{
        if (rounds.stream().anyMatch(Round::isFailed)) throw new GameOverException();
        if (rounds.stream().anyMatch(Round::isOngoing)) throw new RoundStillOngoingException();
        return rounds.add(round);
    }

    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }

    public int nextWordSize() {
        return startWordSize + (rounds.size() % amountOfWordSizeIncreases);
    }

    public int getPoints() {
        return rounds.stream().filter(round -> !round.isOngoing()).mapToInt(r -> pointCalculator.calculatePoints(r.getFeedback())).sum();
    }
}
