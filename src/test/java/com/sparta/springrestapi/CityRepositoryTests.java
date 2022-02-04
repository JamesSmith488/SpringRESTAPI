package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.CityEntity;
import com.sparta.springrestapi.repositories.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CityRepositoryTests {

    @Autowired
    private CityRepository repository;

    @Test
    @DisplayName("Checking that there are 600 cities test")
    void checkingThatThereAre600CitiesTest() {
        List<CityEntity> all = repository.findAll();
        Assertions.assertEquals(600, all.size());
    }

}