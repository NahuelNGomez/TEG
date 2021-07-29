package edu.fiuba.algo3.modelo.exceptions;

public class InvalidRegroup extends Throwable {
    private String message;

    public InvalidRegroup() {
        this.message = "Invalid regroup";
    }
}
