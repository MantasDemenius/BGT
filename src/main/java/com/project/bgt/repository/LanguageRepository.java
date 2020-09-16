package com.project.bgt.repository;

import com.project.bgt.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

  Language findByCode(String code);
}
