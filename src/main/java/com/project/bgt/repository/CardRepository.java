package com.project.bgt.repository;

import com.project.bgt.model.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

  List<Card> findAllByLanguageId(long languageId);
}
