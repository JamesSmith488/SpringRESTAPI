package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Integer> {

    List<FilmEntity> findByReleaseYear(Integer releaseYear);
    List<FilmEntity> findByLanguageId(Integer languageId);
    List<FilmEntity> findByRating(String rating);
    List<FilmEntity> findByLength(Integer length);

}