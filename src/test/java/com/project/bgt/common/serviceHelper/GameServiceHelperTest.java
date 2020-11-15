package com.project.bgt.common.serviceHelper;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.bgt.dto.GameDTO;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceHelperTest {

  private GameServiceHelper gameServiceHelperUnderTest;

  @BeforeEach
  void setUp() {
    gameServiceHelperUnderTest = new GameServiceHelper();
  }

  @Test
  void convertGamesToGameDTOs_SimpleGameList_ConvertedToListDTO() {
    // Setup
    final List<Game> games = List.of(
      new Game(new Language("name", "code"), new User("username", "email", "password"), "author",
        "title", "description"));
    final List<GameDTO> expectedResult = List
      .of(new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description"));

    // Run the test
    final List<GameDTO> result = gameServiceHelperUnderTest.convertGamesToGameDTOs(games);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertGamesToGameDTOs_NullList_DTONullList() {
    // Setup
    final List<Game> games = List.of();
    final List<GameDTO> expectedResult = List.of();

    // Run the test
    final List<GameDTO> result = gameServiceHelperUnderTest.convertGamesToGameDTOs(games);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertGameToGameDTO_SimpleGame_ConvertedToDTO() {
    // Setup
    final Game game = new Game(new Language("name", "code"),
      new User("username", "email", "password"), "author", "title", "description");
    final GameDTO expectedResult = new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description");

    // Run the test
    final GameDTO result = gameServiceHelperUnderTest.convertGameToGameDTO(game);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertGameToGameDTO_Null_Null() {
    // Setup
    final Game game = null;
    final GameDTO expectedResult = null;

    // Run the test
    final GameDTO result = gameServiceHelperUnderTest.convertGameToGameDTO(game);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }
}
