package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.ActorController;
import com.sparta.springrestapi.entities.ActorEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SpringRestapiApplicationTests {

    @Autowired
    private ActorController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void doTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ActorEntity> response = restTemplate.getForEntity("http://localhost:8080/actors/1", ActorEntity.class);
        System.out.println(response.getBody());
        ActorEntity actor = restTemplate.getForObject("http://localhost:8080/actors/1", ActorEntity.class);

    }

}