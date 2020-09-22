package com.project.bgt.repository;

import com.project.bgt.model.Card;
import com.project.bgt.model.CardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTranslationRepository extends JpaRepository<CardTranslation, Long> {

}
