package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidNumberOfDices;

import java.util.ArrayList;

public class Battlefield {
    //private Dice dice;
    private MockDice mockDice;

    public Battlefield(){
        //dice = dice.create();
        mockDice = mockDice.create();
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

        //ArrayList<Integer> attackerDice = dice.rollDice(amountDice);
        ArrayList<Integer> attackerDice = mockDice.rollDice(amountDice,1); //si le mando 1 es el ganador si es cero pierde
        Integer defenderAmountDice = defender.diceAmount();
        //ArrayList<Integer> defenderDice = dice.rollDice(defenderAmountDice);
        ArrayList<Integer> defenderDice = mockDice.rollDice(amountDice,0);

        Integer stop = amountDice > defenderAmountDice ? defenderAmountDice : amountDice;

        //return dice.diceRoundResults(attackerDice,defenderDice, stop);
        return mockDice.diceRoundResults(attackerDice,defenderDice,stop);
    }
}
