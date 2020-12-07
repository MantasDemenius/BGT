package com.project.bgt.repository;

import com.project.bgt.dto.OriginalGameDTO;
import com.project.bgt.model.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalGameRepository extends JpaRepository<OriginalGameDTO, Long> {

  @Query(
    value = "select\n"
      + "\tg.id,\n"
      + "\tl.\"name\" \"language\",\n"
      + "\tg.author,\n"
      + "\tg.title,\n"
      + "\tg.description\n"
      + "from game g\n"
      + "left join game_relationship gr\n"
      + "\ton g.id = gr.translated_game_id\n"
      + "left join \"language\" l\n"
      + "\ton g.language_id = l.id\n"
      + "where gr.original_game_id is null\n"
      + "order by g.id",
    nativeQuery = true
  )
  List<OriginalGameDTO> findAllOriginalGames();

}
