package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {
}