package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public class Continent {
    String name;
    ArrayList<Country> countriesInContinent;

    public Continent(String name){
        this.name = name;
        countriesInContinent = new ArrayList<>();
    }

}
