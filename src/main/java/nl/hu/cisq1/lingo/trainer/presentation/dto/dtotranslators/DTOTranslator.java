package nl.hu.cisq1.lingo.trainer.presentation.dto.dtotranslators;

public interface DTOTranslator<DTO, S> {
    DTO translateToDTO(S s);
}
