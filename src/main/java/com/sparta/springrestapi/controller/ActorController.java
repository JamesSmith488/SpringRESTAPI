package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.ActorEntity;
import com.sparta.springrestapi.exceptions.ActorNotFoundException;
import com.sparta.springrestapi.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ActorController {

    private final ActorRepository repository;

    @Autowired
    public ActorController(ActorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/actors")
    public CollectionModel<ActorEntity> findAllActors() {
        List<ActorEntity> actors = repository.findAll();
        for(ActorEntity actor : actors) {
            Link selfLink = linkTo(methodOn(ActorController.class)
                    .findActor(actor.getActorId()))
                    .withSelfRel();
            actor.add(selfLink);
        }
        Link link = linkTo(methodOn(ActorController.class)
                .findAllActors())
                .withSelfRel();
        return CollectionModel.of(actors,link);
    }

    @GetMapping("/actors/{id}")
    public EntityModel<ActorEntity> findActor(@PathVariable("id") Integer id) {
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
                                .findActor(id))
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
                                    .findActor(actor.getActorId()))
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
    public void deleteActor(@PathVariable("id") Integer id) {
        repository.deleteById(id);
    }

}