package com.sparta.springrestapi.exceptions;

public class ActorNotFoundException extends RuntimeException {

    public ActorNotFoundException(Integer id) {
        super("Could not find actor: " + id);
    }

}