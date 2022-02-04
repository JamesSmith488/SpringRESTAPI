package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.LanguageController;
import com.sparta.springrestapi.entities.LanguageEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class LanguageControllerTests {
    
    @Autowired
    private LanguageController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }
    
    @Test
    @DisplayName("Checking that language with id 3 is Japanese test")
    void checkingThatLanguageWithId3IsJapaneseTest() {
        RestTemplate restTemplate = new RestTemplate();
        LanguageEntity language = restTemplate.getForObject("http://localhost:8080/language/3", LanguageEntity.class);
        assert language != null;
        Assertions.assertEquals("Japanese", language.getName());
    }
    
}