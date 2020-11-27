package com.project.bgt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.dto.LanguageDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Language;
import com.project.bgt.repository.LanguageRepository;
import com.project.bgt.security.SecurityService;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

class LanguageServiceTest {
  @Mock
  private SecurityService mockSecurityService;
  @Mock
  private LanguageRepository mockLanguageRepository;
  @InjectMocks
  private LanguageService languageServiceUnderTest;


  @BeforeEach
  void setUp() {
    initMocks(this);
    languageServiceUnderTest = new LanguageService(mockLanguageRepository);
  }

  @Test
  void getLanguages__LanguageDTOsList() {
    // Setup
    final List<LanguageDTO> expectedResult = List.of(new LanguageDTO(0L, "name", "code"));
    mockFindAll();

    // Run the test
    final List<LanguageDTO> result = languageServiceUnderTest.getLanguages();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getLanguage_ExistingLanguageId_Language() {
    // Setup
    final Language expectedResult = new Language("name", "code");
    mockFindById();
    // Run the test
    final Language result = languageServiceUnderTest.getLanguage(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getLanguage_NotExistingLanguageId_RecordNotFoundException() {
    // Setup
    String expectedMessage = "Language with id: 0 was not found!";

    // Run the test
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      languageServiceUnderTest.getLanguage(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void getLanguage__RecordNotFoundException() {
    // Setup
    String expectedMessage = "Language with id: 0 was not found!";
    when(mockLanguageRepository.findById(0L)).thenReturn(Optional.empty());

    // Run the test
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      languageServiceUnderTest.getLanguage(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void getLanguageDto_ExistingLanguageId_LanguageDTO() {
    // Setup
    final LanguageDTO expectedResult = new LanguageDTO(0L, "name", "code");
    mockFindById();

    // Run the test
    final LanguageDTO result = languageServiceUnderTest.getLanguageDTO(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void createLanguage_NotExistingLanguageDTO_StatusCreatedWithLocation() {
    // Setup
    HttpHeaders responseHeaders = new HttpHeaders();
    mockServletContext();
    URI location = ServletUriComponentsBuilder.
      fromCurrentContextPath().
      path("languages/0")
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

    final LanguageDTO languageDTO = new LanguageDTO(0L, "name", "code");
    mockSave();

    // Run the test
    final ResponseEntity result = languageServiceUnderTest.createLanguage(languageDTO);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void updateLanguage_ExistingLanguage_StatusNoContent() {
    // Setup
    final LanguageDTO newLanguageDTO = new LanguageDTO(0L, "name", "code");
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.NO_CONTENT);
    mockFindById();
    mockSave();

    // Run the test
    final ResponseEntity result = languageServiceUnderTest.updateLanguage(newLanguageDTO, 0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteLanguage_ExistingLanguage_StatusOk() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.OK);
    // Run the test
    final ResponseEntity result = languageServiceUnderTest.deleteLanguage(0L);

    // Verify the results
    verify(mockLanguageRepository).deleteById(0L);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteLanguage_NotExistingLanguage_RecordNotFoundException() {
    // Setup
    String expectedMessage = "Language with id: 0 was not found!";
    // Run the test
    doThrow(new IllegalArgumentException()).when(mockLanguageRepository).deleteById(0L);
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      languageServiceUnderTest.deleteLanguage(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  void mockFindAll(){
    when(mockLanguageRepository.findAll()).thenReturn(List.of(new Language("name", "code")));
  }

  void mockFindById(){
    final Optional<Language> language = Optional.of(new Language("name", "code"));
    when(mockLanguageRepository.findById(0L)).thenReturn(language);
  }

  private void mockSave() {
    when(mockLanguageRepository.save(new Language("name", "code")))
      .thenReturn(new Language("name", "code"));
  }

  void mockServletContext(){
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }
}
