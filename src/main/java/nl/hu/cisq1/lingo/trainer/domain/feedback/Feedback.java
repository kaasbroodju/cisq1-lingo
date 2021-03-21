package nl.hu.cisq1.lingo.trainer.domain.feedback;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.*;

public class Feedback extends ArrayList<FeedbackPart> {
    public static Feedback generateFeedback(Word solution, Word guess) {
        Feedback output = new Feedback();

        // return invalid list when word length does not fit.
//        if (solution.getLength() != guess.getLength()) {
//            for (char c : guess.getValue().toCharArray()) {
//                output.add(new FeedbackPart(c, Mark.INVALID));
//            }
//            return output;
//        }
        for (Integer i = 0; i < guess.getLength(); i++) {
            char solutionChar = solution.getValue().charAt(i);
            char guessChar = solution.getValue().charAt(i);
            if (solutionChar == guessChar) {
                output.add(new FeedbackPart(guessChar, Mark.CORRECT));
            } else {
                output.add(new FeedbackPart(guessChar, Mark.INCORRECT));
            }
        }
        return output;
    }
}
