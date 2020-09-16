package com.project.bgt.service;

import com.project.bgt.exception.CardException;
import com.project.bgt.model.Card;
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

  public CardService(CardRepository cardRepository){
    this.cardRepository = cardRepository;
  }

  public List<Card> getCards() {
    return cardRepository.findAll();
  }

  @Transactional
  public Card createCard(Card card) {
    CardException.checkCard(card);

    return cardRepository.save(
      new Card(
        card.getTitle(),
        card.getDescription()
      )
    );
  }

  @Transactional
  public Card updateCard(Card newCard, long id) {
    CardException.checkCard(newCard);
    Card card = cardRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Card with this id was not found"
        ));

    card.setTitle(newCard.getTitle());
    card.setDescription(newCard.getDescription());

    return card;
  }

  public ResponseEntity deleteCard(long id) {
    try{
      cardRepository.deleteById(id);
      return new ResponseEntity(HttpStatus.OK);
    }catch(Exception ex){
      return new ResponseEntity("Card with this id was not found", HttpStatus.BAD_REQUEST);
    }
  }
}
