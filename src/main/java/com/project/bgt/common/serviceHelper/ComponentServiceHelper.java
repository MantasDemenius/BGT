package com.project.bgt.common.serviceHelper;

import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.model.Component;
import com.project.bgt.model.Game;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentServiceHelper {

  public List<ComponentDTO> convertComponentsToComponentDTOs(List<Component> Components) {
    return Components.stream()
      .map(this::convertComponentToComponentDTO)
      .collect(Collectors.toList());
  }

  public ComponentDTO convertComponentToComponentDTO(Component component) {
    if(component == null) return null;

    return new ComponentDTO(
      component.getId(),
      component.getLanguage().getId(),
      component.getUser().getId(),
      component.getComponentsGame().get(0).getId(),
      getOriginalComponentId(component),
      component.getTitle(),
      component.getDescription(),
      component.getCategory()
    );
  }

  public List<ComponentDTO> filterComponentDTOByLanguageId(List<ComponentDTO> components, long languageId){
     return components
      .stream()
      .filter(x -> x.languageIdEquals(languageId))
      .collect(Collectors.toList());
  }

  private long getOriginalComponentId(Component component){
    return component.getOriginalComponents().isEmpty() ? 0 : component.getOriginalComponents().get(0).getId();
  }

}
