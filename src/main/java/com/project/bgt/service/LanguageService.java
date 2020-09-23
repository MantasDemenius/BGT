package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.LanguageCheck;
import com.project.bgt.common.check.ValueCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
import com.project.bgt.dto.LanguageDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.LanguageRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
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

  public List<LanguageDto> getLanguages() {
    return convertLanguagesToLanguageDtos(languageRepository.findAll());
  }

  public Language getLanguage(long languageId) {
    return languageRepository.findById(languageId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.LANGUAGE_NOT_FOUND_ID));
  }

  public LanguageDto getLanguageDto(long languageId) {
    return convertLanguageToLanguageDto(getLanguage(languageId));
  }

  public ResponseEntity createLanguage(LanguageDto languageDto) {
    LanguageCheck.checkLanguage(languageDto);

    Language newLanguage = new Language(
      languageDto.getName(),
      languageDto.getCode()
    );

    languageRepository.save(newLanguage);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.LANGUAGE_PATH, newLanguage.getId()),
      HttpStatus.CREATED);
  }


  public ResponseEntity updateLanguage(LanguageDto newLanguageDto, long languageId) {
    LanguageCheck.checkLanguage(newLanguageDto);
    Language language = getLanguage(languageId);

    language.setName(newLanguageDto.getName());
    language.setCode(newLanguageDto.getCode());

    languageRepository.save(language);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteLanguage(long languageId) {
    try {
      languageRepository.deleteById(languageId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.LANGUAGE_NOT_FOUND_ID);
    }
  }

  private List<LanguageDto> convertLanguagesToLanguageDtos(List<Language> languages) {
    return languages.stream()
      .map(this::convertLanguageToLanguageDto)
      .collect(Collectors.toList());
  }

  private LanguageDto convertLanguageToLanguageDto(Language language) {
    return new LanguageDto(
      language.getId(),
      language.getName(),
      language.getCode()
    );
  }
}
