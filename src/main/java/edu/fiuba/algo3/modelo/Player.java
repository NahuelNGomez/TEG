package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public class Player {
    private String name;
    private String color;
    private ArrayList dominatedCountries = new ArrayList();

    public Player(String color) {
        this.color = color;
        //this.name = name;
    }
    public int cantidadPaises(){
        return dominatedCountries.size();
    }
    public void addCountry(Country country){
        dominatedCountries.add(country);
    }

    public Player dominatedCountry(Country country){
        boolean isDominated = dominatedCountries.contains(country);
        if(isDominated){
            return this;
        }
        return null;
    }
}
