package edu.fiuba.algo3.modelo.exceptions;

public class EmptyContinentParameterException extends Exception {
    private String message;

    public EmptyContinentParameterException() {
        this.message = "The continent sent is empty";
    }

    public String getMessage(){
        return this.message;
    }
}
