package com.project.bgt.controller;

import com.project.bgt.common.Consts;
import com.project.bgt.dto.CardDto;
import com.project.bgt.model.Card;
import com.project.bgt.service.CardService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(Consts.CARDS_PATH)
public class CardController {

  private final CardService cardService;

  public CardController(CardService cardService) {
    this.cardService = cardService;
  }

  @GetMapping("")
  public List<Card> getCards() {
    try {
      return cardService.getCards();
    } catch (Exception ex) {
      throw new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR, "We are working on it");
    }
  }

  @GetMapping("{id}")
  public Card getCard(@PathVariable(value = "id") long id) {
    return cardService.getCard(id);
  }

  @PostMapping("")
  public ResponseEntity createCard(@RequestBody CardDto card) {
    return cardService.createCard(card);
  }

  @PutMapping("{id}")
  public ResponseEntity updateCard(@PathVariable(value = "id") long id, @RequestBody CardDto card) {
    return cardService.updateCard(card, id);
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteCard(@PathVariable(value = "id") long id) {
    return cardService.deleteCard(id);
  }
}