package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.FilmController;
import com.sparta.springrestapi.entities.FilmEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class FilmControllerTests {
    
    @Autowired
    private FilmController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }
    
    @Test
    @DisplayName("Checking title of film with id 1 test")
    void checkingTitleOfFilmWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        FilmEntity film = restTemplate.getForObject("http://localhost:8080/films/1", FilmEntity.class);
        assert film != null;
        Assertions.assertEquals("ACADEMY DINOSAUR", film.getTitle());
    }

    @Test
    @DisplayName("Checking description of film with id 1 test")
    void checkingDescriptionOfFilmWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        FilmEntity film = restTemplate.getForObject("http://localhost:8080/films/1", FilmEntity.class);
        assert film != null;
        Assertions.assertEquals("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies", film.getDescription());
    }

    @Test
    @DisplayName("Checking release year of film with id 1 test")
    void checkingReleaseYearOfFilmWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        FilmEntity film = restTemplate.getForObject("http://localhost:8080/films/1", FilmEntity.class);
        assert film != null;
        Assertions.assertEquals(2006, film.getReleaseYear());
    }

    @Test
    @DisplayName("Checking language id of film with id 1 test")
    void checkingLanguageIdOfFilmWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        FilmEntity film = restTemplate.getForObject("http://localhost:8080/films/1", FilmEntity.class);
        assert film != null;
        Assertions.assertEquals(1, film.getLanguageId());
    }

    @Test
    @DisplayName("Checking length of film with id 1 test")
    void checkingLengthOfFilmWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        FilmEntity film = restTemplate.getForObject("http://localhost:8080/films/1", FilmEntity.class);
        assert film != null;
        Assertions.assertEquals(86, film.getLength());
    }

    @Test
    @DisplayName("Checking rating of film with id 1 test")
    void checkingRatingOfFilmWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        FilmEntity film = restTemplate.getForObject("http://localhost:8080/films/1", FilmEntity.class);
        assert film != null;
        Assertions.assertEquals("PG", film.getRating());
    }
    
}