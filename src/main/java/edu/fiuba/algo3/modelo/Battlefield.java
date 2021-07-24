package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.util.ArrayList;

public class Battlefield {
    private Dice dice;
    private MockDice mockDice;

    public Battlefield(){
        dice = dice.create();
        mockDice = mockDice.create();
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    public Integer[] battle(int amountDice, Country defender) throws EmptyCountryParameterException {
        checkValidCountryParameter(defender);

        DiceRoll attackerDiceResult = dice.rollDice(amountDice);
        Integer defenderAmountDice = defender.diceAmount();
        DiceRoll defenderDiceResult = dice.rollDice(defenderAmountDice);

        Integer stop = amountDice > defenderAmountDice ? defenderAmountDice : amountDice;

        return dice.diceRoundResults(attackerDiceResult,defenderDiceResult, stop);
    }
}
