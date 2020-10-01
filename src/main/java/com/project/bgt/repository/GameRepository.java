package com.project.bgt.repository;

import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  List<Game> findAllByLanguageId(long languageId);

  @Query(
    value = "select\n"
      + "\tg.*\n"
      + "from game g\n"
      + "left join game_relationship gr\n"
      + "\ton g.id = gr.translated_game_id\n"
      + "where gr.original_game_id is null\n"
      + "order by g.id",
    nativeQuery = true
  )
  List<Game> findAllOriginalGames();
}
