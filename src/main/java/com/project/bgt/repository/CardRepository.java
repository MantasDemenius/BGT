package com.project.bgt.repository;

import com.project.bgt.model.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

  List<Card> findAllByLanguageId(long languageId);

  @Query(
    value = "select c.* from card c inner join game_card gc on c.id = gc.card_id where gc.game_id = :gameId",
    nativeQuery = true)
  List<Card> findAllByGameId(@Param("gameId") long gameId);

  @Query(
    value = "select \n"
      + "\tct.* \n"
      + "from card c \n"
      + "inner join card_relationship cr \n"
      + "\ton c.id = cr.original_card_id \n"
      + "inner join card ct \n"
      + "\ton cr.translated_card_id = ct.id \n"
      + "where c.id = :cardId\n"
      + "union\n"
      + "select\n"
      + "\tc.*\n"
      + "from card c\n"
      + "where c.id = :cardId",
    nativeQuery = true
  )
  List<Card> findAllCardTranslationsByOriginalCardId(@Param("cardId") long cardId);
}
