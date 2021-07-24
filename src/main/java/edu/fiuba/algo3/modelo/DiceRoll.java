package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class DiceRoll {
    private ArrayList<Integer> results;

    public DiceRoll(){
        results = new ArrayList<>();
    }

    public void addResult(Integer oneResult) {
        results.add(oneResult);
        Collections.sort(results, Collections.reverseOrder());
    }

    public Integer[] compareResultsWith(DiceRoll diceResult, Integer stop) {
        Integer[] returningResult = {0,0};

        for(int i = 0; i < stop; i++){
            if( results.get(i)  > diceResult.getResults().get(i) ){
                returningResult[0] = returningResult[0] + 1;
            } else {
                returningResult[1] = returningResult[1] + 1;
            }
        }
        return returningResult;
    }

    private ArrayList<Integer> getResults() {
        return results;
    }
}
