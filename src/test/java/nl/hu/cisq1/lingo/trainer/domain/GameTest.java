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
        assertTrue(new Game().addRound(new Round(new Word("foobar"))));
    }

    @Test
    @DisplayName("unable to add round if a round is ongoing")
    void addRoundOngoing() {
        Game game = new Game();
        game.addRound(new Round(new Word("foobar")));

        assertThrows(RoundStillOngoingException.class, () -> game.addRound(new Round(new Word("foobar"))));
    }

    @Test
    @DisplayName("unable to add round if a round is failed")
    void addRoundFailed() {
        Game game = new Game();
        game.addRound(new Round(new Word("foobar")));
        for (int i = 0; i < 6; i++) {
            game.getCurrentRound().addGuess(new Word("hutspot"));
        }

        assertThrows(GameOverException.class, () -> game.addRound(new Round(new Word("foobar"))));
    }

}