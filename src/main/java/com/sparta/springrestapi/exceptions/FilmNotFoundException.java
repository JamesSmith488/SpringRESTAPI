package com.sparta.springrestapi.exceptions;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(Integer id) {
        super("Could not find film: " + id);
    }

}