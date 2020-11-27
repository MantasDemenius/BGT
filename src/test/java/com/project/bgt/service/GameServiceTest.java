package com.project.bgt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.dto.GameComponentDTO;
import com.project.bgt.dto.GameDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.ComponentCategory;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.model.User;
import com.project.bgt.repository.GameRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

class GameServiceTest {

  @Mock
  private GameRepository mockGameRepository;
  @Mock
  private ComponentService mockComponentService;
  @Mock
  private LanguageService mockLanguageService;
  @Mock
  private UserService mockUserService;

  @InjectMocks
  private GameService gameServiceUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
    gameServiceUnderTest = new GameService();
    gameServiceUnderTest.setGameRepository(mockGameRepository);
    gameServiceUnderTest.setComponentService(mockComponentService);
    gameServiceUnderTest.setLanguageService(mockLanguageService);
    gameServiceUnderTest.setUserService(mockUserService);
  }

  @Test
  void getGames__GameDTOsList() {
    // Setup
    mockFindAll();
    final List<GameDTO> expectedResult = List
      .of(new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description"));
    // Run the test
    final List<GameDTO> result = gameServiceUnderTest.getGames();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void createGame_NotExistingGameDTO_StatusCreatedWithLocation() {
    // Setup
    HttpHeaders responseHeaders = new HttpHeaders();
    mockServletContext();
    URI location = ServletUriComponentsBuilder.
      fromCurrentContextPath().
      path("games/0")
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

    final GameDTO gameDTO = new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description");
    mockLanguageServiceGetLanguage();
    mockUserServiceGetUser();
    mockSave();

    // Run the test
    final ResponseEntity result = gameServiceUnderTest.createGame(gameDTO);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void createGame_NotExistingGameDTOWithOriginalGame_StatusCreatedWithLocation() {
    // Setup
    HttpHeaders responseHeaders = new HttpHeaders();
    mockServletContext();
    URI location = ServletUriComponentsBuilder.
      fromCurrentContextPath().
      path("games/0")
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

    final GameDTO gameDTO = new GameDTO(0L, 0L, 0L, 1L, "author", "title", "description");
    mockFindById();
    mockLanguageServiceGetLanguage();
    mockUserServiceGetUser();
    mockSave();

    // Run the test
    final ResponseEntity result = gameServiceUnderTest.createGame(gameDTO);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void updateGame_ExistingGame_StatusNoContent() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.NO_CONTENT);

    final GameDTO newGameDTO = new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description");
    mockFindById();
    // Run the test
    final ResponseEntity result = gameServiceUnderTest.updateGame(newGameDTO, 0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteGame_ExistingGame_StatusOk() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.OK);
    // Run the test
    final ResponseEntity result = gameServiceUnderTest.deleteGame(0L);

    // Verify the results
    verify(mockGameRepository).deleteById(0L);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteGame_NotExistingGame_RecordNotFoundException() {
    // Setup
    String expectedMessage = "Game with id: 0 was not found!";
    // Run the test
    doThrow(new IllegalArgumentException()).when(mockGameRepository).deleteById(0L);
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      gameServiceUnderTest.deleteGame(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void getGame_ExistingGame_Game() {
    // Setup
    final Game expectedResult = new Game(new Language("name", "code"),
      new User("username", "email", "password"), "author", "title", "description");

    mockFindById();
    // Run the test
    final Game result = gameServiceUnderTest.getGame(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getGame_NotExistingGame_RecordNotFoundException() {
    // Setup
    String expectedMessage = "Game with id: 0 was not found!";
    // Run the test
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      gameServiceUnderTest.getGame(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void getGameDTO_ExistingGame_GameDTO() {
    // Setup
    final GameDTO expectedResult = new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description");
    mockFindById();

    // Run the test
    final GameDTO result = gameServiceUnderTest.getGameDTO(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getGameComponents_GameWithComponents_GameWithComponentsList() {
    // Setup
    final GameComponentDTO expectedResult = new GameComponentDTO();
    expectedResult.setGame(new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description"));
    expectedResult.setComponents(List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES)));
    mockFindById();
    when(mockComponentService.getAllComponentTranslationsDTOByGameId(anyLong())).thenReturn(
      List.of(
        new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES)));

    // Run the test
    final GameComponentDTO result = gameServiceUnderTest.getGameComponents(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getGamesByLanguageId_LanguageWithTranslatedGames_GamesWithThatLanguage() {
    // Setup
    final List<Game> expectedResult = List.of(
      new Game(new Language("name", "code"), new User("username", "email", "password"), "author",
        "title", "description"));
    mockFindAllByLanguageId();
    // Run the test
    final List<Game> result = gameServiceUnderTest.getGamesByLanguageId(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getAllGamesWithComponentsByLanguageId_ExistingGamesWithTranslations_AllLanguageGames() {
    // Setup
    final GameComponentDTO gameComponentDTO = new GameComponentDTO();
    gameComponentDTO.setGame(new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description"));
    gameComponentDTO.setComponents(
      List
        .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES)));
    final List<GameComponentDTO> expectedResult = List.of(gameComponentDTO);
    mockFindAllByLanguageId();
    when(mockComponentService.getComponentDTOsByGameId(0L)).thenReturn(List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES)));

    // Run the test
    final List<GameComponentDTO> result = gameServiceUnderTest
      .getAllGamesWithComponentsByLanguageId(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getOriginalGames_ThereAreOriginalGames_AllOriginalGames() {
    // Setup
    final List<GameDTO> expectedResult = List
      .of(new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description"));
    when(mockGameRepository.findAllOriginalGames()).thenReturn(List.of(
      new Game(new Language("name", "code"), new User("username", "email", "password"), "author",
        "title", "description")));
    // Run the test
    final List<GameDTO> result = gameServiceUnderTest.getOriginalGames();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getGameLanguages_TranslatedGame_Languages() {
    // Setup
    when(mockGameRepository.existsById(anyLong())).thenReturn(true);
    when(mockGameRepository.findAllGameLanguages(anyLong())).thenReturn(List.of("value"));
    // Run the test
    final List<String> result = gameServiceUnderTest.getGameLanguages(0L);

    // Verify the results
    assertThat(result).isEqualTo(List.of("value"));
  }

  @Test
  void gameDoesNotExistById_NotExistingGame_RecordNotFoundException(){
    // Setup
    String expectedMessage = "Game with id: 0 was not found!";
    when(mockGameRepository.existsById(anyLong())).thenReturn(false);
    // Run the test
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      gameServiceUnderTest.gameDoesNotExistById(0L);
    });
    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void gameDoesNotExistById_ExistingGame__(){
    // Setup
    when(mockGameRepository.existsById(anyLong())).thenReturn(true);
    // Run the test
    gameServiceUnderTest.gameDoesNotExistById(0L);
    // Verify the results
  }

  void mockFindById() {
    final Game expectedResult = new Game(new Language("name", "code"),
      new User("username", "email", "password"), "author", "title", "description");

    when(mockGameRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
  }

  void mockFindAll() {
    Language language = new Language("language", "code");
    language.setId(0L);
    User user = new User("username", "email", "password");
    user.setId(0L);
    Game games = new Game(language, user, "author", "title", "description");
    games.setOriginalGames(List.of());
    final List<Game> findAllResult = List.of(games);

    when(mockGameRepository.findAll()).thenReturn(findAllResult);
  }

  void mockFindAllByLanguageId() {
    final List<Game> result = List.of(
      new Game(new Language("name", "code"), new User("username", "email", "password"), "author",
        "title", "description"));
    when(mockGameRepository.findAllByLanguageId(anyLong())).thenReturn(result);
  }

  void mockServletContext() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  private void mockSave() {
    final GameDTO gameDTO = new GameDTO(0L, 0L, 0L, 0L, "author", "title", "description");
    when(mockGameRepository
      .save(new Game(new Language("name", "code"), new User("username", "email", "password"),
        "author", "title", "description")))
      .thenReturn(new Game(new Language("name", "code"), new User("username", "email", "password"),
        "author", "title", "description"));
  }

  void mockLanguageServiceGetLanguage() {
    final Language language = new Language("name", "code");
    language.setId(0L);
    when(mockLanguageService.getLanguage(anyLong())).thenReturn(language);
  }

  void mockUserServiceGetUser() {
    final User expectedResult = new User("username", "email", "password");
    when(mockUserService.getUser(anyLong())).thenReturn(expectedResult);
  }
}
