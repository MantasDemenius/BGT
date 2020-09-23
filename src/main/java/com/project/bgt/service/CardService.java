package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.check.ValueCheck;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
import com.project.bgt.dto.GameDto;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.repository.CardRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final CardRepository cardRepository;
  private final LanguageService languageService;
  private final GameService gameService;

  public CardService(CardRepository cardRepository, LanguageService languageService,
    GameService gameService) {
    this.cardRepository = cardRepository;
    this.languageService = languageService;
    this.gameService = gameService;
  }

  public List<CardDto> getCards() {
    return convertCardsToCardDtos(cardRepository.findAll());
  }

  @Transactional
  public ResponseEntity createCard(CardDto cardDto) {
    ValueCheck.checkValues(cardDto);
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
    ValueCheck.checkValues(newCardDto);

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

  public CardDto getCardDto(long gameId){
    return convertCardToCardDto(getCard(gameId));
  }

  private List<CardDto> convertCardsToCardDtos(List<Card> cards) {
    return cards.stream()
      .map(this::convertCardToCardDto)
      .collect(Collectors.toList());
  }

  private CardDto convertCardToCardDto(Card card) {
    return new CardDto(
      card.getTitle(),
      card.getDescription(),
      card.getCardsGame().get(0).getId(),
      card.getLanguage().getId(),
      card.getOriginalCards().isEmpty() ? 0 : card.getOriginalCards().get(0).getId()
    );
  }
}
