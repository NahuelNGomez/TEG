package edu.fiuba.algo3.modelo;

public class MockDice {
    public static MockDice singleton = null;

    private MockDice(){}

    public static MockDice create(){
        if (Dice.singleton == null)
            singleton = new MockDice();
        return singleton;
    }

    public DiceRoll rollDice(int diceAmount, int winner) {
        DiceRoll result = new DiceRoll();

        if(winner == 1){
            for(int i = 0; i < diceAmount; i++){
                result.addResult(6);
            }

        } else if(winner == 0){
            for(int i = 0; i < diceAmount; i++){
                result.addResult(1);
            }
        }
        return result;
    }

    public Integer[] diceRoundResults(DiceRoll attackerDice, DiceRoll defenderDice, Integer stop) {
        return attackerDice.compareResultsWith(defenderDice,stop);
    }


}
