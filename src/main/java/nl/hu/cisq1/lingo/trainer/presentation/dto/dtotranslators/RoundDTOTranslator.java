package nl.hu.cisq1.lingo.trainer.presentation.dto.dtotranslators;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.dto.RoundDTO;

public class RoundDTOTranslator implements DTOTranslator<RoundDTO, Round> {
    @Override
    public RoundDTO translateToDTO(Round round) {
        RoundDTO output = new RoundDTO();
        output.hint = round.visibleSolution();
        output.feedback = round.getFeedback();
        output.isFailed = round.isFailed();
        return output;
    }
}
