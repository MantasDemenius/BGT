package com.project.bgt.common.serviceHelper;

import com.project.bgt.dto.ComponentDTO;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentServiceHelper {

  public List<ComponentDTO> filterComponentDTOByLanguageId(List<ComponentDTO> components, long languageId){
     return components
      .stream()
      .filter(x -> x.languageIdEquals(languageId))
      .collect(Collectors.toList());
  }

}
