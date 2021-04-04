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

        assertThrows(RoundStillOngoingException.class, () -> game.addRound(new Round(new Word("foobar"))));
    }

    @Test
    @DisplayName("unable to add round if a round is failed")
    void addRoundFailed() {
        Game game = new Game();
        game.addRound(new Round(new Word("stampot")));
        for (int i = 0; i < 6; i++) {
            game.getCurrentRound().addGuess(new Word("hutspot"));
        }

        assertThrows(GameOverException.class, () -> game.addRound(new Round(new Word("stampot"))));
    }

}