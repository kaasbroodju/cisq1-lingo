package nl.hu.cisq1.lingo.trainer.domain.pointcalculator;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.feedback.FeedbackPart;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PointCalculatorStrategyTest {
    @ParameterizedTest
    @DisplayName("return feedback after guess")
    @MethodSource("provideGuessesForAddGuess")
    void calculatePoints(int expected, PointCalculatorStrategy calculatorStrategy) {
        Round round = new Round(new Word("hutspot"));
        String[] guesses = {
                "teaparty",
                "grandpa",
                "orange juice",
                "stampot",
                "hutspot"
        };
        for (String guess : guesses) {
            round.addGuess(new Word(guess));
        }

        assertEquals(expected, calculatorStrategy.CalculatePoints(round.getFeedback()));
    }

    private static Stream<Arguments> provideGuessesForAddGuess() {
        return Stream.of(
                Arguments.of(5, new TraditionalPointCalculatorStrategy())
        );
    }

}