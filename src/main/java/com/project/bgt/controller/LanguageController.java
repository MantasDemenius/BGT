package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.LanguageDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.service.LanguageService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConst.PATH + PathConst.LANGUAGE_PATH)
public class LanguageController {

  private final LanguageService languageService;

  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @GetMapping("")
  public List<LanguageDTO> getLanguages() {
    try {
      return languageService.getLanguages();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @GetMapping("{languageId}")
  public LanguageDTO getLanguage(@PathVariable(value = "languageId") long languageId) {
    return languageService.getLanguageDTO(languageId);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("")
  public ResponseEntity createLanguage(@Valid @RequestBody LanguageDTO languageDto) {
    return languageService.createLanguage(languageDto);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("{languageId}")
  public ResponseEntity updateLanguage(@PathVariable(value = "languageId") long languageId,
    @Valid @RequestBody LanguageDTO languageDto) {
    return languageService.updateLanguage(languageDto, languageId);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("{languageId}")
  public ResponseEntity deleteLanguage(@PathVariable(value = "languageId") long languageId) {
    return languageService.deleteLanguage(languageId);
  }
}