package nl.hu.cisq1.lingo.trainer.domain.feedback;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

}