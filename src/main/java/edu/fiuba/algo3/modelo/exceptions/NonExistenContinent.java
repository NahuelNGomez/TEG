package edu.fiuba.algo3.modelo.exceptions;

public class NonExistenContinent extends Exception{
    private String message;

    public NonExistenContinent() {
        this.message = "The continent sent does not exist";
    }

    public String getMessage(){
        return this.message;
    }

}
