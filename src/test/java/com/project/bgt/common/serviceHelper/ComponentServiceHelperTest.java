package com.project.bgt.common.serviceHelper;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.model.Component;
import com.project.bgt.model.ComponentCategory;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComponentServiceHelperTest {

  private ComponentServiceHelper componentServiceHelperUnderTest;

  @BeforeEach
  void setUp() {
    componentServiceHelperUnderTest = new ComponentServiceHelper();
  }

  @Test
  void convertComponentsToComponentDTOs_ComponentsList_ComponentDTOsList() {
    // Setup
    final List<Component> Components = List.of(
      new Component(new Language("name", "code"), new User("username", "email", "password"),
        new Game(), "title", "description", ComponentCategory.RULES));
    final List<ComponentDTO> expectedResult = List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES));

    // Run the test
    final List<ComponentDTO> result = componentServiceHelperUnderTest
      .convertComponentsToComponentDTOs(Components);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertComponentToComponentDTO_Component_ComponentDTO() {
    // Setup
    final Component component = new Component(new Language("name", "code"),
      new User("username", "email", "password"), new Game(), "title", "description",
      ComponentCategory.RULES);
    final ComponentDTO expectedResult = new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description",
      ComponentCategory.RULES);

    // Run the test
    final ComponentDTO result = componentServiceHelperUnderTest
      .convertComponentToComponentDTO(component);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertComponentToComponentDTO_ComponentWithOriginal_ComponentDTO() {
    // Setup
    final Component component = new Component(new Language("name", "code"),
      new User("username", "email", "password"), new Game(), "title", "description",
      ComponentCategory.RULES);
    List<Component> originalComponents = List.of(new Component(new Language("name", "code"),
      new User("username", "email", "password"), new Game(), "title", "description",
      ComponentCategory.RULES));
    originalComponents.get(0).setId(1L);
    component.setOriginalComponents(originalComponents);
    final ComponentDTO expectedResult = new ComponentDTO(0L, 0L, 0L, 0L, 1L, "title", "description",
      ComponentCategory.RULES);

    // Run the test
    final ComponentDTO result = componentServiceHelperUnderTest
      .convertComponentToComponentDTO(component);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertComponentToComponentDTO_null_null() {
    // Setup

    // Run the test
    final ComponentDTO result = componentServiceHelperUnderTest
      .convertComponentToComponentDTO(null);

    // Verify the results
    assertThat(result).isEqualTo(null);
  }

  @Test
  void filterComponentDTOByLanguageId_ComponentsList_FilteredComponentsList() {
    // Setup
    final List<ComponentDTO> components = List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES));
    final List<ComponentDTO> expectedResult = List
      .of(new ComponentDTO(0L, 0L, 0L, 0L, 0L, "title", "description", ComponentCategory.RULES));

    // Run the test
    final List<ComponentDTO> result = componentServiceHelperUnderTest
      .filterComponentDTOByLanguageId(components, 0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }
}
