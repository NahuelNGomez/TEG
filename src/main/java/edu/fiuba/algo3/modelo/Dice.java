package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dice {
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
}
