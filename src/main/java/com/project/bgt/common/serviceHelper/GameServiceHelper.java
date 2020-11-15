package com.project.bgt.common.serviceHelper;

import com.project.bgt.dto.GameDTO;
import com.project.bgt.model.Game;
import java.util.List;
import java.util.stream.Collectors;

public class GameServiceHelper {

  public List<GameDTO> convertGamesToGameDTOs(List<Game> games) {
    return games.stream()
      .map(this::convertGameToGameDTO)
      .collect(Collectors.toList());
  }

  public GameDTO convertGameToGameDTO(Game game) {
    if(game == null ) return null;
    return new GameDTO(
      game.getId(),
      game.getLanguage().getId(),
      game.getUser().getId(),
      getOriginalGameId(game),
      game.getAuthor(),
      game.getTitle(),
      game.getDescription()
    );
  }

  private long getOriginalGameId(Game game){
    return game.getOriginalGames().isEmpty() ? 0 : game.getOriginalGames().get(0).getId();
  }

}
