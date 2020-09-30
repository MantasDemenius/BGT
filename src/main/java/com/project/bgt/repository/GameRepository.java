package com.project.bgt.repository;

import com.project.bgt.model.Card;
import com.project.bgt.model.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  List<Game> findAllByLanguageId(long languageId);
}
