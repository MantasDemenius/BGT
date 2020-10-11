package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.common.serviceHelper.ComponentServiceHelper;
import com.project.bgt.common.serviceHelper.ServiceHelper;
import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.exception.BadRequestException;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Component;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.model.User;
import com.project.bgt.repository.ComponentRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComponentService {

  private final ComponentServiceHelper componentServiceHelper = new ComponentServiceHelper();
  private LanguageService languageService;
  private GameService gameService;
  private UserService userService;
  private ComponentRepository componentRepository;

  @Autowired
  public void setGameService(GameService gameService) {
    this.gameService = gameService;
  }

  @Autowired
  public void setLanguageService(LanguageService languageService) {
    this.languageService = languageService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setComponentRepository(ComponentRepository componentRepository) {
    this.componentRepository = componentRepository;
  }


  public List<ComponentDTO> getComponents() {
    return componentServiceHelper.convertComponentsToComponentDTOs(componentRepository.findAll());
  }

  public List<Component> getComponentsByGameId(long gameId) {
    return componentRepository.findAllByGameId(gameId);
  }

  public List<ComponentDTO> getComponentDTOsByGameId(long gameId) {
    return componentServiceHelper.convertComponentsToComponentDTOs(getComponentsByGameId(gameId));
  }

  public List<Component> getAllComponentTranslationsByOriginalComponentId(long ComponentId) {
    return componentRepository.findAllComponentTranslationsByOriginalComponentId(ComponentId);
  }

  public List<Component> getAllComponentTranslations(List<Component> Components) {
    List<Component> allComponents = new ArrayList<Component>();

    for (Component component : Components) {
      allComponents.addAll(getAllComponentTranslationsByOriginalComponentId(component.getId()));
    }

    return allComponents;
  }

  @Transactional
  public ResponseEntity createComponent(ComponentDTO componentDTO) {
//    ComponentCheck.checkComponents(componentDto);

    ServiceHelper serviceHelper = new ServiceHelper();

    Language language = languageService.getLanguage(componentDTO.getLanguageId());
    User user = userService.getUser(componentDTO.getUserId());
    Game game = gameService.getGame(componentDTO.getGameId());

    Component newComponent = new Component(
      language,
      user,
      game,
      componentDTO.getTitle(),
      componentDTO.getDescription(),
      componentDTO.getCategory()
    );

    if (!serviceHelper.isOriginal(componentDTO.getOriginalComponentId())) {
      Component component = getComponent(componentDTO.getOriginalComponentId());
      newComponent.getOriginalComponents().add(component);
    }

    componentRepository.save(newComponent);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.COMPONENTS_PATH, newComponent.getId()),
      HttpStatus.CREATED);
  }

  //  update title, description
  public ResponseEntity updateComponent(ComponentDTO newComponentDTO, long ComponentId) {
//    ComponentCheck.checkComponents(newComponentDTO);
    Component component = getComponent(ComponentId);

    component.setTitle(newComponentDTO.getTitle());
    component.setDescription(newComponentDTO.getDescription());

    componentRepository.save(component);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteComponent(long ComponentId) {
    try {
      componentRepository.deleteById(ComponentId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (DataIntegrityViolationException ex) {
      throw new BadRequestException("This component has translated games");
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.COMPONENT_NOT_FOUND_ID);
    }
  }

  public Component getComponent(long ComponentId) {
    return componentRepository.findById(ComponentId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.COMPONENT_NOT_FOUND_ID));
  }

  public ComponentDTO getComponentDTO(long ComponentId) {
    return componentServiceHelper.convertComponentToComponentDTO(getComponent(ComponentId));
  }


}
