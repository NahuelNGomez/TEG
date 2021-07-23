package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public class Continent {
    String name;
    ArrayList<Country> countriesInContinent;

    public Continent(String name){
        this.name = name;
        countriesInContinent = new ArrayList<>();
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

}
