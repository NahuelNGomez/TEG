package edu.fiuba.algo3.modelo;


public class Country {
    private String name;
    private int armyAmount;

    public Country(String name) {
        this.name = name;
        this.armyAmount = 0;
    }

    //rompe encapsulamiento -- EL DICCIONARIO NO FUNCIONA SIN UNA CLAVE STRING
    public String getName(){
        return this.name;
    }

    //rompe encapsulamiento -- shuffleCards
    public Integer getArmyAmount(){
        return this.armyAmount;
    }

    public boolean correctAmountOfArmyInCountry(Integer expectedAmount) {
        return this.armyAmount == expectedAmount;
    }

    public boolean stillHasArmy(){
        return this.armyAmount > 0;
    }

    public boolean canInvade(Integer amountDice){
        if(amountDice <= 3 && amountDice >= 1){
            if (armyAmount >= 3) {
                return (amountDice <= 3);
            } else {
                return (amountDice < armyAmount);
            }
        }
        return false;
    }

    public boolean validArmiesToAttack(){
        return (armyAmount > 1);
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
        if(armyAmount >= lostArmy) {
            armyAmount = armyAmount - lostArmy;
        } else {
            armyAmount = 0;
        }
    }

    public boolean hasALargerArmy(int armyAmount){
        return (this.armyAmount > armyAmount);
    }


    public void cleanArmy() {
        armyAmount = 0;
    }
}
