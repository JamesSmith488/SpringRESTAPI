package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.ActorEntity;
import com.sparta.springrestapi.repositories.ActorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RESTIntTests {

    @Autowired
    private ActorRepository repository;

    @Test
    void doTest() {
        List<ActorEntity> all = repository.findAll();
        Assertions.assertEquals(201, all.size());
    }

}