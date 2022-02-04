package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.CountryEntity;
import com.sparta.springrestapi.repositories.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CountryRepositoryTests {
    
    @Autowired
    private CountryRepository repository;
    
    @Test
    @DisplayName("Checking there are 109 countries test")
    void checkingThereAre109CountriesTest() {
        List<CountryEntity> all = repository.findAll();
        Assertions.assertEquals(109, all.size());
    }
    
}