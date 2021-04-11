package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.RoundStillOngoingException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {

    @Test
    @DisplayName("Start a game with a round ready to go for guessing")
    void startNewGame() {
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        WordService mockWordService = mock(WordService.class);
        when(mockRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(mockWordService.provideRandomWord(anyInt())).thenReturn("hutspot");
        TrainerService trainerService = new TrainerService(mockWordService, mockRepository);

        Game game = trainerService.startNewGame();

        assertEquals(1, game.getRounds().size());
        assertDoesNotThrow(() -> game.getCurrentRound());
    }

    @Test
    @DisplayName("Add a new round after a round is finished.")
    void startNewRound() {
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        WordService mockWordService = mock(WordService.class);
        when(mockRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(mockWordService.provideRandomWord(anyInt())).thenReturn("hutspot");
        TrainerService trainerService = new TrainerService(mockWordService, mockRepository);
        Game game = trainerService.startNewGame();
        game.getCurrentRound().addGuess(new Word("hutspot"));
        when(mockRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(game));

        trainerService.startNewRound(1l);

        assertEquals(2, game.getRounds().size());

    }

    @Test
    @DisplayName("Fail to start a round while another round is still going on.")
    void startNewRoundOngoingException() {
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        WordService mockWordService = mock(WordService.class);
        TrainerService trainerService = new TrainerService(mockWordService, mockRepository);
        Game game = new Game();
        game.addRound(new Round(new Word("invalid")));
        when(mockRepository.findById(anyLong())).thenReturn(java.util.Optional.of(game));
        when(mockWordService.provideRandomWord(anyInt())).thenReturn("solution");

        assertThrows(RoundStillOngoingException.class, () -> trainerService.startNewRound(1l));
    }

    @Test
    @DisplayName("Fail to start a round while previous game failed.")
    void startNewRoundGameOverException() {
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        WordService mockWordService = mock(WordService.class);
        TrainerService trainerService = new TrainerService(mockWordService, mockRepository);
        Game game = new Game();
        game.addRound(new Round(new Word("foo")));
        for (int i = 0; i < 6; i++) {
            game.getCurrentRound().addGuess(new Word("bar"));
        }
        when(mockRepository.findById(anyLong())).thenReturn(java.util.Optional.of(game));
        when(mockWordService.provideRandomWord(anyInt())).thenReturn("solution");

        assertThrows(GameOverException.class, () -> trainerService.startNewRound(1l));
    }

    @Test
    @DisplayName("Throw exception when getting a non existing game.")
    void getNonExistingGame() {
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        WordService mockWordService = mock(WordService.class);
        TrainerService trainerService = new TrainerService(mockWordService, mockRepository);

        when(mockRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> trainerService.getGame(-1l));
    }

    @Test
    @DisplayName("User can make a valid guess without errors")
    void makeValidGuess() {
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        WordService mockWordService = mock(WordService.class);
        TrainerService trainerService = new TrainerService(mockWordService, mockRepository);
        Game game = new Game();
        game.addRound(new Round(new Word("foo")));
        when(mockRepository.findById(anyLong())).thenReturn(java.util.Optional.of(game));

        assertDoesNotThrow(() -> trainerService.makeGuess(1l, "bar"));
    }

    @Test
    @DisplayName("Unable to make a guess when the game is over.")
    void makeGuessAfterGameIsOver() {
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        WordService mockWordService = mock(WordService.class);
        TrainerService trainerService = new TrainerService(mockWordService, mockRepository);
        Game game = new Game();
        game.addRound(new Round(new Word("foo")));
        for (int i = 0; i < 6; i++) {
            game.getCurrentRound().addGuess(new Word("bar"));
        }
        when(mockRepository.findById(anyLong())).thenReturn(java.util.Optional.of(game));

        assertThrows(GameOverException.class, () -> trainerService.makeGuess(1l, "foo"));
    }
}