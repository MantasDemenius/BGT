package com.project.bgt.controller;

import com.project.bgt.common.Consts;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
