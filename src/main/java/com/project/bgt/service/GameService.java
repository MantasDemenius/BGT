package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.ComponentCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
import com.project.bgt.dto.GameComponentDto;
import com.project.bgt.dto.GameDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.GameRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private LanguageService languageService;
  private CardService cardService;

  private GameRepository gameRepository;

  @Autowired
  public void setCardService(CardService cardService){
    this.cardService = cardService;
  }
  @Autowired
  public void setLanguageService(LanguageService languageService){
    this.languageService = languageService;
  }
  @Autowired
  public void setGameRepository(GameRepository gameRepository){
    this.gameRepository = gameRepository;
  }


  public List<GameDto> getGames() {
    return convertGamesToGameDtos(gameRepository.findAll());
  }

  public ResponseEntity createGame(GameDto gameDto) {
    ComponentCheck.checkComponents(gameDto);
    Game originalGame = getGame(gameDto.getOriginalGameId());
    Language language = languageService.getLanguage(gameDto.getLanguageId());

    Game newGame = gameRepository.save(
      new Game(
        gameDto.getTitle(),
        gameDto.getDescription(),
        language
      ));

    if (gameDto.getOriginalGameId() != 0) {
      Game game = getGame(gameDto.getOriginalGameId());
      newGame.getOriginalGames().add(game);
    }

    gameRepository.save(newGame);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.GAME_PATH, newGame.getId()),
      HttpStatus.CREATED);
  }



  public ResponseEntity updateGame(GameDto newGameDto, long gameId) {
    ComponentCheck.checkComponents(newGameDto);

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

  public GameDto getGameDto(long gameId) {
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

  public GameComponentDto getGameComponents(long gameId, Boolean allLanguages) {
    GameComponentDto gameComponent = new GameComponentDto();

    GameDto gameDto = getGameDto(gameId);
    List<CardDto> cards = cardService.convertCardsToCardDtos(
      cardService.getAllCardTranslations(cardService.getCardsByGameId(gameId)));

    if(!allLanguages){
      cards = cards
        .stream()
        .filter(x -> x.languageIdEquals(gameDto.getLanguageId()))
        .collect(Collectors.toList());
    }

    gameComponent.setGame(gameDto);
    gameComponent.setCards(cards);

    return gameComponent;
  }

  public List<Game> getGamesByLanguageId(long languageId){
    return gameRepository.findAllByLanguageId(languageId);
  }

  public List<GameComponentDto> getGameComponentsByLanguageId(long languageId) {
    List<GameComponentDto> gameComponents = new ArrayList<GameComponentDto>();
    List<Game> games = getGamesByLanguageId(languageId);

    for(Game game : games){
      GameComponentDto gameComponent = new GameComponentDto();

      List<CardDto> cards = cardService.getCardDtosByGameId(game.getId());
      gameComponent.setGame(convertGameToGameDto(game));
      gameComponent.setCards(cards);

      gameComponents.add(gameComponent);
    }
    return gameComponents;
  }

  public List<GameDto> getOriginalGames() {
    return convertGamesToGameDtos(gameRepository.findAllOriginalGames());
  }
}

