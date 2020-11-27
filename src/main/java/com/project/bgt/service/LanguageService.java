package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.LanguageDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Language;
import com.project.bgt.repository.LanguageRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

  private final LanguageRepository languageRepository;

  public LanguageService(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }
//  public Language findLanguageByCode(String languageCode) {
//
//    Language language = languageRepository.findByCode(languageCode);
//    LanguageCheck.checkIfLanguageProvided(language);
//
//    return language;
//  }

  public List<LanguageDTO> getLanguages() {
    return convertLanguagesToLanguageDTOs(languageRepository.findAll());
  }

  public Language getLanguage(long languageId) {
    return languageRepository.findById(languageId)
      .orElseThrow(() -> new RecordNotFoundException("Language with id: " + languageId + " was not found!"));
  }

  public LanguageDTO getLanguageDTO(long languageId) {
    return convertLanguageToLanguageDTO(getLanguage(languageId));
  }

  public ResponseEntity createLanguage(LanguageDTO languageDto) {

    Language newLanguage = new Language(
      languageDto.getName(),
      languageDto.getCode()
    );

    languageRepository.save(newLanguage);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.LANGUAGE_PATH, newLanguage.getId()),
      HttpStatus.CREATED);
  }


  public ResponseEntity updateLanguage(LanguageDTO newLanguageDTO, long languageId) {
    Language language = getLanguage(languageId);

    language.setName(newLanguageDTO.getName());
    language.setCode(newLanguageDTO.getCode());

    languageRepository.save(language);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteLanguage(long languageId) {
    try {
      languageRepository.deleteById(languageId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException("Language with id: " + languageId + " was not found!");
    }
  }

  private List<LanguageDTO> convertLanguagesToLanguageDTOs(List<Language> languages) {
    return languages.stream()
      .map(this::convertLanguageToLanguageDTO)
      .collect(Collectors.toList());
  }

  private LanguageDTO convertLanguageToLanguageDTO(Language language) {
    return new LanguageDTO(
      language.getId(),
      language.getName(),
      language.getCode()
    );
  }
}
