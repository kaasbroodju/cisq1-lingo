package nl.hu.cisq1.lingo.feedback;

public class FeedbackPart {
    private Character letter;
    private Mark mark;

    public FeedbackPart(Character letter, Mark mark) {
        this.letter = letter;
        this.mark = mark;
    }

    public Character getLetter() {
        return letter;
    }

    public Mark getMark() {
        return mark;
    }
}
