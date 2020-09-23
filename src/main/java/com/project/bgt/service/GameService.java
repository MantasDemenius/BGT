package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.GameCheck;
import com.project.bgt.common.check.ValueCheck;
import com.project.bgt.common.constant.PathConst;
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

  public ResponseEntity createGame(GameDto gameDto) {
    ValueCheck.checkValues(gameDto);
    Language language = languageService.findLanguageByCode(gameDto.getLanguageCode());

    Game newGame = gameRepository.save(
      new Game(
        gameDto.getTitle(),
        gameDto.getDescription(),
        language
      ));

    if(gameDto.getOriginalGameId() != 0){
      Game game = getGame(gameDto.getOriginalGameId());
      newGame.getOriginalGames().add(game);
    }

    gameRepository.save(newGame);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.GAME_PATH, newGame.getId()),
      HttpStatus.CREATED);
  }

  public ResponseEntity updateGame(GameDto newGameDto, long gameId) {
    ValueCheck.checkValues(newGameDto);

    Language language = languageService.findLanguageByCode(newGameDto.getLanguageCode());
    Game game = getGame(gameId);

    game.setTitle(newGameDto.getTitle());
    game.setDescription(newGameDto.getDescription());
    game.setLanguage(language);

    gameRepository.save(game);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  //find error for foreign key violation
  public ResponseEntity deleteGame(long gameId) {
    try {
      gameRepository.deleteById(gameId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.GAME_NOT_FOUND_ID);
    }
  }

  public Game getGame(long gameId) {
    return gameRepository.findById(gameId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.GAME_NOT_FOUND_ID));
  }
}
