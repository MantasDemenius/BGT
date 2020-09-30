package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.GameComponentDto;
import com.project.bgt.dto.GameDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.service.GameService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConst.GAME_PATH)
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("")
  public List<GameDto> getGames() {
    try {
      return gameService.getGames();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @GetMapping("{gameId}")
  public GameDto getGame(@PathVariable(value = "gameId") long gameId) {
    return gameService.getGameDto(gameId);
  }

  @PostMapping("")
  public ResponseEntity createGame(@RequestBody GameDto gameDto) {
    return gameService.createGame(gameDto);
  }

  @PutMapping("{gameId}")
  public ResponseEntity updateGame(@PathVariable(value = "gameId") long gameId,
    @RequestBody GameDto gameDto) {
    return gameService.updateGame(gameDto, gameId);
  }

  @DeleteMapping("{gameId}")
  public ResponseEntity deleteGame(@PathVariable(value = "gameId") long gameId) {
    return gameService.deleteGame(gameId);
  }

  @GetMapping("{gameId}" + PathConst.COMPONENTS_PATH)
  public GameComponentDto getGameComponents(@PathVariable(value = "gameId") long gameId,
    @RequestParam(name = "allLanguages") Boolean allLanguages) {
    return gameService.getGameComponents(gameId, allLanguages);
  }

  @GetMapping(PathConst.COMPONENTS_PATH + PathConst.LANGUAGE_PATH + "/{languageId}")
  public List<GameComponentDto> getGameComponentsByLanguageId(
    @PathVariable(value = "languageId") long languageId) {
    return gameService.getGameComponentsByLanguageId(languageId);
  }
}
