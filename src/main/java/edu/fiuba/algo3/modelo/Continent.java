package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public class Continent {
    private String name;
    private ArrayList<Country> countriesInContinent;
    private Integer amountOfArmyWhenDominated;

    public Continent(String name, Integer num){
        this.name = name;
        countriesInContinent = new ArrayList<>();
        amountOfArmyWhenDominated = num;
    }

    public String getName(){
        return name;
    }

    public void addCountry(Country newCountry){
        countriesInContinent.add(newCountry);
    }

    public boolean sameNumberOfCountries(int amount){
        return (amount == countriesInContinent.size());
    }

    public ArrayList<Country> getCountries(){
        return countriesInContinent;
    }

    public Integer getAmountOfArmyWhenDominated() {
        return amountOfArmyWhenDominated;
    }

    public boolean correctAmountOfArmyInContinentCountries(Integer numberOfArmy) {
        Integer total = 0;
        for(Country country : countriesInContinent){
            total = total + country.getArmyAmount();
        }
        return (total == numberOfArmy);
    }

    public Integer amountOfArmy() {
        Integer total = 0;
        for(Country country : countriesInContinent){
            total = total + country.getArmyAmount();
        }
        return total;
    }
}
