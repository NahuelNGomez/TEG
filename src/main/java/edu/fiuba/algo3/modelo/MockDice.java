package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public class MockDice {
    public static MockDice singleton = null;

    private MockDice(){}

    public static MockDice create(){
        if (Dice.singleton == null)
            singleton = new MockDice();
        return singleton;
    }

    public ArrayList<Integer> rollDice(int diceAmount, int winner) {
        ArrayList<Integer> diceResult = new ArrayList<Integer>();
        if(winner == 1){
            for(int i = 0; i < diceAmount; i++){
                diceResult.add(6);
            }

        } else if(winner == 0){
            for(int i = 0; i < diceAmount; i++){
                diceResult.add(1);
            }
        }
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
