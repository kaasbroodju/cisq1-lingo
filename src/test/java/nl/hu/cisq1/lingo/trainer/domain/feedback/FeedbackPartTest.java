package nl.hu.cisq1.lingo.trainer.domain.feedback;

import nl.hu.cisq1.lingo.trainer.domain.Game;
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

class FeedbackPartTest {

    @Test
    @DisplayName("Feedbackpart equal to eachother.")
    void equalFeedbackParts() {
        FeedbackPart self = new FeedbackPart('a', Mark.CORRECT);

        assertEquals(self, self);
        assertEquals(new FeedbackPart('a', Mark.CORRECT), new FeedbackPart('a', Mark.CORRECT));
    }

    @ParameterizedTest
    @DisplayName("Feedbackparts equal to eachother.")
    @MethodSource("provideFeedbackPartsForNotEquals")
    void giveHints(Object a, Object b) {
        assertNotEquals(a, b);
    }


    private static Stream<Arguments> provideFeedbackPartsForNotEquals() {
        return Stream.of(
                Arguments.of(
                        new FeedbackPart('a', Mark.CORRECT),
                        new FeedbackPart('a', Mark.INCORRECT)
                ),
                Arguments.of(
                        new FeedbackPart('a', Mark.CORRECT),
                        new FeedbackPart('b', Mark.CORRECT)
                ),
                Arguments.of(
                        new FeedbackPart('a', Mark.CORRECT),
                        new FeedbackPart('b', Mark.INCORRECT)
                ),
                Arguments.of(
                        new FeedbackPart('a', Mark.INCORRECT),
                        new FeedbackPart('b', Mark.INCORRECT)
                ),
                Arguments.of(
                        new FeedbackPart('a', Mark.INCORRECT),
                        new Game()
                )

        );
    }

}