package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.FilmCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmCategoryRepository extends JpaRepository<FilmCategoryEntity, Integer> {

    List<FilmCategoryEntity> findByCategoryId(Integer id);
    FilmCategoryEntity findByFilmId(Integer id);

}