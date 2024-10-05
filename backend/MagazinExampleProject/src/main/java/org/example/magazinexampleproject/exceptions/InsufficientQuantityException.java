package org.example.magazinexampleproject.exceptions;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String s){
        super(s);
    }
}
