package edu.fiuba.algo3.modelo;

import java.util.Random;

public class Dice {
    public static Dice singleton = null;

    private Dice(){}

    public static Dice create(){
        if (Dice.singleton == null)
            singleton = new Dice();
        return singleton;
    }

    public DiceRoll rollDice(int diceAmount) {
        DiceRoll result = new DiceRoll();

        for(int i = 0; i < diceAmount; i++){
            Random rand = new Random();
            int diceNumber = rand.nextInt(6) + 1;
            result.addResult(diceNumber);
        }
        return result;
    }

    public Integer[] diceRoundResults(DiceRoll attackerDice, DiceRoll defenderDice, Integer stop) {
        return attackerDice.compareResultsWith(defenderDice,stop);
    }
}
