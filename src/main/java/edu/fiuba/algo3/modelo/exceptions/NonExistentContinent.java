package edu.fiuba.algo3.modelo.exceptions;

public class NonExistentContinent extends Exception{
    private String message;

    public NonExistentContinent() {
        this.message = "The continent sent does not exist";
    }

    public String getMessage(){
        return this.message;
    }
}
