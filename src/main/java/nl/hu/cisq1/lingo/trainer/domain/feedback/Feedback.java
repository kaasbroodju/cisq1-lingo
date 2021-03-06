package nl.hu.cisq1.lingo.trainer.domain.feedback;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Feedback extends ArrayList<FeedbackPart> {

    public Feedback() {}

    public Feedback(Collection<? extends FeedbackPart> c) {
        super(c);
    }


    public static Feedback generateFeedback(Word solution, Word guess) {
        Feedback output = new Feedback();

        // return invalid list when word length does not fit.
        if (!solution.getLength().equals(guess.getLength())) return Feedback.invalid(guess);
        if (solution.equals(guess)) return Feedback.correct(guess);

        StringBuilder leftOverStringBuilder = new StringBuilder();

        // check all correct spots
        for (Integer i = 0; i < guess.getLength(); i++) {
            char solutionChar = solution.getValue().charAt(i);
            char guessChar = guess.getValue().charAt(i);
            if (solutionChar == guessChar) {

                output.add(new FeedbackPart(guessChar, Mark.CORRECT));
            } else {
                output.add(new FeedbackPart(guessChar, Mark.INCORRECT));
                leftOverStringBuilder.append(solutionChar);
            }
        }

        String leftOverCharacters = leftOverStringBuilder.toString();

        // check if a spot is on a different location
        if (leftOverCharacters.length() > 1) {
            for (int i = 0; i < output.size(); i++) {
                FeedbackPart part = output.get(i);
                if (part.getMark() == Mark.CORRECT) continue;
                if (leftOverCharacters.indexOf(part.getLetter()) != -1) {
                    leftOverCharacters = leftOverCharacters.replaceFirst(
                            String.valueOf(part.getLetter()), "");
                    output.set(i, new FeedbackPart(part.getLetter(), Mark.DIFFERENTLOCATION));
                }
            }
        }

        return output;
    }

    public static String giveHint(Word solution, List<Feedback> guesses) {
        List<Feedback> filteredGuesses = guesses.stream().filter(feedbackParts -> feedbackParts.size() == solution.getLength()).collect(Collectors.toList());
        return solution.getValue().charAt(0) + IntStream
                .range(1, solution.getLength())
                .mapToObj(i -> filteredGuesses.stream().anyMatch(feedbackParts -> feedbackParts.get(i).getMark() == Mark.CORRECT) ? String.valueOf(solution.getValue().charAt(i)) : ".")
                .collect(Collectors.joining());
    }

    private static Feedback generateAllMark(Word word, Mark mark) {
        return new Feedback(IntStream
                .range(0, word.getLength())
                .mapToObj(i -> new FeedbackPart(word.getValue().charAt(i), mark))
                .collect(Collectors.toList()));

    }

    public static Feedback correct(Word word) {
        return generateAllMark(word, Mark.CORRECT);
    }

    public static Feedback incorrect(Word word) {
        return generateAllMark(word, Mark.INCORRECT);
    }

    public static Feedback invalid(Word word) {
        return generateAllMark(word, Mark.INVALID);
    }
}
