package com.project.bgt.controller;

import com.project.bgt.common.Consts;
import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import com.project.bgt.service.CardService;
import com.project.bgt.service.GameService;
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
@RequestMapping(Consts.GAME_PATH)
public class GameController {

//  private final GameService gameService;
//
//  public GameController(GameService gameService){
//    this.gameService = gameService;
//  }
//
//  @GetMapping("")
//  public List<Game> getGames(){
//    try {
//      return cardService.getCards();
//    }catch (Exception ex){
//      throw new ResponseStatusException(
//        HttpStatus.INTERNAL_SERVER_ERROR , "We are working on it");
//    }
//  }
//
//  @PostMapping("")
//  public ResponseEntity createCard(@RequestBody Card card){
//    return cardService.createCard(card);
//  }
//
//  @PutMapping("{id}")
//  public ResponseEntity updateCard(@PathVariable(value = "id") long id, @RequestBody Card card){
//    return cardService.updateCard(card, id);
//  }
//
//  @DeleteMapping("{id}")
//  public ResponseEntity deleteCard(@PathVariable(value = "id") long id){
//    return cardService.deleteCard(id);
//  }
}
