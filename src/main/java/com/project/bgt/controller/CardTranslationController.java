package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.CardDto;
import com.project.bgt.dto.CardTranslationDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.CardTranslation;
import com.project.bgt.service.CardTranslationService;
import java.net.URISyntaxException;
import java.util.List;
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
@RequestMapping(PathConst.CARD_TRANSLATION_PATH)
public class CardTranslationController {

  private final CardTranslationService cardTranslationService;

  public CardTranslationController(CardTranslationService cardTranslationService) {
    this.cardTranslationService = cardTranslationService;
  }

  @GetMapping("")
  public List<CardTranslation> getCardTranslations() {
    try {
      return cardTranslationService.getCardTranslations();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @GetMapping("{cardTranslationId}")
  public CardTranslation getCardTranslation(@PathVariable(value = "cardTranslationId") long cardTranslationId) {
    return cardTranslationService.getCardTranslation(cardTranslationId);
  }

  @PostMapping("")
  public ResponseEntity createCardTranslation(@RequestBody CardTranslationDto cardTranslationDto) {
    return cardTranslationService.createCardTranslation(cardTranslationDto);
  }

  @PutMapping("{cardTranslationId}")
  public ResponseEntity updateCardTranslation(
    @PathVariable(value = "cardTranslationId") long cardTranslationId,
    @RequestBody CardTranslationDto cardTranslationDto) {
    return cardTranslationService.updateCardTranslation(cardTranslationDto, cardTranslationId);
  }

  @DeleteMapping("{cardTranslationId}")
  public ResponseEntity deleteCardTranslation(@PathVariable(value = "cardTranslationId") long cardTranslationId) {
    return cardTranslationService.deleteCardTranslation(cardTranslationId);
  }
}
