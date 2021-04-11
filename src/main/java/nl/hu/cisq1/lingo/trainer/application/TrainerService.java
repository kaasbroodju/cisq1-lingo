package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    private WordService wordService;
    private SpringGameRepository springGameRepository;

    public TrainerService(WordService wordService, SpringGameRepository springGameRepository) {
        this.wordService = wordService;
        this.springGameRepository = springGameRepository;
    }

    public Game startNewGame() {
        return startNewRound(new Game());
    }

    public Game startNewRound(Long id) {
        return startNewRound(getGame(id));
    }

    private Game startNewRound(Game game) {
        Word solution = new Word(this.wordService.provideRandomWord(game.nextWordSize()));
        Round round = new Round(solution);
        game.addRound(round);
        return this.springGameRepository.save(game);
    }

    public Round makeGuess(Long id, String guess) {
        Game game = getGame(id);
        Round currentRound = game.getCurrentRound();
        currentRound.addGuess(new Word(guess));
        this.springGameRepository.save(game);
        return currentRound;
    }

    public Game getGame(Long id) {
        return this.springGameRepository.findById(id).orElseThrow();
    }
}
