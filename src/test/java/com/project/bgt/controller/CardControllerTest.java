package com.project.bgt.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.bgt.common.Consts;
import com.project.bgt.model.Card;
import com.project.bgt.model.Language;
import com.project.bgt.service.CardService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CardController.class)
class CardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CardService cardService;

  @Test
  void getCards() {
    List<Card> cards = new ArrayList<Card>();
    cards.add(new Card("best", "", new Language()));
    when(cardService.getCards()).thenReturn(cards);

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