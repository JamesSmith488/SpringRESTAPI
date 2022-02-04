package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.LanguageEntity;
import com.sparta.springrestapi.exceptions.LanguageNotFoundException;
import com.sparta.springrestapi.repositories.LanguageRepository;
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
public class LanguageController {

    private final LanguageRepository repository;

    @Autowired
    public LanguageController(LanguageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/language/all")
    public CollectionModel<EntityModel<LanguageEntity>> findAllLanguages() {
        List<EntityModel<LanguageEntity>> languages = repository.findAll().stream()
                .map(language -> EntityModel.of(language,
                        linkTo(methodOn(LanguageController.class)
                                .findLanguageById(language.getLanguageId()))
                                .withSelfRel()))
                .toList();
        return CollectionModel.of(languages,
                linkTo(methodOn(LanguageController.class)
                        .findAllLanguages())
                        .withSelfRel());
    }

    @GetMapping("/language/{id}")
    public EntityModel<LanguageEntity> findLanguageById(@PathVariable("id") Integer id) {
        LanguageEntity language = repository.findById(id)
                .orElseThrow(() -> new LanguageNotFoundException(id));
        return EntityModel.of(language,
                linkTo(methodOn(LanguageController.class)
                        .findLanguageById(id))
                        .withSelfRel(),
                linkTo(methodOn(LanguageController.class)
                        .findAllLanguages())
                        .withRel("allLanguages"));
    }

    @GetMapping("/language")
    public CollectionModel<EntityModel<LanguageEntity>> findLanguageByName(@RequestParam(required = false) String name) {
        List<EntityModel<LanguageEntity>> foundLanguages;
        if(name == null) {
            return findAllLanguages();
        } else {
            foundLanguages = new ArrayList<>();
            for(LanguageEntity language : repository.findAll()) {
                if(language.getName().toLowerCase().contains(name.toLowerCase())) {
                    foundLanguages.add(EntityModel.of(language,
                            linkTo(methodOn(LanguageController.class)
                                    .findLanguageById(language.getLanguageId()))
                                    .withSelfRel()));
                }
            }
        }
        return CollectionModel.of(foundLanguages,
                linkTo(methodOn(LanguageController.class)
                        .findLanguageByName(name))
                        .withSelfRel());
    }

    @PostMapping("/language")
    public EntityModel<LanguageEntity> addLanguage(@RequestBody LanguageEntity language) throws ValidationException {
        if(language.getLanguageId() == null && language.getName() != null && language.getLastUpdate() != null) {
            return EntityModel.of(repository.save(language),
                    linkTo(methodOn(LanguageController.class)
                            .findLanguageById(language.getLanguageId()))
                            .withSelfRel(),
                    linkTo(methodOn(LanguageController.class)
                            .findAllLanguages())
                            .withRel("allLanguages"));
        } else {
            throw new ValidationException("Language Cannot Be Created");
        }
    }


    @PutMapping("/language")
    public ResponseEntity<LanguageEntity> updateLanguage(@RequestBody LanguageEntity language) {
        if(repository.findById(language.getLanguageId()).isPresent()) {
            return new ResponseEntity<>(repository.save(language), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(language, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/language/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}