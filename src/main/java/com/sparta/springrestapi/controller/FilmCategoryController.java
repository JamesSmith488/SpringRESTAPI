package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.CategoryEntity;
import com.sparta.springrestapi.entities.FilmCategoryEntity;
import com.sparta.springrestapi.entities.FilmEntity;
import com.sparta.springrestapi.exceptions.CategoryNotFoundException;
import com.sparta.springrestapi.repositories.CategoryRepository;
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

    @GetMapping("/film_category/all")
    public CollectionModel<EntityModel<FilmCategoryEntity>> findAllFilmCategory() {
        List<EntityModel<FilmCategoryEntity>> filmCategories = repository.findAll().stream()
                .map(filmCategory -> EntityModel.of(filmCategory,
                        linkTo(methodOn(FilmController.class)
                                .findFilmById(filmCategory.getFilmId()))
                                .withRel("film"),
                        linkTo(methodOn(CategoryController.class)
                                .findCategoryById(filmCategory.getCategoryId()))
                                .withRel("category")))
                .toList();
        return CollectionModel.of(filmCategories,
                linkTo(methodOn(FilmCategoryController.class)
                        .findAllFilmCategory())
                        .withSelfRel());
    }

    @GetMapping("/films/category/{id}")
    public CollectionModel<EntityModel<FilmEntity>> findFilmsByCategory(@PathVariable("id") Integer id) {
        List<FilmCategoryEntity> filmCategories = repository.findByCategoryId(id);
        List<EntityModel<FilmEntity>> foundFilms = new ArrayList<>();
        for(FilmEntity films : filmRepository.findAll()) {
            for(FilmCategoryEntity record : filmCategories){
                if(record.getFilmId().equals(films.getFilmId())) {
                    foundFilms.add(EntityModel.of(films,
                            linkTo(methodOn(FilmController.class)
                                    .findFilmById(films.getFilmId()))
                                    .withSelfRel(),
                            linkTo(methodOn(LanguageController.class)
                                    .findLanguageById(films.getLanguageId()))
                                    .withRel("language"),
                            linkTo(methodOn(CategoryController.class)
                                    .findCategoryById(id))
                                    .withRel("category")));
                }
            }
        }
        return CollectionModel.of(foundFilms);
    }

    @GetMapping("/category/films/{id}")
    public EntityModel<CategoryEntity> findCategoryByFilm(@PathVariable("id") Integer id) {
        FilmCategoryEntity filmCategory = repository.findByFilmId(id);
        CategoryEntity category = categoryRepository.findById(filmCategory.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(filmCategory.getCategoryId()));
        return EntityModel.of(category,
                linkTo(methodOn(CategoryController.class)
                        .findCategoryById(filmCategory.getCategoryId()))
                        .withSelfRel(),
                linkTo(methodOn(FilmController.class)
                        .findFilmById(id))
                        .withRel("film"));
    }

    @PostMapping("/film_category")
    public EntityModel<FilmCategoryEntity> addFilmCategory(@RequestBody FilmCategoryEntity filmCategory) throws ValidationException {
        if(filmCategory.getFilmId() != null && filmCategory.getCategoryId() != null) {
            return EntityModel.of(filmCategory,
                    linkTo(methodOn(CategoryController.class)
                            .findCategoryById(filmCategory.getCategoryId()))
                            .withRel("category"),
                    linkTo(methodOn(FilmController.class)
                            .findFilmById(filmCategory.getFilmId()))
                            .withRel("film"));
        } else {
            throw new ValidationException("Film Category Cannot Be Created");
        }
    }

    @PutMapping("/film_category")
    public ResponseEntity<FilmCategoryEntity> updateFilmCategory(@RequestBody FilmCategoryEntity filmCategory) {
        if(repository.findByCategoryIdAndFilmId(filmCategory.getCategoryId(), filmCategory.getFilmId()).isPresent()) {
            return new ResponseEntity<>(repository.save(filmCategory), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(filmCategory, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/film_category/{filmId}/{categoryId}")
    public ResponseEntity<?> deleteFilmCategory(@PathVariable("filmId") Integer filmId, @PathVariable("categoryId") Integer categoryId) {
        repository.deleteByCategoryIdAndFilmId(categoryId, filmId);
        return ResponseEntity.noContent().build();
    }

}