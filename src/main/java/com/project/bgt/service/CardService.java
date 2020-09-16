package com.project.bgt.service;

import com.project.bgt.exception.CardException;
import com.project.bgt.model.Card;
import com.project.bgt.repository.CardRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CardService {

  private final CardRepository cardRepository;

  public CardService(CardRepository cardRepository){
    this.cardRepository = cardRepository;
  }

  public List<Card> getCards() {
    return cardRepository.findAll();
  }

  @Transactional
  public ResponseEntity createCard(Card card) {
    CardException.checkCard(card);
    try{
      Card newCard = cardRepository.save(
        new Card(
          card.getTitle(),
          card.getDescription()
        ));
      return ResponseEntity.status(HttpStatus.CREATED).body("http://localhost:5000/api/cards/" + newCard.getId());
    }catch(Exception ex){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We are working on it");
    }
  }

  @Transactional
  public ResponseEntity updateCard(Card newCard, long id) {
    CardException.checkCard(newCard);
    Card card = cardRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Card with this id was not found"
        ));

    card.setTitle(newCard.getTitle());
    card.setDescription(newCard.getDescription());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity deleteCard(long id) {
    try{
      cardRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch(Exception ex){
      return new ResponseEntity<>("Card with this id was not found", HttpStatus.BAD_REQUEST);
    }
  }
}
