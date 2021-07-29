package edu.fiuba.algo3.modelo;

public class ArmyMover {

    public static void moveArmy(Country mapCountry1, Country mapCountry2, Integer armyToRegroup) {
        mapCountry1.removeArmy(armyToRegroup);
        mapCountry2.addArmy(armyToRegroup);
    }
}
