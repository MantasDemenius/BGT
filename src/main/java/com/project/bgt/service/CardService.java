package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.ComponentCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.CardRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private LanguageService languageService;
  private GameService gameService;
  private CardRepository cardRepository;

  @Autowired
  public void setGameService(GameService gameService){
    this.gameService = gameService;
  }
  @Autowired
  public void setLanguageService(LanguageService languageService){
    this.languageService = languageService;
  }
  @Autowired
  public void setCardRepository(CardRepository cardRepository){
    this.cardRepository = cardRepository;
  }


  public List<CardDto> getCards() {
    return convertCardsToCardDtos(cardRepository.findAll());
  }

  public List<Card> getCardsByGameId(long gameId){
    return cardRepository.findAllByGameId(gameId);
  }

  public List<CardDto> getCardDtosByGameId(long gameId){
    return convertCardsToCardDtos(getCardsByGameId(gameId));
  }

  public List<Card> getAllCardTranslationsByOriginalCardId(long cardId){
    return cardRepository.findAllCardTranslationsByOriginalCardId(cardId);
  }

  public List<Card> getAllCardTranslations(List<Card> cards){
    List<Card> allCards = new ArrayList<Card>();

    for(Card card : cards){
      allCards.addAll(getAllCardTranslationsByOriginalCardId(card.getId()));
    }

    return allCards;
  }

  @Transactional
  public ResponseEntity createCard(CardDto cardDto) {
    ComponentCheck.checkComponents(cardDto);
    Language language = languageService.getLanguage(cardDto.getLanguageId());
    Game game = gameService.getGame(cardDto.getGameId());

    Card newCard = new Card(
      cardDto.getTitle(),
      cardDto.getDescription(),
      language
    );
    newCard.getCardsGame().add(game);

    if(cardDto.getOriginalCardId() != 0){
      Card card = getCard(cardDto.getOriginalCardId());
      newCard.getOriginalCards().add(card);
    }

    cardRepository.save(newCard);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.CARDS_PATH, newCard.getId()),
      HttpStatus.CREATED);
  }


  public ResponseEntity updateCard(CardDto newCardDto, long cardId) {
    ComponentCheck.checkComponents(newCardDto);

    Language language = languageService.getLanguage(newCardDto.getLanguageId());
    Card card = getCard(cardId);

    card.setTitle(newCardDto.getTitle());
    card.setDescription(newCardDto.getDescription());
    card.setLanguage(language);

    cardRepository.save(card);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteCard(long cardId) {
    try {
      cardRepository.deleteById(cardId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.CARD_NOT_FOUND_ID);
    }
  }

  public Card getCard(long cardId) {
    return cardRepository.findById(cardId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.CARD_NOT_FOUND_ID));
  }

  public CardDto getCardDto(long cardId){
    return convertCardToCardDto(getCard(cardId));
  }

  public List<CardDto> convertCardsToCardDtos(List<Card> cards) {
    return cards.stream()
      .map(this::convertCardToCardDto)
      .collect(Collectors.toList());
  }

  public CardDto convertCardToCardDto(Card card) {
    return new CardDto(
      card.getTitle(),
      card.getDescription(),
      card.getLanguage().getId(),
      card.getCardsGame().get(0).getId(),
      card.getOriginalCards().isEmpty() ? 0 : card.getOriginalCards().get(0).getId()
    );
  }
}
