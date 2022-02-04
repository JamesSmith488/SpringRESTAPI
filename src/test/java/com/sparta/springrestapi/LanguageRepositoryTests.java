package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.LanguageEntity;
import com.sparta.springrestapi.repositories.LanguageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LanguageRepositoryTests {

    @Autowired
    private LanguageRepository repository;

    @Test
    @DisplayName("Checking there are 6 languages test")
    void checkingThereAre6LanguagesTest() {
        List<LanguageEntity> all = repository.findAll();
        Assertions.assertEquals(6, all.size());
    }

}