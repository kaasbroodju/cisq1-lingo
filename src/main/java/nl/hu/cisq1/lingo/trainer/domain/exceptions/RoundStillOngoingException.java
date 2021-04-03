package nl.hu.cisq1.lingo.trainer.domain.exceptions;

public class RoundStillOngoingException extends RuntimeException {
    public RoundStillOngoingException() {
        super("A round is still ongoing");
    }
}
