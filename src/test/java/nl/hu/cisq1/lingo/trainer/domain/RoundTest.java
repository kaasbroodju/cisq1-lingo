package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.feedback.FeedbackPart;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Mark;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @ParameterizedTest
    @DisplayName("return feedback after guess")
    @MethodSource("provideGuessesForAddGuess")
    void addGuess(Feedback expected, Word solution, Word guess) {
        assertEquals(expected, new Round(solution).addGuess(guess));
    }

    private static Stream<Arguments> provideGuessesForAddGuess() {
        return Stream.of(
                Arguments.of(Feedback.correct(new Word("foobar")),
                        new Word("foobar"),
                        new Word("foobar")
                ),
                Arguments.of(Feedback.incorrect(new Word("hutspot")),
                        new Word("badging"),
                        new Word("hutspot")
                ),
                Arguments.of(Feedback.invalid(new Word("foobar")),
                        new Word("foo"),
                        new Word("foobar")
                )
        );
    }

    @ParameterizedTest
    @DisplayName("after guess add feedback to feedback list")
    @MethodSource("provideGuessesForAddGuessToFeedback")
    void addGuessToFeedback(List<Feedback> expected, Word solution, Word guess) {
        Round round = new Round(solution);
        round.addGuess(guess);

        assertEquals(expected, round.getFeedback());
    }

    private static Stream<Arguments> provideGuessesForAddGuessToFeedback() {
        return Stream.of(
                Arguments.of(new ArrayList(Arrays.asList(Feedback.invalid(new Word("hutspot")))),
                        new Word("foobar"),
                        new Word("hutspot")
                ),
                Arguments.of(new ArrayList(Arrays.asList(Feedback.invalid(new Word("hutspot")))),
                        new Word("foobar"),
                        new Word("hutspot")
                ),
                Arguments.of(new ArrayList(Arrays.asList(Feedback.invalid(new Word("hutspot")))),
                        new Word("foobar"),
                        new Word("hutspot")
                )
        );
    }

    @Test
    @DisplayName("round is failed after all legit guessing tries have been used")
    void isFailed() {
        Round round = new Round(new Word("stampot"));
        for (int i = 0; i < 6; i++) {
            round.addGuess(new Word("hutspot"));
        }

        assertTrue(round.isFailed());
    }

    @Test
    @DisplayName("round is not failed if there's invalid guesses")
    void isNotFailed() {
        Round round = new Round(new Word("foobar"));
        for (int i = 0; i < 6; i++) {
            round.addGuess(new Word("pizza"));
        }

        assertFalse(round.isFailed());
    }

    @Test
    @DisplayName("round is ongoing when round is created")
    void isOngoing() {
        assertTrue(new Round(new Word("foobar")).isOngoing());
    }

    @Test
    @DisplayName("round is not ongoing when the word is guessed correctly")
    void isNotOngoingWordIsGuessed() {
        Round round = new Round(new Word("foobar"));
        round.addGuess(new Word("foobar"));

        assertFalse(round.isOngoing());
    }

    @Test
    @DisplayName("round is not ongoing when the word is not guessed and all legit attempts are used")
    void isNotOngoingRoundFailed() {
        Round round = new Round(new Word("stampot"));
        for (int i = 0; i < 6; i++) {
            round.addGuess(new Word("hutspot"));
        }

        assertFalse(round.isOngoing());
    }

    @Test
    @DisplayName("always show first letter and all the letters guessed on the correct spot")
    void visibleSolution() {
        Round round = new Round(new Word("stampot"));

        round.addGuess(new Word("hutspot"));


        assertEquals("s...pot", round.visibleSolution());
    }

}