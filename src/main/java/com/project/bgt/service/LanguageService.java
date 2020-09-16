package com.project.bgt.service;

import com.project.bgt.exception.LanguageException;
import com.project.bgt.model.Language;
import com.project.bgt.repository.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

  private final LanguageRepository languageRepository;

  public LanguageService(LanguageRepository languageRepository){
    this.languageRepository = languageRepository;
  }

  public Language findLanguageByCode(String languageCode){
    LanguageException.checkIfLanguageProvided(languageCode);

    Language language = languageRepository.findByCode(languageCode);
    LanguageException.checkLanguage(language);

    return language;
  }
}
