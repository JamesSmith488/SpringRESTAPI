package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.FilmActorEntity;
import com.sparta.springrestapi.entities.FilmActorEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActorEntity, FilmActorEntityPK> {

    Optional<FilmActorEntity> findByActorIdAndFilmId(Integer actorId, Integer filmId);
    void deleteByActorIdAndFilmId(Integer actorId, Integer filmId);
    List<FilmActorEntity> findByActorId(Integer id);
    List<FilmActorEntity> findByFilmId(Integer id);

}