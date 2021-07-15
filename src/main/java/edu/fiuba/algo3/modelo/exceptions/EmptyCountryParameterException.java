package edu.fiuba.algo3.modelo.exceptions;

public class EmptyCountryParameterException extends Exception{
    private String message;

    public EmptyCountryParameterException() {
        this.message = "The country sent is empty";
    }
    public String getMessage(){
        return this.message;
    }

}
