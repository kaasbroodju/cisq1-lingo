package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService service;

    @Test
    @DisplayName("Start new game with an opening game.")
    void newGame() {
        assertEquals(1, this.service.startNewGame().getRounds().size());
    }

    @Test
    @DisplayName("After a round is done create a new round.")
    void newRound() {
        Game game = this.service.startNewGame();
        this.service.makeGuess(game.getId(), game.getCurrentRound().getSolution().getValue());

        this.service.startNewRound(game.getId());

        assertEquals(2, this.service.getGame(game.getId()).getRounds().size());
    }

    @Test
    @DisplayName("Make a simple guess.")
    void makeGuess() {
        Game game = this.service.startNewGame();

        game.getCurrentRound().addGuess(new Word("random guess what is definitely invalid"));

        assertEquals(1, game.getCurrentRound().getFeedback().size());
    }

    @Test
    @DisplayName("Get a game with an id")
    void getGameWithAnId() {
        Game game = this.service.startNewGame();

        assertEquals(game.getId(), this.service.getGame(game.getId()).getId());
    }
}
