package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.FilmEntity;
import com.sparta.springrestapi.repositories.FilmRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilmRepositoryTests {

    @Autowired
    private FilmRepository repository;

    @Test
    @DisplayName("Checking there are 1000 films test")
    void checkingThereAre1000FilmsTest() {
        List<FilmEntity> all = repository.findAll();
        Assertions.assertEquals(1000, all.size());
    }
    
    @Test
    @DisplayName("Checking there are 0 films with release year 2022 test")
    void checkingThereAre0FilmsWithReleaseYear2022Test() {
        List<FilmEntity> all = repository.findByReleaseYear(2022);
        Assertions.assertEquals(0, all.size());
    }

    @Test
    @DisplayName("Checking there are 0 films with language id 2 test")
    void checkingThereAre0FilmsWithLanguageId2Test() {
        List<FilmEntity> all = repository.findByLanguageId(2);
        Assertions.assertEquals(0, all.size());
    }

    @Test
    @DisplayName("Checking there are 2 films with length 95 test")
    void checkingThereAre2FilmsWithLength95Test() {
        List<FilmEntity> all = repository.findByLength(95);
        Assertions.assertEquals(2, all.size());
    }

    @Test
    @DisplayName("Checking there are 195 films with rating R test")
    void checkingThereAre195FilmsWithRatingRTest() {
        List<FilmEntity> all = repository.findByRating("R");
        Assertions.assertEquals(195, all.size());
    }

}