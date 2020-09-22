package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.CardDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.service.CardService;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConst.CARDS_PATH)
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
      throw new RecordNotFoundException();
    }
  }

  @GetMapping("{cardId}")
  public Card getCard(@PathVariable(value = "cardId") long cardId) {
    return cardService.getCard(cardId);
  }

  @PostMapping("")
  public ResponseEntity createCard(@RequestBody CardDto cardDto) {
    return cardService.createCard(cardDto);
  }

  @PutMapping("{cardId}")
  public ResponseEntity updateCard(@PathVariable(value = "cardId") long cardId, @RequestBody CardDto cardDto) {
    return cardService.updateCard(cardDto, cardId);
  }

  @DeleteMapping("{cardId}")
  public ResponseEntity deleteCard(@PathVariable(value = "cardId") long cardId) {
    return cardService.deleteCard(cardId);
  }
}