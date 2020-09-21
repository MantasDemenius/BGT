package com.project.bgt.service;

import com.project.bgt.common.check.CardCheck;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
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
  public ResponseEntity createCard(CardDto card) throws URISyntaxException {
    CardCheck.checkCard(card);
    Language language = languageService.findLanguageByCode(card.getLanguageCode());

    Card newCard = cardRepository.save(
      new Card(
        card.getTitle(),
        card.getDescription(),
        language
      ));
    String ur = System.getenv("DATABASE_URL");
    URI location = new URI(ur+ "/api/cards/" + newCard.getId());
//    URI location = new URI("http://localhost:5000/api/cards/" + newCard.getId());
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(location);
    return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
  }

  @Transactional
  public ResponseEntity updateCard(CardDto newCard, long id) {
    CardCheck.checkCard(newCard);

    Card card = cardRepository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.CARD_NOT_FOUND_ID));

    Language language = languageService.findLanguageByCode(newCard.getLanguageCode());

    card.setTitle(newCard.getTitle());
    card.setDescription(newCard.getDescription());
    card.setLanguage(language);

    cardRepository.save(card);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity deleteCard(long id) {
    try {
      cardRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.CARD_NOT_FOUND_ID);
    }
  }

  public Card getCard(long id) {
    return cardRepository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.CARD_NOT_FOUND_ID));
  }
}
