package com.project.bgt.service;

import com.project.bgt.common.check.LanguageCheck;
import com.project.bgt.model.Language;
import com.project.bgt.repository.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

  private final LanguageRepository languageRepository;

  public LanguageService(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

  public Language findLanguageByCode(String languageCode) {
    LanguageCheck.checkIfLanguageProvided(languageCode);

    Language language = languageRepository.findByCode(languageCode);
    LanguageCheck.checkLanguage(language);

    return language;
  }
}
