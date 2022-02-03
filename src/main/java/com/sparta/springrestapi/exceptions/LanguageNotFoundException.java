package com.sparta.springrestapi.exceptions;

public class LanguageNotFoundException extends RuntimeException {

    public LanguageNotFoundException(Integer id) {
        super("Could not find language: " + id);
    }

}