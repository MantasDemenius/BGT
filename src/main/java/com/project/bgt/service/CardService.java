package com.project.bgt.service;

import com.project.bgt.dto.CardDto;
import com.project.bgt.exception.CardException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Language;
import com.project.bgt.repository.CardRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
  public ResponseEntity createCard(CardDto card) {
    CardException.checkCard(card);
    Language language = languageService.findLanguageByCode(card.getLanguageCode());
    try {

      Card newCard = cardRepository.save(
        new Card(
          card.getTitle(),
          card.getDescription(),
          language
        ));
      return ResponseEntity.status(HttpStatus.CREATED)
        .body("http://localhost:5000/api/cards/" + newCard.getId());
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We are working on it");
    }
  }

  @Transactional
  public ResponseEntity updateCard(CardDto newCard, long id) {
    CardException.checkCard(newCard);

    Card card = cardRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Card with this id was not found"
      ));
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
      return new ResponseEntity<>("Card with this id was not found", HttpStatus.BAD_REQUEST);
    }
  }

  public Card getCard(long id) {
    return cardRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Card with this id was not found"
      ));
  }
}
