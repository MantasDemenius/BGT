package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.ComponentCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Component;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.ComponentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComponentService {

  private LanguageService languageService;
  private GameService gameService;
  private ComponentRepository componentRepository;

  @Autowired
  public void setGameService(GameService gameService){
    this.gameService = gameService;
  }
  @Autowired
  public void setLanguageService(LanguageService languageService){
    this.languageService = languageService;
  }
  @Autowired
  public void setComponentRepository(ComponentRepository componentRepository){
    this.componentRepository = componentRepository;
  }


  public List<ComponentDTO> getComponents() {
    return convertComponentsToComponentDtos(componentRepository.findAll());
  }

  public List<Component> getComponentsByGameId(long gameId){
    return componentRepository.findAllByGameId(gameId);
  }

  public List<ComponentDTO> getComponentDTOsByGameId(long gameId){
    return convertComponentsToComponentDtos(getComponentsByGameId(gameId));
  }

  public List<Component> getAllComponentTranslationsByOriginalComponentId(long ComponentId){
    return componentRepository.findAllComponentTranslationsByOriginalComponentId(ComponentId);
  }

  public List<Component> getAllComponentTranslations(List<Component> Components){
    List<Component> allComponents = new ArrayList<Component>();

    for(Component component : Components){
      allComponents.addAll(getAllComponentTranslationsByOriginalComponentId(component.getId()));
    }

    return allComponents;
  }

  @Transactional
  public ResponseEntity createComponent(ComponentDTO componentDto) {
    ComponentCheck.checkComponents(componentDto);
    Language language = languageService.getLanguage(componentDto.getLanguageId());
    Game game = gameService.getGame(componentDto.getGameId());

    Component newComponent = new Component(
      componentDto.getTitle(),
      componentDto.getDescription(),
      language
    );
    newComponent.getComponentsGame().add(game);

    if(componentDto.getOriginalComponentId() != 0){
      Component component = getComponent(componentDto.getOriginalComponentId());
      newComponent.getOriginalComponents().add(component);
    }

    componentRepository.save(newComponent);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.COMPONENTS_PATH, newComponent.getId()),
      HttpStatus.CREATED);
  }


  public ResponseEntity updateComponent(ComponentDTO newComponentDTO, long ComponentId) {
    ComponentCheck.checkComponents(newComponentDTO);

    Language language = languageService.getLanguage(newComponentDTO.getLanguageId());
    Component component = getComponent(ComponentId);

    component.setTitle(newComponentDTO.getTitle());
    component.setDescription(newComponentDTO.getDescription());
    component.setLanguage(language);

    componentRepository.save(component);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteComponent(long ComponentId) {
    try {
      componentRepository.deleteById(ComponentId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.COMPONENT_NOT_FOUND_ID);
    }
  }

  public Component getComponent(long ComponentId) {
    return componentRepository.findById(ComponentId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.COMPONENT_NOT_FOUND_ID));
  }

  public ComponentDTO getComponentDto(long ComponentId){
    return convertComponentToComponentDto(getComponent(ComponentId));
  }

  public List<ComponentDTO> convertComponentsToComponentDtos(List<Component> Components) {
    return Components.stream()
      .map(this::convertComponentToComponentDto)
      .collect(Collectors.toList());
  }

  public ComponentDTO convertComponentToComponentDto(Component component) {
    return new ComponentDTO(
      component.getTitle(),
      component.getDescription(),
      component.getLanguage().getId(),
      component.getComponentsGame().get(0).getId(),
      component.getOriginalComponents().isEmpty() ? 0 : component.getOriginalComponents().get(0).getId()
    );
  }
}
