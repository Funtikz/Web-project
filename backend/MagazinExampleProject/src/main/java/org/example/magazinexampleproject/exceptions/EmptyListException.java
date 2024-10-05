package org.example.magazinexampleproject.exceptions;


public class EmptyListException extends RuntimeException {

    public EmptyListException(){
        super("This list is empty :(");
    }
}
