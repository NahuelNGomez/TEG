package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;


public class MockBattlefield {
    private final MockDice mockDice;

    public MockBattlefield(){
        mockDice = MockDice.create();
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    public Integer[] battle(int amountDice, Country defender) throws EmptyCountryParameterException {
        checkValidCountryParameter(defender);

        DiceRoll attackerDiceResult = mockDice.rollDice(amountDice,1);
        Integer defenderAmountDice = defender.diceAmount();
        DiceRoll defenderDiceResult = mockDice.rollDice(defenderAmountDice,0);

        Integer stop = amountDice > defenderAmountDice ? defenderAmountDice : amountDice;

        return mockDice.diceRoundResults(attackerDiceResult,defenderDiceResult, stop);
    }
}
