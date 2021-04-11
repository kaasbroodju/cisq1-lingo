package nl.hu.cisq1.lingo.trainer.presentation.dto.dtotranslators;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.dto.RoundDTO;

public interface DTOTranslator<DTO, S> {
    DTO translateToDTO(S s);
}
