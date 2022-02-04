package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.ActorController;
import com.sparta.springrestapi.entities.ActorEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ActorControllerTests {

    @Autowired
    private ActorController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }
    
    @Test
    @DisplayName("Checking actor with id 1 first name is Penelope test")
    void checkingActorWithId1FirstNameIsPenelopeTest() {
        RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<ActorEntity> response = restTemplate.getForEntity("http://localhost:8080/actors/1", ActorEntity.class);
        ActorEntity actor = restTemplate.getForObject("http://localhost:8080/actors/1", ActorEntity.class);
        assert actor != null;
        Assertions.assertEquals("PENELOPE", actor.getFirstName());
    }

    @Test
    @DisplayName("Checking actor with id 1 last name is Guiness test")
    void checkingActorWithId1LastNameIsGuinessTest() {
        RestTemplate restTemplate = new RestTemplate();
        ActorEntity actor = restTemplate.getForObject("http://localhost:8080/actors/1", ActorEntity.class);
        assert actor != null;
        Assertions.assertEquals("GUINESS", actor.getLastName());
    }

}