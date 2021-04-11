package nl.hu.cisq1.lingo.trainer.presentation.dto;

import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.trainer.domain.feedback.Feedback;

import java.util.List;

@NoArgsConstructor
public class RoundDTO {
    public String hint;
    public List<Feedback> feedback;
    public boolean isFailed;
}
