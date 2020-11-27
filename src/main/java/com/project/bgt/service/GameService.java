package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.common.serviceHelper.ComponentServiceHelper;
import com.project.bgt.common.serviceHelper.GameServiceHelper;
import com.project.bgt.common.serviceHelper.ServiceHelper;
import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.dto.GameComponentDTO;
import com.project.bgt.dto.GameDTO;
import com.project.bgt.exception.BadRequestException;
import com.project.bgt.exception.EmailAlreadyExistsException;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.exception.ResponseEntityBuilder;
import com.project.bgt.exception.UsernameAlreadyExistsException;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.model.User;
import com.project.bgt.repository.GameRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@Service
public class GameService {

  private final GameServiceHelper gameServiceHelper = new GameServiceHelper();
  private LanguageService languageService;
  private ComponentService componentService;
  private UserService userService;
  private GameRepository gameRepository;

  @Autowired
  public void setComponentService(ComponentService componentService) {
    this.componentService = componentService;
  }

  @Autowired
  public void setLanguageService(LanguageService languageService) {
    this.languageService = languageService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setGameRepository(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }


  public List<GameDTO> getGames() {
    return gameServiceHelper.convertGamesToGameDTOs(gameRepository.findAll());
  }

  @Transactional
  public ResponseEntity createGame(GameDTO gameDTO) {
    Language language = languageService.getLanguage(gameDTO.getLanguageId());
    User user = userService.getUser(gameDTO.getUserId());
    ServiceHelper serviceHelper = new ServiceHelper();

    Game newGame = new Game(
      language,
      user,
      gameDTO.getAuthor(),
      gameDTO.getTitle(),
      gameDTO.getDescription()
    );

    if (!serviceHelper.isOriginal(gameDTO.getOriginalGameId())) {
      Game game = getGame(gameDTO.getOriginalGameId());
      newGame.getOriginalGames().add(game);
    }

    gameRepository.save(newGame);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.GAME_PATH, newGame.getId()),
      HttpStatus.CREATED);
  }


  //Only update Author, Description, Title, Language, All the fields are required
  public ResponseEntity updateGame(GameDTO newGameDTO, long gameId) {
    Language language = languageService.getLanguage(newGameDTO.getLanguageId());
    Game game = getGame(gameId);

    game.setLanguage(language);
    game.setAuthor(newGameDTO.getAuthor());
    game.setTitle(newGameDTO.getTitle());
    game.setDescription(newGameDTO.getDescription());

    gameRepository.save(game);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  //find error for foreign key violation
  public ResponseEntity deleteGame(long gameId) {
    try {
      gameRepository.deleteById(gameId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (DataIntegrityViolationException ex) {
      throw new BadRequestException("This game has translated games");
    } catch (Exception ex) {
      throw new RecordNotFoundException("Game with id: " + gameId + " was not found!");
    }
  }

  public Game getGame(long gameId) {
    return gameRepository.findById(gameId)
      .orElseThrow(() -> new RecordNotFoundException("Game with id: " + gameId + " was not found!"));
  }

  public GameDTO getGameDTO(long gameId) {
    return gameServiceHelper.convertGameToGameDTO(getGame(gameId));
  }

  public GameComponentDTO getGameComponents(long gameId) {
    GameComponentDTO gameComponent = new GameComponentDTO();

    GameDTO gameDto = getGameDTO(gameId);
    List<ComponentDTO> components = componentService.getAllComponentTranslationsDTOByGameId(gameId);

    gameComponent.setGame(gameDto);
    gameComponent.setComponents(components);

    return gameComponent;
  }

  public List<Game> getGamesByLanguageId(long languageId) {
    return gameRepository.findAllByLanguageId(languageId);
  }

  public List<GameComponentDTO> getAllGamesWithComponentsByLanguageId(long languageId) {
    List<GameComponentDTO> gameComponents = new ArrayList<GameComponentDTO>();
    List<Game> games = getGamesByLanguageId(languageId);

    for (Game game : games) {
      GameComponentDTO gameComponent = new GameComponentDTO();

      List<ComponentDTO> components = componentService.getComponentDTOsByGameId(game.getId());
      gameComponent.setGame(gameServiceHelper.convertGameToGameDTO(game));
      gameComponent.setComponents(components);

      gameComponents.add(gameComponent);
    }
    return gameComponents;
  }

  public List<GameDTO> getOriginalGames() {
    return gameServiceHelper.convertGamesToGameDTOs(gameRepository.findAllOriginalGames());
  }

  public List<String> getGameLanguages(long gameId) {
    gameDoesNotExistById(gameId);
    return gameRepository.findAllGameLanguages(gameId);
  }

  public void gameDoesNotExistById(long gameId) {
    if(!gameRepository.existsById(gameId)){
      throw new RecordNotFoundException("Game with id: " + gameId + " was not found!");
    }
  }
}

