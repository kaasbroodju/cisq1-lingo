package nl.hu.cisq1.lingo.trainer.domain.exceptions;

public class GameOverException extends RuntimeException {
    public GameOverException() {
        super("Game has ended");
    }
}
