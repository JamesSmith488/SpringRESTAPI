package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.FilmCategoryEntity;
import com.sparta.springrestapi.entities.FilmCategoryEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmCategoryRepository extends JpaRepository<FilmCategoryEntity, FilmCategoryEntityPK> {

    Optional<FilmCategoryEntity> findByCategoryIdAndFilmId(Integer categoryId, Integer filmId);
    void deleteByCategoryIdAndFilmId(Integer categoryId, Integer filmId);
    List<FilmCategoryEntity> findByCategoryId(Integer id);
    FilmCategoryEntity findByFilmId(Integer id);

}