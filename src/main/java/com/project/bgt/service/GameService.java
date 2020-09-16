package com.project.bgt.service;

import com.project.bgt.dto.GameDto;
import com.project.bgt.exception.GameException;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.GameRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GameService {

  private final GameRepository gameRepository;
  private final LanguageService languageService;

  public GameService(GameRepository gameRepository, LanguageService languageService) {
    this.gameRepository = gameRepository;
    this.languageService = languageService;
  }

  public List<Game> getGames() {
    return gameRepository.findAll();
  }

  public ResponseEntity createGame(GameDto game) {
    GameException.checkGame(game);
    Language language = languageService.findLanguageByCode(game.getLanguageCode());
    try {

      Game newGame = gameRepository.save(
        new Game(
          game.getTitle(),
          game.getDescription(),
          language
        ));
      return ResponseEntity.status(HttpStatus.CREATED)
        .body("http://localhost:5000/api/games/" + newGame.getId());
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We are working on it");
    }
  }


  @Transactional
  public ResponseEntity updateGame(GameDto newGame, long id) {
    GameException.checkGame(newGame);

    Game game = gameRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Game with this id was not found"
      ));
    Language language = languageService.findLanguageByCode(newGame.getLanguageCode());

    game.setTitle(newGame.getTitle());
    game.setDescription(newGame.getDescription());
    game.setLanguage(language);

    gameRepository.save(game);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity deleteGame(long id) {
    try {
      gameRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>("Game with this id was not found", HttpStatus.BAD_REQUEST);
    }
  }

  public Game getGame(long id) {
    return gameRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Game with this id was not found"
      ));
  }
}
