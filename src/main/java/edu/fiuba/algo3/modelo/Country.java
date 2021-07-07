package edu.fiuba.algo3.modelo;

public class Country {
    private String name;
    private int armyAmount;

    public Country(String name) {
        this.name = name;
        this.armyAmount = 1;

    }

    public int getArmyAmount(){
        return armyAmount;
    }

    public String getName(){
        return this.name;
    }
    public void addArmy(int amount){
        armyAmount = armyAmount + amount;
    }
}
