package com.sparta.springrestapi.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Integer id) {
        super("Could not find category: " + id);
    }

}