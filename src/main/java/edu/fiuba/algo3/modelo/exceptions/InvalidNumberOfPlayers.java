package edu.fiuba.algo3.modelo.exceptions;

public class InvalidNumberOfPlayers extends Throwable {
    private String message;

    public InvalidNumberOfPlayers() {
        this.message = "Invalid number of players";
    }

}
