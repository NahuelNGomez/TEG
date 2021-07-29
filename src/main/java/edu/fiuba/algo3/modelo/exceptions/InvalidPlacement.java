package edu.fiuba.algo3.modelo.exceptions;

public class InvalidPlacement extends Throwable {
    private String message;

    public InvalidPlacement() {
        this.message = "Invalid placement of armies";
    }
}
