package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public class Battlefield {
    private Dice dice;

    public Battlefield(){
        dice = new Dice();
    }
    public Integer[] battle(Country attacker, int amountDice, Country defender) {
        //Check amount dice
        ArrayList<Integer> attackerDice = dice.rollDice(amountDice);
        Integer defenderAmountDice = defender.diceAmount();
        ArrayList<Integer> defenderDice = dice.rollDice(defenderAmountDice);
        Integer[] results = {0,0};
        Integer stop = amountDice > defenderAmountDice ? defenderAmountDice : amountDice;
        for(int i = 0; i < stop; i++){
            if(attackerDice.get(i) > defenderDice.get(i)){
                results[0]++;
            }
            else{
                results[1]++;
            }
        }

        return results;
    }
}
