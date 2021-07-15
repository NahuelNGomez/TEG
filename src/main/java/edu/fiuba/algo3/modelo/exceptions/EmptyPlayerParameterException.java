package edu.fiuba.algo3.modelo.exceptions;

public class EmptyPlayerParameterException extends Exception{
    private String message;

    public EmptyPlayerParameterException() {
        this.message = "The player sent is empty";
    }

    public String getMessage(){
        return this.message;
    }
}
