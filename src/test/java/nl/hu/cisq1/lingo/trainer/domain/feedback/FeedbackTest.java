package nl.hu.cisq1.lingo.trainer.domain.feedback;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Array;
import java.util.*;
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
                        new ArrayList(Arrays.asList(new Feedback(Arrays.asList(
                                new FeedbackPart('f', Mark.INVALID),
                                new FeedbackPart('o', Mark.INVALID),
                                new FeedbackPart('o', Mark.INVALID),
                                new FeedbackPart('b', Mark.INVALID),
                                new FeedbackPart('a', Mark.INVALID),
                                new FeedbackPart('r', Mark.INVALID)))))
                ),
                Arguments.of(   "f.....",
                        new Word("foobar"),
                        new ArrayList(Arrays.asList(
                            new Feedback(Arrays.asList(
                                new FeedbackPart('f', Mark.INCORRECT),
                                new FeedbackPart('o', Mark.INCORRECT),
                                new FeedbackPart('o', Mark.INCORRECT),
                                new FeedbackPart('b', Mark.INCORRECT),
                                new FeedbackPart('a', Mark.INCORRECT),
                                new FeedbackPart('r', Mark.INCORRECT)
                            ))
                        ))
                ),
                Arguments.of(   "foo",
                        new Word("foo"),
                        new ArrayList(Arrays.asList(
                                new Feedback(Arrays.asList(
                                        new FeedbackPart('f', Mark.CORRECT),
                                        new FeedbackPart('o', Mark.CORRECT),
                                        new FeedbackPart('o', Mark.CORRECT)
                                )),
                                new Feedback(Arrays.asList(
                                        new FeedbackPart('f', Mark.INCORRECT),
                                        new FeedbackPart('o', Mark.INCORRECT),
                                        new FeedbackPart('o', Mark.INCORRECT)
                                ))
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
                                new Feedback(Arrays.asList(
                                        new FeedbackPart('f', Mark.CORRECT),
                                        new FeedbackPart('o', Mark.CORRECT),
                                        new FeedbackPart('o', Mark.CORRECT)
                                )),
                                new Feedback(Arrays.asList(
                                        new FeedbackPart('f', Mark.INVALID),
                                        new FeedbackPart('o', Mark.INVALID),
                                        new FeedbackPart('o', Mark.INVALID)
                                ))
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