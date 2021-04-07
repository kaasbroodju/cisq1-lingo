package nl.hu.cisq1.lingo.trainer.domain.feedback;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
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

    @Override
    public String toString() {
        return letter + " " + mark.name().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackPart part = (FeedbackPart) o;
        return letter.equals(part.letter) && mark == part.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, mark);
    }
}
