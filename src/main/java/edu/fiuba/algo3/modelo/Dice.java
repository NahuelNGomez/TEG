package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dice {
    public static Dice singleton = null;

    public static Dice create(){
        if (Dice.singleton == null)
            singleton = new Dice();
        return singleton;
    }

    public ArrayList<Integer> rollDice(int diceAmount) {
        ArrayList<Integer> diceResult = new ArrayList<Integer>();
        for(int i = 0; i < diceAmount; i++){
            Random rand = new Random();
            int diceNumber = rand.nextInt(6) + 1;
            diceResult.add(diceNumber);
        }
        Collections.sort(diceResult, Collections.reverseOrder());
        return diceResult;
    }

    public Integer[] diceRoundResults(ArrayList<Integer> attackerDice, ArrayList<Integer> defenderDice, Integer stop) {
        Integer[] results = {0,0};

        for(int i = 0; i < stop; i++){
            if( attackerDice.get(i)  > defenderDice.get(i) ){
                results[0]++;
            } else {
                results[1]++;
            }
        }
        return results;
    }
}
