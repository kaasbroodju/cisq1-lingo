package nl.hu.cisq1.lingo.trainer.domain.feedback;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("Word is guessed correctly when all letters are on the correct position")
    void wordIsGuessed() {
        assertEquals(List.of(
                new FeedbackPart('h', Mark.CORRECT),
                new FeedbackPart('u', Mark.CORRECT),
                new FeedbackPart('t', Mark.CORRECT),
                new FeedbackPart('s', Mark.CORRECT),
                new FeedbackPart('p', Mark.CORRECT),
                new FeedbackPart('o', Mark.CORRECT),
                new FeedbackPart('t', Mark.CORRECT)
        ), Feedback.generateFeedback(new Word("hutspot"), new Word("hutspot")));
    }

    @Test
    @DisplayName("Word is guessed correctly when all letters are on the correct position")
    void wordIsNotGuessed() {
        assertEquals(List.of(
                new FeedbackPart('h', Mark.CORRECT),
                new FeedbackPart('u', Mark.CORRECT),
                new FeedbackPart('d', Mark.INCORRECT),
                new FeedbackPart('s', Mark.CORRECT),
                new FeedbackPart('p', Mark.CORRECT),
                new FeedbackPart('o', Mark.CORRECT),
                new FeedbackPart('t', Mark.CORRECT)
        ), Feedback.generateFeedback(new Word("hutspot"), new Word("hudspot")));
    }

    @Test
    @DisplayName("Word is invalid when amount of letters does not match")
    void guessIsInvalid() {
        assertEquals(List.of(
                new FeedbackPart('h', Mark.INVALID),
                new FeedbackPart('u', Mark.INVALID),
                new FeedbackPart('t', Mark.INVALID),
                new FeedbackPart('s', Mark.INVALID),
                new FeedbackPart('p', Mark.INVALID),
                new FeedbackPart('o', Mark.INVALID),
                new FeedbackPart('t', Mark.INVALID)
        ), Feedback.generateFeedback(new Word("foo"), new Word("hutspot")));
    }

    @Test
    @DisplayName("Word is invalid when amount of letters does not match")
    void guessIsValid() {
        assertEquals(List.of(
                new FeedbackPart('b', Mark.INCORRECT),
                new FeedbackPart('a', Mark.INCORRECT),
                new FeedbackPart('r', Mark.INCORRECT)
        ), Feedback.generateFeedback(new Word("foo"), new Word("bar")));
    }

    // todo naar static test
    @Test
    @DisplayName("Word is invalid when amount of letters does not match")
    void guessIsAtDifferentLocation1() {
        assertEquals(List.of(
                new FeedbackPart('r', Mark.INCORRECT),
                new FeedbackPart('o', Mark.CORRECT),
                new FeedbackPart('o', Mark.DIFFERENTLOCATION),
                new FeedbackPart('m', Mark.INCORRECT)
        ), Feedback.generateFeedback(new Word("foto"), new Word("room")));
    }

    @Test
    @DisplayName("Word is invalid when amount of letters does not match")
    void guessIsAtDifferentLocation2() {
        assertEquals(List.of(
                new FeedbackPart('b', Mark.DIFFERENTLOCATION),
                new FeedbackPart('a', Mark.DIFFERENTLOCATION),
                new FeedbackPart('r', Mark.DIFFERENTLOCATION),
                new FeedbackPart('f', Mark.DIFFERENTLOCATION),
                new FeedbackPart('o', Mark.DIFFERENTLOCATION),
                new FeedbackPart('o', Mark.DIFFERENTLOCATION)
        ), Feedback.generateFeedback(new Word("foobar"), new Word("barfoo")));
    }

    @Test
    @DisplayName("Word is invalid when amount of letters does not match")
    void guessIsAtDifferentLocation3() {
        assertEquals(List.of(
                new FeedbackPart('r', Mark.CORRECT),
                new FeedbackPart('o', Mark.CORRECT),
                new FeedbackPart('e', Mark.DIFFERENTLOCATION),
                new FeedbackPart('o', Mark.DIFFERENTLOCATION),
                new FeedbackPart('o', Mark.INCORRECT)
        ), Feedback.generateFeedback(new Word("rooie"), new Word("roeoo")));
    }

    @ParameterizedTest
    @DisplayName("Show only letters that have been guessed correctly")
    @MethodSource("provideFeedbackForGiveHint")
    void giveHints(String expected, Word solution, List<Feedback> guesses) {
        assertEquals(expected, Feedback.giveHint(solution, guesses));
    }

    private static Stream<Arguments> provideFeedbackForGiveHint() {
        return Stream.of(
                Arguments.of(   "f.....",
                        new Word("foobar"),
                        new ArrayList(Collections.singleton(new Feedback(Arrays.asList(
                                new FeedbackPart('f', Mark.INVALID),
                                new FeedbackPart('o', Mark.INVALID),
                                new FeedbackPart('o', Mark.INVALID),
                                new FeedbackPart('b', Mark.INVALID),
                                new FeedbackPart('a', Mark.INVALID),
                                new FeedbackPart('r', Mark.INVALID)))))
                ),
                Arguments.of(   "f.....",
                        new Word("foobar"),
                        new ArrayList(Collections.singleton(new Feedback(Arrays.asList(
                                new FeedbackPart('f', Mark.INVALID),
                                new FeedbackPart('o', Mark.INVALID),
                                new FeedbackPart('o', Mark.INVALID),
                                new FeedbackPart('b', Mark.INVALID),
                                new FeedbackPart('a', Mark.INVALID),
                                new FeedbackPart('r', Mark.INVALID)))))
                )
        );
    }

}