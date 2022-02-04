package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.FilmEntity;
import com.sparta.springrestapi.exceptions.FilmNotFoundException;
import com.sparta.springrestapi.repositories.FilmCategoryRepository;
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
public class FilmController {

    private final FilmRepository repository;
    private final FilmCategoryRepository filmCategoryRepository;

    @Autowired
    public FilmController(FilmRepository repository, FilmCategoryRepository filmCategoryRepository) {
        this.repository = repository;
        this.filmCategoryRepository = filmCategoryRepository;
    }

    @GetMapping("/films/all")
    public CollectionModel<EntityModel<FilmEntity>> findAllFilms() {
        List<EntityModel<FilmEntity>> films = repository.findAll().stream()
                .map(film -> EntityModel.of(film,
                        linkTo(methodOn(FilmController.class)
                                .findFilmById(film.getFilmId()))
                                .withSelfRel(),
                        linkTo(methodOn(CategoryController.class)
                                .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                                .withRel("category"),
                        linkTo(methodOn(LanguageController.class)
                                .findLanguageById(film.getLanguageId()))
                                .withRel("language")))
                .toList();
        return CollectionModel.of(films,
                linkTo(methodOn(FilmController.class)
                        .findAllFilms())
                        .withSelfRel());
    }

    @GetMapping("/films/{id}")
    public EntityModel<FilmEntity> findFilmById(@PathVariable("id") Integer id) {
        FilmEntity film = repository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
        return EntityModel.of(film,
                linkTo(methodOn(FilmController.class)
                        .findFilmById(id))
                        .withSelfRel(),
                linkTo(methodOn(CategoryController.class)
                        .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                        .withRel("category"),
                linkTo(methodOn(LanguageController.class)
                        .findLanguageById(film.getLanguageId()))
                        .withRel("language"),
                linkTo(methodOn(FilmController.class)
                        .findAllFilms())
                        .withRel("allFilms"));
    }

    @GetMapping("/films")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByTitle(@RequestParam(required = false) String title) {
        List<EntityModel<FilmEntity>> foundFilms;
        if(title == null) {
            return findAllFilms();
        } else {
            foundFilms = new ArrayList<>();
            for(FilmEntity film : repository.findAll()) {
                if (film.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    foundFilms.add(EntityModel.of(film,
                            linkTo(methodOn(FilmController.class)
                                    .findFilmById(film.getFilmId()))
                                    .withSelfRel(),
                            linkTo(methodOn(CategoryController.class)
                                    .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                                    .withRel("category"),
                            linkTo(methodOn(LanguageController.class)
                                    .findLanguageById(film.getLanguageId()))
                                    .withRel("language")));
                }
            }
        }
        return CollectionModel.of(foundFilms,
                linkTo(methodOn(FilmController.class)
                        .findFilmsByTitle(title))
                        .withSelfRel());
    }

    @GetMapping("/films/year/{year}")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByYear(@PathVariable("year") Integer year) {
        List<EntityModel<FilmEntity>> films = repository.findByReleaseYear(year).stream()
                .map(film -> EntityModel.of(film,
                        linkTo(methodOn(FilmController.class)
                                .findFilmById(film.getFilmId()))
                                .withSelfRel(),
                        linkTo(methodOn(CategoryController.class)
                                .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                                .withRel("category"),
                        linkTo(methodOn(LanguageController.class)
                                .findLanguageById(film.getLanguageId()))
                                .withRel("language")))
                .toList();
        return CollectionModel.of(films,
                linkTo(methodOn(FilmController.class)
                        .findFilmsByYear(year))
                        .withSelfRel());
    }

    @GetMapping("/films/language/{id}")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByLanguageId(@PathVariable("id") Integer id) {
        List<EntityModel<FilmEntity>> films = repository.findByLanguageId(id).stream()
                .map(film -> EntityModel.of(film,
                        linkTo(methodOn(FilmController.class)
                                .findFilmById(film.getFilmId()))
                                .withSelfRel(),
                        linkTo(methodOn(CategoryController.class)
                                .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                                .withRel("category"),
                        linkTo(methodOn(LanguageController.class)
                                .findLanguageById(film.getLanguageId()))
                                .withRel("language")))
                .toList();
        return CollectionModel.of(films,
                linkTo(methodOn(FilmController.class)
                        .findFilmsByLanguageId(id))
                        .withSelfRel());
    }

    @GetMapping("/films/rating/{rating}")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByRating(@PathVariable("rating") String rating) {
        List<EntityModel<FilmEntity>> films = repository.findByRating(rating).stream()
                .map(film -> EntityModel.of(film,
                        linkTo(methodOn(FilmController.class)
                                .findFilmById(film.getFilmId()))
                                .withSelfRel(),
                        linkTo(methodOn(CategoryController.class)
                                .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                                .withRel("category"),
                        linkTo(methodOn(LanguageController.class)
                                .findLanguageById(film.getLanguageId()))
                                .withRel("language")))
                .toList();
        return CollectionModel.of(films,
                linkTo(methodOn(FilmController.class)
                        .findFilmsByRating(rating))
                        .withSelfRel());
    }

    @GetMapping("/films/length/{length}")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByLength(@PathVariable("length") Integer length) {
        List<EntityModel<FilmEntity>> films = repository.findByLength(length).stream()
                .map(film -> EntityModel.of(film,
                        linkTo(methodOn(FilmController.class)
                                .findFilmById(film.getFilmId()))
                                .withSelfRel(),
                        linkTo(methodOn(CategoryController.class)
                                .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                                .withRel("category"),
                        linkTo(methodOn(LanguageController.class)
                                .findLanguageById(film.getLanguageId()))
                                .withRel("language")))
                .toList();
        return CollectionModel.of(films,
                linkTo(methodOn(FilmController.class)
                        .findFilmsByLength(length))
                        .withSelfRel());
    }

    @PostMapping("/films")
    public EntityModel<FilmEntity> addFilm(@RequestBody FilmEntity film) throws ValidationException {
        if(film.getFilmId() == null && film.getTitle() != null && film.getDescription() != null && film.getReleaseYear() != null && film.getLanguageId() != null && film.getRating() != null && film.getLastUpdate() != null) {
            return EntityModel.of(repository.save(film),
                    linkTo(methodOn(FilmController.class)
                            .findFilmById(film.getFilmId()))
                            .withSelfRel(),
                    linkTo(methodOn(CategoryController.class)
                            .findCategoryById(filmCategoryRepository.findByFilmId(film.getFilmId()).getCategoryId()))
                            .withRel("category"),
                    linkTo(methodOn(LanguageController.class)
                            .findLanguageById(film.getLanguageId()))
                            .withRel("language"),
                    linkTo(methodOn(FilmController.class)
                            .findAllFilms())
                            .withRel("allFilms"));
        } else {
            throw new ValidationException("Film Cannot Be Created");
        }
    }

    @PutMapping("/films")
    public ResponseEntity<FilmEntity> updateFilm(@RequestBody FilmEntity film) {
        if(repository.findById(film.getFilmId()).isPresent()) {
            return new ResponseEntity<>(repository.save(film), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(film, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/films/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}