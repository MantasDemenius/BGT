package com.project.bgt.service;

import com.project.bgt.model.Card;
import com.project.bgt.repository.CardRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final CardRepository cardRepository;

  public CardService(CardRepository cardRepository){
    this.cardRepository = cardRepository;
  }

  public List<Card> getCards() {
    return cardRepository.findAll();
  }
}
