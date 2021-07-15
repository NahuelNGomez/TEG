package edu.fiuba.algo3.modelo;
import java.util.ArrayList;

public class Country {
    private String name;
    private int armyAmount;

    public Country(String name) {
        this.name = name;
        this.armyAmount = 1;

    }

    //rompe encapsulamiento -- EL DICCIONARIO NO FUNCIONA SIN UNA CLAVE STRING
    public String getName(){
        return this.name;
    }

    //rompe encapsulamiento -- shuffleCards
    public Integer getArmyAmount(){
        return this.armyAmount;
    }

    public boolean correctAmountOfArmyInCountry(Integer expectedAmount){
        return this.armyAmount == expectedAmount;
    }

    public boolean stillHasArmy(){
        return this.armyAmount > 0;
    }

    public boolean canInvade(Integer amountDice){
        return this.armyAmount > amountDice;
    }

    public void addArmy(int amount){
        armyAmount = armyAmount + amount;
    }

    public Integer diceAmount() {
        if(armyAmount < 3){
            return armyAmount;
        }
        return 3;
    }

    public void removeArmy(Integer lostArmy) {
        if(armyAmount >= lostArmy){
            armyAmount = armyAmount - lostArmy;
        } else{
            armyAmount = 0;
        }
    }


}
