package com.project.bgt.controller;

import com.project.bgt.common.Consts;
import com.project.bgt.model.Card;
import com.project.bgt.service.CardService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(Consts.CARDS_PATH)
public class CardController {

  private final CardService cardService;

  public CardController(CardService cardService){
    this.cardService = cardService;
  }

  @GetMapping("")
  public List<Card> getCards(){
    try {
//      throw new Exception();
      return cardService.getCards();
    }catch (Exception ex){
      throw new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR , "Sorry my bad");
    }
  }
}