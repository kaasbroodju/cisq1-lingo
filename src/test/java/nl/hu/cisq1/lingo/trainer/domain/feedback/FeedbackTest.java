package nl.hu.cisq1.lingo.trainer.domain.feedback;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackTest {
    @Test
    @DisplayName("Word is guessed correctly when all letters are on the correct position")
    void wordIsGuessed() {
        assertEquals(Feedback.correct(new Word("hutspot")), Feedback.generateFeedback(new Word("hutspot"), new Word("hutspot")));
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
        assertEquals(Feedback.invalid(new Word("hutspot")), Feedback.generateFeedback(new Word("foo"), new Word("hutspot")));
    }

    @Test
    @DisplayName("Word is invalid when amount of letters does not match")
    void guessIsValid() {
        assertEquals(Feedback.incorrect(new Word("bar")), Feedback.generateFeedback(new Word("foo"), new Word("bar")));
    }

    @ParameterizedTest
    @DisplayName("Show if letter is in the word but not on the correct spot")
    @MethodSource("provideFeedbackForGuessIsAtDifferentLocation")
    void guessIsAtDifferentLocation(List expected, Word answer, Word guess) {
        assertEquals(new Feedback(expected), Feedback.generateFeedback(answer, guess));
    }

    private static Stream<Arguments> provideFeedbackForGuessIsAtDifferentLocation() {
        return Stream.of(
                Arguments.of(List.of(
                    new FeedbackPart('r', Mark.INCORRECT),
                    new FeedbackPart('o', Mark.CORRECT),
                    new FeedbackPart('o', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('m', Mark.INCORRECT)),
                        new Word("foto"),
                        new Word("room")
                ),
                Arguments.of(List.of(
                    new FeedbackPart('b', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('a', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('r', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('f', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('o', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('o', Mark.DIFFERENTLOCATION)),
                        new Word("foobar"),
                        new Word("barfoo")
                ),
                Arguments.of(List.of(
                    new FeedbackPart('r', Mark.CORRECT),
                    new FeedbackPart('o', Mark.CORRECT),
                    new FeedbackPart('e', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('o', Mark.DIFFERENTLOCATION),
                    new FeedbackPart('o', Mark.INCORRECT)),
                        new Word("rooie"),
                        new Word("roeoo")
                )
        );
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
                        new ArrayList(Arrays.asList(Feedback.invalid(new Word("foobar"))))
                ),
                Arguments.of(   "f.....",
                        new Word("foobar"),
                        new ArrayList(Arrays.asList(
                            Feedback.incorrect(new Word("foobar"))
                        ))
                ),
                Arguments.of(   "foo",
                        new Word("foo"),
                        new ArrayList(Arrays.asList(
                                Feedback.correct(new Word("foo")),
                                Feedback.incorrect(new Word("foo"))

                                ))
                ),
                Arguments.of(   "foo",
                        new Word("foo"),
                        new ArrayList(Arrays.asList(
                                new Feedback(Arrays.asList(
                                        new FeedbackPart('f', Mark.CORRECT),
                                        new FeedbackPart('o', Mark.CORRECT),
                                        new FeedbackPart('r', Mark.INCORRECT)
                                )),
                                Feedback.correct(new Word("foo")),
                                Feedback.invalid(new Word("foo"))
                        ))
                ),
                Arguments.of(   "fo.",
                        new Word("foo"),
                        new ArrayList(Arrays.asList(
                                new Feedback(Arrays.asList(
                                        new FeedbackPart('o', Mark.DIFFERENTLOCATION),
                                        new FeedbackPart('o', Mark.CORRECT),
                                        new FeedbackPart('f', Mark.DIFFERENTLOCATION)
                                ))
                        ))
                )
        );
    }

}