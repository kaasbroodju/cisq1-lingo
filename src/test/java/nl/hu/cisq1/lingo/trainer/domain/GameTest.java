package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.RoundStillOngoingException;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    @DisplayName("add round to game")
    void addRound() {
        Game game = new Game();
        Word solution = new Word("foobar");
        assertTrue(game.addRound(new Round(solution)));
        game.getCurrentRound().addGuess(solution);
        assertTrue(game.addRound(new Round(solution)));
    }

    @Test
    @DisplayName("unable to add round if a round is ongoing")
    void addRoundOngoing() {
        Game game = new Game();
        game.addRound(new Round(new Word("foobar")));
        Round round = new Round(new Word("foobar"));

        assertThrows(RoundStillOngoingException.class, () -> game.addRound(round));
    }

    @Test
    @DisplayName("unable to add round if a round is failed")
    void addRoundFailed() {
        Game game = new Game();
        game.addRound(new Round(new Word("stampot")));
        for (int i = 0; i < 6; i++) {
            game.getCurrentRound().addGuess(new Word("hutspot"));
        }
        Round round = new Round(new Word("stampot"));

        assertThrows(GameOverException.class, () -> game.addRound(round));
    }

    @Test
    @DisplayName("display size of next round word")
    void nextWordSize() {
        Game game = new Game();
        assertEquals(5, game.nextWordSize());
        game.addRound(new Round(new Word("fooba")));
        assertEquals(6, game.nextWordSize());
        game.getCurrentRound().addGuess(new Word("fooba"));
        game.addRound(new Round(new Word("foobar")));
        assertEquals(7, game.nextWordSize());
        game.getCurrentRound().addGuess(new Word("foobar"));
        game.addRound(new Round(new Word("foobars")));
        assertEquals(5, game.nextWordSize());

    }

    @Test
    @DisplayName("get amount of points scored")
    void getPoints() {
        Game game = new Game();
        game.addRound(new Round(new Word("hutspot")));
        game.getCurrentRound().addGuess(new Word("hutspot"));
        game.addRound(new Round(new Word("hutspot")));
        game.getCurrentRound().addGuess(new Word("hutspot"));

        assertEquals(50, game.getPoints());


    }
}