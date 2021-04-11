package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WordTest {

    @Test
    @DisplayName("length is based on given word")
    void lengthBasedOnWord() {
        Word word = new Word("woord");
        int length = word.getLength();
        assertEquals(5, length);
    }

    @Test
    @DisplayName("Equal words")
    void equalWords() {
        Word same = new Word("woord");

        assertEquals(same, same);
        assertEquals(new Word("woord"), new Word("woord"));
    }

    @Test
    @DisplayName("Equal words")
    void notEqualWords() {

        assertNotEquals(new Word("vier"), new Word("vijf"));
        assertNotEquals("vijf", new Word("vijf"));
        assertNotEquals(null, new Word("vijf"));

    }
}
