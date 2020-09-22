package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.ValueCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardTranslationDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.CardTranslation;
import com.project.bgt.model.Language;
import com.project.bgt.repository.CardTranslationRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardTranslationService {

  private final CardTranslationRepository cardTranslationRepository;
  private final LanguageService languageService;
  private final CardService cardService;

  public CardTranslationService(CardTranslationRepository cardTranslationRepository,
    LanguageService languageService, CardService cardService) {
    this.cardTranslationRepository = cardTranslationRepository;
    this.languageService = languageService;
    this.cardService = cardService;
  }

  public List<CardTranslation> getCardTranslations() {
    return cardTranslationRepository.findAll();
  }


  public ResponseEntity createCardTranslation(CardTranslationDto cardTranslationDto) {
    ValueCheck.checkValues(cardTranslationDto);
    Language language = languageService.findLanguageByCode(cardTranslationDto.getLanguageCode());
    Card card = cardService.getCard(cardTranslationDto.getCardId());

    CardTranslation newCardTranslation = cardTranslationRepository.save(
      new CardTranslation(
        cardTranslationDto.getTitle(),
        cardTranslationDto.getDescription(),
        language,
        card
      ));

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.CARD_TRANSLATION_PATH, newCardTranslation.getId()),
      HttpStatus.CREATED);
  }

  public CardTranslation getCardTranslation(long cardTranslationId) {
    return cardTranslationRepository.findById(cardTranslationId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.CARD_TRANSLATION_NOT_FOUND_ID));
  }

  public ResponseEntity updateCardTranslation(CardTranslationDto newCardTranslationDto, long cardTranslationId) {
    ValueCheck.checkValues(newCardTranslationDto);

    CardTranslation cardTranslation = getCardTranslation(cardTranslationId);

    Language language = languageService.findLanguageByCode(newCardTranslationDto.getLanguageCode());
    Card card = cardService.getCard(newCardTranslationDto.getCardId());

    cardTranslation.setTitle(newCardTranslationDto.getTitle());
    cardTranslation.setDescription(newCardTranslationDto.getDescription());
    cardTranslation.setLanguage(language);
    cardTranslation.setCard(card);

    cardTranslationRepository.save(cardTranslation);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteCardTranslation(long cardTranslationId) {
    try {
      cardTranslationRepository.deleteById(cardTranslationId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.CARD_TRANSLATION_NOT_FOUND_ID);
    }
  }
}
