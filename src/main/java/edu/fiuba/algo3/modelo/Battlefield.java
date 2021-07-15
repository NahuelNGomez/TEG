package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidNumberOfDices;

import java.util.ArrayList;

public class Battlefield {
    private Dice dice;

    public Battlefield(){
        dice = dice.create();
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    private void checkValidNumberOfDice(Integer amountDice) throws InvalidNumberOfDices {
        if(amountDice > 3 || amountDice < 1) {
            throw new InvalidNumberOfDices();
        }
    }

    public Integer[] battle(int amountDice, Country defender) throws EmptyCountryParameterException, InvalidNumberOfDices {
        checkValidCountryParameter(defender);
        checkValidNumberOfDice(amountDice);

        ArrayList<Integer> attackerDice = dice.rollDice(amountDice);
        Integer defenderAmountDice = defender.diceAmount();
        ArrayList<Integer> defenderDice = dice.rollDice(defenderAmountDice);

        Integer stop = amountDice > defenderAmountDice ? defenderAmountDice : amountDice;

        return dice.diceRoundResults(attackerDice,defenderDice, stop);
    }
}
