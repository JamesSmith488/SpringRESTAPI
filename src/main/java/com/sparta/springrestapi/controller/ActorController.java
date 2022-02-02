package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.ActorEntity;
import com.sparta.springrestapi.exceptions.ActorNotFoundException;
import com.sparta.springrestapi.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ActorController {

    private final ActorRepository repository;

    @Autowired
    public ActorController(ActorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/actors/all")
    public CollectionModel<EntityModel<ActorEntity>> findAllActors() {
        List<EntityModel<ActorEntity>> actors = repository.findAll().stream()
                .map(actor -> EntityModel.of(actor,
                        linkTo(methodOn(ActorController.class)
                                .findActorById(actor.getActorId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
        return CollectionModel.of(actors,
                linkTo(methodOn(ActorController.class)
                .findAllActors())
                .withSelfRel());
    }

    @GetMapping("/actors/name/{name}")
    public CollectionModel<EntityModel<ActorEntity>> findActorsByName(@PathVariable String name) {
        List<EntityModel<ActorEntity>> foundActors;
        if(name == null) {
            return findAllActors();
        } else {
            foundActors = new ArrayList<>();
            for(ActorEntity actor : repository.findAll()) {
                if((actor.getFirstName() + " " + actor.getLastName()).toLowerCase().contains(name.toLowerCase())) {
                    foundActors.add(EntityModel.of(actor,
                            linkTo(methodOn(ActorController.class)
                                    .findActorById(actor.getActorId()))
                                    .withSelfRel()));
                }
            }
        }
        return CollectionModel.of(foundActors,
                linkTo(methodOn(ActorController.class)
                        .findActorsByName(name))
                        .withSelfRel());
    }

    @GetMapping("/actors")
    public CollectionModel<EntityModel<ActorEntity>> findActorsByName(@RequestParam() String firstName, @RequestParam() String lastName) {
        List<EntityModel<ActorEntity>> foundActors;
        if(firstName == null && lastName == null) {
            return findAllActors();
        } else if(lastName == null) {
            foundActors = new ArrayList<>();
            for(ActorEntity actor : repository.findAll()) {
               if(actor.getFirstName().toLowerCase().contains(firstName.toLowerCase())) {
                   foundActors.add(EntityModel.of(actor,
                           linkTo(methodOn(ActorController.class)
                                   .findActorById(actor.getActorId()))
                                   .withSelfRel()));
               }
            }
        } else if(firstName == null) {
            foundActors = new ArrayList<>();
            for(ActorEntity actor : repository.findAll()) {
                if(actor.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                    foundActors.add(EntityModel.of(actor,
                            linkTo(methodOn(ActorController.class)
                                    .findActorById(actor.getActorId()))
                                    .withSelfRel()));
                }
            }
        } else {
            foundActors = new ArrayList<>();
            for(ActorEntity actor : repository.findAll()) {
                if(actor.getFirstName().toLowerCase().contains(firstName.toLowerCase()) && actor.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                    foundActors.add(EntityModel.of(actor,
                            linkTo(methodOn(ActorController.class)
                                    .findActorById(actor.getActorId()))
                                    .withSelfRel()));
                }
            }
        }
        return CollectionModel.of(foundActors,
                linkTo(methodOn(ActorController.class)
                .findActorsByName(firstName, lastName))
                .withSelfRel());
    }

    @GetMapping("/actors/{id}")
    public EntityModel<ActorEntity> findActorById(@PathVariable("id") Integer id) {
        ActorEntity actor = repository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException(id));
        return EntityModel.of(actor,
                //WebMvcLinkBuilder.linkTo
                linkTo(
                        //WebMvcLinkBuilder.methodOn
                        methodOn(ActorController.class)
                                .findAllActors())
                                .withRel("allActors"),
                linkTo(
                        methodOn(ActorController.class)
                                .findActorById(id))
                                .withSelfRel()
        );
    }

    @PostMapping("/actors")
    public EntityModel<ActorEntity> addActor(@RequestBody ActorEntity actor) throws ValidationException {
        if(actor.getActorId() == null && actor.getFirstName() != null && actor.getLastName() != null && actor.getLastUpdate() != null) {
            return EntityModel.of(repository.save(actor),
                    linkTo(
                            methodOn(ActorController.class)
                                    .findAllActors())
                            .withRel("allActors"),
                    linkTo(
                            methodOn(ActorController.class)
                                    .findActorById(actor.getActorId()))
                            .withSelfRel()
            );
        } else {
            throw new ValidationException("Actor Cannot Be Created");
        }
    }

    @PutMapping("/actors")
    public ResponseEntity<ActorEntity> updateActor(@RequestBody ActorEntity actor) {
        if(repository.findById(actor.getActorId()).isPresent()) {
            return new ResponseEntity<>(repository.save(actor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(actor, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}