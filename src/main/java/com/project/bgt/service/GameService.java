package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.ValueCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.GameDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.CardRepository;
import com.project.bgt.repository.GameRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private final GameRepository gameRepository;
  private final LanguageService languageService;
  private final CardRepository cardRepository;

  public GameService(GameRepository gameRepository, LanguageService languageService, CardRepository cardRepository) {
    this.gameRepository = gameRepository;
    this.languageService = languageService;
    this.cardRepository = cardRepository;
  }

  public List<GameDto> getGames() {
    return convertGamesToGameDtos(gameRepository.findAll());
  }

  public ResponseEntity createGame(GameDto gameDto) {
    ValueCheck.checkValues(gameDto);
    Language language = languageService.getLanguage(gameDto.getLanguageId());

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

    Language language = languageService.getLanguage(newGameDto.getLanguageId());
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

  public GameDto getGameDto(long gameId){
    return convertGameToGameDto(getGame(gameId));
  }

  private List<GameDto> convertGamesToGameDtos(List<Game> games) {
    return games.stream()
      .map(this::convertGameToGameDto)
      .collect(Collectors.toList());
  }

  private GameDto convertGameToGameDto(Game game) {
    return new GameDto(
      game.getTitle(),
      game.getDescription(),
      game.getLanguage().getId(),
      game.getOriginalGames().isEmpty() ? 0 : game.getOriginalGames().get(0).getId()
    );
  }
}
