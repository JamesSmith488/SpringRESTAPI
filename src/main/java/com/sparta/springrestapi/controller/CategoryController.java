package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.CategoryEntity;
import com.sparta.springrestapi.exceptions.CategoryNotFoundException;
import com.sparta.springrestapi.repositories.CategoryRepository;
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
public class CategoryController {

    private final CategoryRepository repository;

    @Autowired
    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/category/all")
    public CollectionModel<EntityModel<CategoryEntity>> findAllCategories() {
        List<EntityModel<CategoryEntity>> categories = repository.findAll().stream()
                .map(category -> EntityModel.of(category,
                        linkTo(methodOn(CategoryController.class)
                                .findCategoryById(category.getCategoryId()))
                                .withSelfRel()))
                .toList();
        return CollectionModel.of(categories,
                linkTo(methodOn(CategoryController.class)
                        .findAllCategories())
                        .withSelfRel());
    }

    @GetMapping("/category/{id}")
    public EntityModel<CategoryEntity> findCategoryById(@PathVariable("id") Integer id) {
        CategoryEntity category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return EntityModel.of(category,
                linkTo(methodOn(CategoryController.class)
                        .findCategoryById(id))
                        .withSelfRel(),
                linkTo(methodOn(CategoryController.class)
                        .findAllCategories())
                        .withRel("allCategories"));
    }

    @GetMapping("/category")
    public CollectionModel<EntityModel<CategoryEntity>> findCategoryByName(@RequestParam(required = false) String name) {
        List<EntityModel<CategoryEntity>> foundCategories;
        if(name == null) {
            return findAllCategories();
        } else {
            foundCategories = new ArrayList<>();
            for(CategoryEntity category : repository.findAll()) {
                if(category.getName().toLowerCase().contains(name.toLowerCase())) {
                    foundCategories.add(EntityModel.of(category,
                            linkTo(methodOn(CategoryController.class)
                                    .findCategoryById(category.getCategoryId()))
                                    .withSelfRel()));
                }
            }
        }
        return CollectionModel.of(foundCategories,
                linkTo(methodOn(CategoryController.class)
                        .findCategoryByName(name))
                        .withSelfRel());
    }

    @PostMapping("/category")
    public EntityModel<CategoryEntity> addCategory(@RequestBody CategoryEntity category) throws ValidationException {
        if(category.getCategoryId() == null && category.getName() != null && category.getLastUpdate() != null) {
            return EntityModel.of(repository.save(category),
                    linkTo(methodOn(CategoryController.class)
                            .findCategoryById(category.getCategoryId()))
                            .withSelfRel(),
                    linkTo(methodOn(CategoryController.class)
                            .findAllCategories())
                            .withRel("allCategories"));
        } else {
            throw new ValidationException("Category Cannot Be Created");
        }
    }

    @PutMapping("/category")
    public ResponseEntity<CategoryEntity> updateCategory(@RequestBody CategoryEntity category) {
        if(repository.findById(category.getCategoryId()).isPresent()) {
            return new ResponseEntity<>(repository.save(category), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(category, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}