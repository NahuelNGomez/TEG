package edu.fiuba.algo3.modelo.exceptions;

public class NonExistentCountry extends Exception{
    private String message;

    public NonExistentCountry() {
        this.message = "The country sent does not exist";
    }

    public String getMessage(){
        return this.message;
    }
}
