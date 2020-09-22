package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.CardCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
import com.project.bgt.exception.BadRequestException;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Language;
import com.project.bgt.repository.CardRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final CardRepository cardRepository;
  private final LanguageService languageService;

  public CardService(CardRepository cardRepository, LanguageService languageService) {
    this.cardRepository = cardRepository;
    this.languageService = languageService;
  }

  public List<Card> getCards() {
    return cardRepository.findAll();
  }

  @Transactional
  public ResponseEntity createCard(CardDto cardDto) {
    CardCheck.checkCard(cardDto);
    Language language = languageService.findLanguageByCode(cardDto.getLanguageCode());

    Card newCard = cardRepository.save(
      new Card(
        cardDto.getTitle(),
        cardDto.getDescription(),
        language
      ));

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.CARD_TRANSLATION_PATH, newCard.getId()),
      HttpStatus.CREATED);
  }


  public ResponseEntity updateCard(CardDto newCardDto, long cardId) {
    CardCheck.checkCard(newCardDto);

    Language language = languageService.findLanguageByCode(newCardDto.getLanguageCode());
    Card card = getCard(cardId);

    card.setTitle(newCardDto.getTitle());
    card.setDescription(newCardDto.getDescription());
    card.setLanguage(language);

    cardRepository.save(card);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteCard(long cardId) {
    try {
      cardRepository.deleteById(cardId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.CARD_NOT_FOUND_ID);
    }
  }

  public Card getCard(long cardId) {
    return cardRepository.findById(cardId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.CARD_NOT_FOUND_ID));
  }
}
