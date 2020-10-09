package com.project.bgt.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.project.bgt.model.Component;
import com.project.bgt.model.Language;
import com.project.bgt.service.ComponentService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ComponentController.class)
class ComponentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ComponentService componentService;

  @Test
  void getCards() {
    List<Component> components = new ArrayList<Component>();
    components.add(new Component("best", "", new Language()));
//    when(componentService.getCards()).thenReturn(components);

//    this.mockMvc.perform(get(Consts.CARDS_PATH)).andDo(print()).andExpect(status().isOk()).andExpect(content())
  }

  @Test
  void getCard() {
  }

  @Test
  void createCard() {
  }

  @Test
  void updateCard() {
  }

  @Test
  void deleteCard() {
  }
}