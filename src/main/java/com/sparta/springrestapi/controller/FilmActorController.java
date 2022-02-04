package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.ActorEntity;
import com.sparta.springrestapi.entities.FilmActorEntity;
import com.sparta.springrestapi.entities.FilmEntity;
import com.sparta.springrestapi.repositories.ActorRepository;
import com.sparta.springrestapi.repositories.FilmActorRepository;
import com.sparta.springrestapi.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FilmActorController {

    private final FilmActorRepository repository;
    private final FilmRepository filmRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public FilmActorController(FilmActorRepository repository, FilmRepository filmRepository, ActorRepository actorRepository) {
        this.repository = repository;
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
    }

    @GetMapping("/film_actor/all")
    public CollectionModel<EntityModel<FilmActorEntity>> findAllFilmActor() {
        List<EntityModel<FilmActorEntity>> filmActors = repository.findAll().stream()
                .map(filmActor -> EntityModel.of(filmActor,
                        linkTo(methodOn(FilmController.class)
                                .findFilmById(filmActor.getFilmId()))
                                .withRel("film"),
                        linkTo(methodOn(ActorController.class)
                                .findActorById(filmActor.getActorId()))
                                .withRel("actor")))
                .toList();
        return CollectionModel.of(filmActors,
                linkTo(methodOn(FilmActorController.class)
                        .findAllFilmActor())
                        .withSelfRel());
    }

    @GetMapping("/films/actor/{id}")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByActor(@PathVariable("id") Integer id) {
        List<FilmActorEntity> filmActors = repository.findByActorId(id);
        List<EntityModel<FilmEntity>> foundFilms = new ArrayList<>();
        for(FilmEntity film : filmRepository.findAll()) {
            for(FilmActorEntity record : filmActors) {
                if(record.getFilmId().equals(film.getFilmId())) {
                    foundFilms.add(EntityModel.of(film,
                            linkTo(methodOn(FilmController.class)
                                    .findFilmById(film.getFilmId()))
                                    .withSelfRel(),
                            linkTo(methodOn(LanguageController.class)
                                    .findLanguageById(film.getLanguageId()))
                                    .withRel("language"),
                            linkTo(methodOn(ActorController.class)
                                    .findActorById(id))
                                    .withRel("actor")));
                }
            }
        }
        return CollectionModel.of(foundFilms);
    }

    @GetMapping("/actors/film/{id}")
    public CollectionModel<EntityModel<ActorEntity>> findActorsByFilm(@PathVariable("id") Integer id) {
        List<FilmActorEntity> filmActors = repository.findByFilmId(id);
        List<EntityModel<ActorEntity>> foundActors = new ArrayList<>();
        for(ActorEntity actor : actorRepository.findAll()) {
            for(FilmActorEntity record : filmActors) {
                if(record.getActorId().equals(actor.getActorId())) {
                    foundActors.add(EntityModel.of(actor,
                            linkTo(methodOn(ActorController.class)
                                    .findActorById(actor.getActorId()))
                                    .withSelfRel(),
                            linkTo(methodOn(FilmController.class)
                                    .findFilmById(id))
                                    .withRel("film")));
                }
            }
        }
        return CollectionModel.of(foundActors);
    }

    @PostMapping("/film_actor")
    public EntityModel<FilmActorEntity> addFilmActor(@RequestBody FilmActorEntity filmActor) throws ValidationException {
        if(filmActor.getActorId() != null && filmActor.getFilmId() != null) {
            return EntityModel.of(filmActor,
                    linkTo(methodOn(ActorController.class)
                            .findActorById(filmActor.getActorId()))
                            .withRel("actor"),
                    linkTo(methodOn(FilmController.class)
                            .findFilmById(filmActor.getFilmId()))
                            .withRel("film"));
        } else {
            throw new ValidationException("Film Actor Cannot Be Created");
        }
    }


    @PutMapping("/film_actor")
    public ResponseEntity<FilmActorEntity> updateFilmActor(@RequestBody FilmActorEntity filmActor) {
        if(repository.findByActorIdAndFilmId(filmActor.getActorId(),filmActor.getFilmId()).isPresent()) {
            return new ResponseEntity<>(repository.save(filmActor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(filmActor, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/film_actor/{filmId}/{actorId}")
    public ResponseEntity<?> deleteFilmActor(@PathVariable("filmId") Integer filmId, @PathVariable("actorId") Integer actorId) {
        repository.deleteByActorIdAndFilmId(actorId, filmId);
        return ResponseEntity.noContent().build();
    }

}
