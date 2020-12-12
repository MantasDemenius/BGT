package com.project.bgt.repository;

import com.project.bgt.dto.OriginalGameDTO;
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
      + "\tg.description,\n"
      + "\tcase when t.\"translated_languages\" is not null then t.\"translated_languages\" else '' end \"translated_languages\"\n"
      + "from game g\n"
      + "left join game_relationship gr\n"
      + "\ton g.id = gr.translated_game_id\n"
      + "left join \"language\" l\n"
      + "\ton g.language_id = l.id\n"
      + "left join \n"
      + "\t(select \n"
      + "\t\tgr.original_game_id \"id\",\n"
      + "\t\tstring_agg(l.\"name\", ', ') \"translated_languages\"\n"
      + "\tfrom game_relationship gr\n"
      + "\tinner join game g \n"
      + "\t\ton gr.translated_game_id = g.id\n"
      + "\tinner join \"language\" l\n"
      + "\t\ton g.language_id = l.id\n"
      + "\tgroup by gr.original_game_id) \n"
      + "\tt on g.id = t.id\n"
      + "where gr.original_game_id is null\n"
      + "order by g.id",
    nativeQuery = true
  )
  List<OriginalGameDTO> findAllOriginalGames();

}
