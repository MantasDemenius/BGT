package com.project.bgt.repository;

import com.project.bgt.model.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

  Language findByCode(String code);

  @Query(
    value = "with all_games as (\n"
      + "select \n"
      + "\tg.*\n"
      + "from game_relationship gr \n"
      + "inner join game g\n"
      + "\ton gr.translated_game_id = g.id\n"
      + "where gr.original_game_id = :gameId\n"
      + "union all\n"
      + "select \n"
      + "\tg.*\n"
      + "from game g\n"
      + "where g.id = :gameId\n"
      + ")\n"
      + "select \n"
      + "\tl.\"name\"\n"
      + "from all_games ag\n"
      + "inner join \"language\" l\n"
      + "\ton ag.language_id = l.id\n"
      + "group by l.\"name\"\n"
      + "order by l.\"name\"",
    nativeQuery = true
  )
  List<String> findAllGameLanguages(@Param("gameId") long gameId);
}
