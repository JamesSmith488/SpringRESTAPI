package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.FilmCategoryEntity;
import com.sparta.springrestapi.entities.FilmEntity;
import com.sparta.springrestapi.repositories.CategoryRepository;
import com.sparta.springrestapi.repositories.FilmCategoryRepository;
import com.sparta.springrestapi.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FilmCategoryController {

    private final FilmCategoryRepository repository;
    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public FilmCategoryController(FilmCategoryRepository repository, FilmRepository filmRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.filmRepository = filmRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/films/category/{id}")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByCategory(@PathVariable Integer id) {
        List<FilmCategoryEntity> films = repository.findByCategoryId(id);
        List<EntityModel<FilmEntity>> foundFilms = new ArrayList<>();
        for(FilmEntity film : filmRepository.findAll()) {
            for(FilmCategoryEntity x : films){
                if(x.getFilmId().equals(film.getFilmId())) {
                    foundFilms.add(EntityModel.of(film,
                            linkTo(methodOn(FilmController.class)
                                    .findFilmById(film.getFilmId()))
                                    .withSelfRel(),
                            linkTo(methodOn(CategoryController.class)
                                    .findCategoryById(id))
                                    .withRel("category"),
                            linkTo(methodOn(LanguageController.class)
                                    .findLanguageById(film.getLanguageId()))
                                    .withRel("language")));
                }
            }
        }
        return CollectionModel.of(foundFilms);
    }

}