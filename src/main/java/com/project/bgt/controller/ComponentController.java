package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.service.ComponentService;
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
@RequestMapping(PathConst.PATH + PathConst.COMPONENTS_PATH)
public class ComponentController {

  private final ComponentService componentService;

  public ComponentController(ComponentService componentService) {
    this.componentService = componentService;
  }

  @GetMapping("")
  public List<ComponentDTO> getComponents() {
    try {
      return componentService.getComponentsDTO();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @GetMapping("/{componentId}")
  public ComponentDTO getComponentById(@PathVariable(value = "componentId") long componentId) {
    return componentService.getComponentDTO(componentId);
  }

  @PreAuthorize("hasAnyAuthority('CREATOR', 'ADMIN')")
  @PostMapping("")
  public ResponseEntity createComponent(@Valid @RequestBody ComponentDTO componentDto) {
    return componentService.createComponent(componentDto);
  }

  @PreAuthorize("hasAnyAuthority('CREATOR', 'ADMIN')")
  @PutMapping("/{componentId}")
  public ResponseEntity updateComponent(@PathVariable(value = "componentId") long componentId, @Valid @RequestBody ComponentDTO componentDto) {
    return componentService.updateComponent(componentDto, componentId);
  }

  @PreAuthorize("hasAnyAuthority('CREATOR', 'ADMIN')")
  @DeleteMapping("/{componentId}")
  public ResponseEntity deleteComponent(@PathVariable(value = "componentId") long componentId) {
    return componentService.deleteComponent(componentId);
  }
}