package com.project.bgt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Component;
import com.project.bgt.model.ComponentCategory;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.model.User;
import com.project.bgt.repository.ComponentRepository;
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

class ComponentServiceTest {

  @Mock
  private ComponentRepository componentRepository;
  @Mock
  private LanguageService mockLanguageService;
  @Mock
  private UserService mockUserService;
  @Mock
  private GameService mockGameService;
  @InjectMocks
  private ComponentService componentServiceUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
    componentServiceUnderTest = new ComponentService();
    componentServiceUnderTest.setComponentRepository(componentRepository);
    componentServiceUnderTest.setLanguageService(mockLanguageService);
    componentServiceUnderTest.setUserService(mockUserService);
    componentServiceUnderTest.setGameService(mockGameService);
  }

  @Test
  void getComponents__ComponentsList() {
    // Setup
    final List<Component> expectedResult = List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES));
    mockFindAll();
    // Run the test
    final List<Component> result = componentServiceUnderTest.getComponents();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getComponentsDTO__ComponentDTOsList() {
    // Setup
    final List<ComponentDTO> expectedResult = List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L,
        "title", "description", ComponentCategory.RULES));
    mockFindAll();

    // Run the test
    final List<ComponentDTO> result = componentServiceUnderTest.getComponentsDTO();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getComponentsByGameId__ComponentsList() {
    // Setup
    final List<Component> expectedResult = List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES));
    mockFindAllByGameId();
    // Run the test
    final List<Component> result = componentServiceUnderTest.getComponentsByGameId(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getComponentDTOsByGameId__ComponentDTOsList() {
    // Setup
    final List<ComponentDTO> expectedResult = List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES));
    mockFindAllByGameId();
    // Run the test
    final List<ComponentDTO> result = componentServiceUnderTest.getComponentDTOsByGameId(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getAllComponentTranslationsByOriginalComponentId_ExistingComponent_ComponentsList() {
    // Setup
    final List<Component> expectedResult = List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES));
    mockFindAllComponentTranslationsByOriginalComponentId();

    // Run the test
    final List<Component> result = componentServiceUnderTest
      .getAllComponentTranslationsByOriginalComponentId(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getAllComponentTranslationsByOriginalComponentId_NotExistingComponent_EmptyList() {
    // Setup
    final List<Component> expectedResult = List.of();

    // Run the test
    final List<Component> result = componentServiceUnderTest
      .getAllComponentTranslationsByOriginalComponentId(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getAllComponentTranslations_ComponentList_ComponentList() {
    // Setup
    final List<Component> components = List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES));
    final List<Component> expectedResult = List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES));
    mockFindAllComponentTranslationsByOriginalComponentId();
    // Run the test
    final List<Component> result = componentServiceUnderTest
      .getAllComponentTranslations(components);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void createLanguage_NotExistingComponentDTO_StatusCreatedWithLocation() {
    // Setup
    HttpHeaders responseHeaders = new HttpHeaders();
    mockServletContext();
    URI location = ServletUriComponentsBuilder.
      fromCurrentContextPath().
      path("components/0")
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

    final ComponentDTO componentDTO = new ComponentDTO(0L, 1L, 0L, 0L, 0L, "title", "description",
      ComponentCategory.RULES);

    mockLanguageServiceGetLanguage();
    mockUserServiceGetUser();
    mockGameServiceGetGame();
    mockSave();
    // Run the test
    final ResponseEntity result = componentServiceUnderTest.createComponent(componentDTO);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void createLanguage_NotExistingComponentDTOWithOriginalComponent_StatusCreatedWithLocation() {
    // Setup
    HttpHeaders responseHeaders = new HttpHeaders();
    mockServletContext();
    URI location = ServletUriComponentsBuilder.
      fromCurrentContextPath().
      path("components/0")
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

    final ComponentDTO componentDTO = new ComponentDTO(0L, 1L, 0L, 0L, 1L, "title", "description",
      ComponentCategory.RULES);
    mockFindById();
    mockLanguageServiceGetLanguage();
    mockUserServiceGetUser();
    mockGameServiceGetGame();
    mockSave();
    // Run the test
    final ResponseEntity result = componentServiceUnderTest.createComponent(componentDTO);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void updateComponent_ExistingComponent_StatusNoContent() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.NO_CONTENT);

    final ComponentDTO newComponentDTO = new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title",
      "description", ComponentCategory.RULES);
    mockFindById();
    // Run the test
    final ResponseEntity result = componentServiceUnderTest.updateComponent(newComponentDTO, 0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteComponent_ExistingComponent_StatusOk() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.OK);
    // Run the test
    final ResponseEntity result = componentServiceUnderTest.deleteComponent(0L);

    // Verify the results
    verify(componentRepository).deleteById(0L);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteComponent_NotExistingComponent_RecordNotFoundException() {
    // Setup
    String expectedMessage = "Component with id: 0 was not found!";
    // Run the test
    doThrow(new IllegalArgumentException()).when(componentRepository).deleteById(0L);
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      componentServiceUnderTest.deleteComponent(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void getComponent_ExistingComponent_Component() {
    // Setup
    final Component expectedResult = new Component(new Language("name", "code"),
      new User("username", "email", "password"), new Game(), "title", "description",
      ComponentCategory.RULES);
    mockFindById();
    // Run the test
    final Component result = componentServiceUnderTest.getComponent(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getComponent_NotExistingComponent_RecordNotFoundException() {
    // Setup
    String expectedMessage = "Component with id: 0 was not found!";
    // Run the test
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      componentServiceUnderTest.getComponent(0L);
    });
    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void getComponentDTO_ExistingComponent_ComponentDTO() {
    // Setup
    final ComponentDTO expectedResult = new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description",
      ComponentCategory.RULES);
    mockFindById();
    // Run the test
    final ComponentDTO result = componentServiceUnderTest.getComponentDTO(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getAllComponentTranslationsDTOByGameId_ComponentWithTranslations_TranslatedComponentsDTOsList() {
    // Setup
    final List<ComponentDTO> expectedResult = List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES));
    mockFindAllByGameId();
    mockFindAllComponentTranslationsByOriginalComponentId();
    // Run the test
    final List<ComponentDTO> result = componentServiceUnderTest
      .getAllComponentTranslationsDTOByGameId(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  void mockFindAll() {
    when(componentRepository.findAll()).thenReturn(List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES)));
  }

  void mockFindAllByGameId() {
    when(componentRepository.findAllByGameId(0L)).thenReturn(List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES)));
  }

  void mockFindAllComponentTranslationsByOriginalComponentId() {
    when(componentRepository.findAllComponentTranslationsByOriginalComponentId(0L))
      .thenReturn(List.of(
        new Component(new Language("name", "code"), new User("username", "email", "password"),
          new Game(), "title", "description", ComponentCategory.RULES)));
  }

  void mockFindById() {
    final Component expectedResult = new Component(new Language("name", "code"),
      new User("username", "email", "password"), new Game(), "title", "description",
      ComponentCategory.RULES);
    when(componentRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
  }

  private void mockSave() {
    when(componentRepository.save(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES)))
      .thenReturn(
        new Component(new Language("name", "code"), new User("username", "email", "password"),
          new Game(), "title", "description", ComponentCategory.RULES));
  }

  void mockServletContext() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
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

  void mockGameServiceGetGame() {
    final Game expectedResult = new Game(new Language("name", "code"),
      new User("username", "email", "password"), "author", "title", "description");
    when(mockGameService.getGame(anyLong())).thenReturn(expectedResult);
  }
}
