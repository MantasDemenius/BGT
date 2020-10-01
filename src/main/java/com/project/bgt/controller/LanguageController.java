package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.LanguageDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Language;
import com.project.bgt.service.LanguageService;
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
@RequestMapping(PathConst.LANGUAGE_PATH)
public class LanguageController {

  private final LanguageService languageService;

  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @GetMapping("")
  public List<LanguageDto> getLanguages() {
    try {
      return languageService.getLanguages();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @GetMapping("{languageId}")
  public LanguageDto getLanguage(@PathVariable(value = "languageId") long languageId) {
    return languageService.getLanguageDto(languageId);
  }

  @PostMapping("")
  public ResponseEntity createLanguage(@RequestBody LanguageDto languageDto) {
    return languageService.createLanguage(languageDto);
  }

  @PutMapping("{languageId}")
  public ResponseEntity updateLanguage(@PathVariable(value = "languageId") long languageId,
    @RequestBody LanguageDto languageDto) {
    return languageService.updateLanguage(languageDto, languageId);
  }

  @DeleteMapping("{languageId}")
  public ResponseEntity deleteLanguage(@PathVariable(value = "languageId") long languageId) {
    return languageService.deleteLanguage(languageId);
  }

  @GetMapping("{languageId}" + PathConst.CARDS_PATH)
  public List<Card> getLanguageCards(@PathVariable(value = "languageId") long languageId){
    return languageService.getLanguageCards(languageId);
  }

  @GetMapping(PathConst.GAME_PATH + "/{gameId}")
  public List<String> getGameLanguages(@PathVariable(value = "gameId") long gameId){
    return languageService.getGameLanguages(gameId);
  }
}