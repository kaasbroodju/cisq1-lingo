package nl.hu.cisq1.lingo.trainer.domain.feedback;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.*;

public class Feedback extends ArrayList<FeedbackPart> {
    public static Feedback generateFeedback(Word solution, Word guess) {
        Feedback output = new Feedback();

        // return invalid list when word length does not fit.
        if (solution.getLength() != guess.getLength()) {
            for (char c : guess.getValue().toCharArray()) {
                output.add(new FeedbackPart(c, Mark.INVALID));
            }
            return output;
        }

        String leftOverCharacters = "";

        // check all correct spots
        for (Integer i = 0; i < guess.getLength(); i++) {
            char solutionChar = solution.getValue().charAt(i);
            char guessChar = guess.getValue().charAt(i);
            if (solutionChar == guessChar) {

                output.add(new FeedbackPart(guessChar, Mark.CORRECT));
            } else {
                output.add(new FeedbackPart(guessChar, Mark.INCORRECT));
                leftOverCharacters += solutionChar;
            }
        }

        // check if a spot is on a different location
        int index;
        for (FeedbackPart feedbackPart : output) {
            index = leftOverCharacters.indexOf(feedbackPart.getLetter());
            System.out.println(leftOverCharacters);
            if (index != -1 && !(feedbackPart.getMark() == Mark.CORRECT)) {
                leftOverCharacters = leftOverCharacters.replaceFirst("" + feedbackPart.getLetter(), "");
                feedbackPart.setMark(Mark.DIFFERENTLOCATION);
            }
        }
        // owo


        return output;
    }

    public static void giveHint(Word solution, List<Feedback> guesses) {

    }
}
