package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.CategoryEntity;
import com.sparta.springrestapi.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repository;

    @Test
    @DisplayName("Checking there are 16 categories test")
    void checkingThereAre16CategoriesTest() {
        List<CategoryEntity> all = repository.findAll();
        Assertions.assertEquals(16, all.size());
    }

}