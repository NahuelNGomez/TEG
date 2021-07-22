package edu.fiuba.algo3.modelo.exceptions;

public class NonExistentPlayer extends Exception {
    private String message;

    public NonExistentPlayer() {
        this.message = "The player sent does not exist";
    }
}
