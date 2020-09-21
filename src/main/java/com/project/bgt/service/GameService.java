package com.project.bgt.service;

import com.project.bgt.common.check.GameCheck;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.GameDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.GameRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

  public ResponseEntity createGame(GameDto game) throws URISyntaxException {
    GameCheck.checkGame(game);
    Language language = languageService.findLanguageByCode(game.getLanguageCode());

    Game newGame = gameRepository.save(
      new Game(
        game.getTitle(),
        game.getDescription(),
        language
      ));

//    URI location = new URI("http://localhost:5000/api/games/" + newGame.getId());
//    HttpHeaders responseHeaders = new HttpHeaders();
//    responseHeaders.setLocation(location);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


  @Transactional
  public ResponseEntity updateGame(GameDto newGame, long id) {
    GameCheck.checkGame(newGame);

    Game game = gameRepository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.GAME_NOT_FOUND_ID));

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
      throw new RecordNotFoundException(ErrorMessages.GAME_NOT_FOUND_ID);
    }
  }

  public Game getGame(long id) {
    return gameRepository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.GAME_NOT_FOUND_ID));
  }
}
