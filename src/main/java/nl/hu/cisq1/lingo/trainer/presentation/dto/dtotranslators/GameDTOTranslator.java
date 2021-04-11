package nl.hu.cisq1.lingo.trainer.presentation.dto.dtotranslators;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GameDTO;

public class GameDTOTranslator implements DTOTranslator<GameDTO, Game>{
    @Override
    public GameDTO translateToDTO(Game game) {
        GameDTO output = new GameDTO();
        output.id = game.getId();
        output.points = game.getPoints();
        output.currentRound = new RoundDTOTranslator().translateToDTO(game.getCurrentRound());
        output.ongoing = game.getCurrentRound().isOngoing();
        return output;
    }
}
