package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.ActorEntity;
import com.sparta.springrestapi.repositories.ActorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ActorRepositoryTests {

    @Autowired
    private ActorRepository repository;

    @Test
    @DisplayName("Checking there are 202 actors test")
    void checkingThereAre202ActorsTest() {
        List<ActorEntity> all = repository.findAll();
        Assertions.assertEquals(202, all.size());
    }

}