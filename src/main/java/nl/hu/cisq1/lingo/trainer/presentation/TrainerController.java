package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.RoundStillOngoingException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GameDTO;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessDTO;
import nl.hu.cisq1.lingo.trainer.presentation.dto.RoundDTO;
import nl.hu.cisq1.lingo.trainer.presentation.dto.dtotranslators.GameDTOTranslator;
import nl.hu.cisq1.lingo.trainer.presentation.dto.dtotranslators.RoundDTOTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("lingo")
public class TrainerController {
    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("game/start")
    public GameDTO createNewGame() {
        Game game = null;
        try {
            game = this.trainerService.startNewGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GameDTOTranslator().translateToDTO(game);
    }

    @GetMapping("game/{id}")
    public GameDTO currentGame(@PathVariable Long id) {
        Game game = null;
        try {
            game = this.trainerService.getGame(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GameDTOTranslator().translateToDTO(game);
    }

    @GetMapping("game/{id}/round")
    public RoundDTO currentRound(@PathVariable Long id) {
        Round round = null;
        try {
            round = this.trainerService.getGame(id).getCurrentRound();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RoundDTOTranslator().translateToDTO(round);
    }

    @PostMapping("game/{id}/round")
    public GameDTO createNewRound(@PathVariable Long id) {
        Game game = null;
        try {
            game = this.trainerService.startNewRound(id);
        } catch (GameOverException | RoundStillOngoingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GameDTOTranslator().translateToDTO(game);
    }

    @PostMapping("game/{id}/guess")
    public RoundDTO makeGuess(@PathVariable Long id, @RequestBody GuessDTO guessDTO) {
        Round round = null;
        try {
            round = this.trainerService.makeGuess(id, guessDTO.guess);
        } catch (GameOverException | RoundStillOngoingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RoundDTOTranslator().translateToDTO(round);
    }
}
