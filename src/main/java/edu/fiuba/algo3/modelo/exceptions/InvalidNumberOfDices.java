package edu.fiuba.algo3.modelo.exceptions;

public class InvalidNumberOfDices extends Throwable {
    private String message;

    public InvalidNumberOfDices() {
        this.message = "Invalid number of dices";
    }

    public String getMessage(){
        return this.message;
    }
}
