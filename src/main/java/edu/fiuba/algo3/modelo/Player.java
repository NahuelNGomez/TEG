package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private String color;
    private ArrayList <Country>dominatedCountries = new ArrayList<Country>();

    public Player(String color) {
        this.color = color;
        //this.name = name;
    }

    public String getColor(){
        return color;
    }

    public int countryAmount(){
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

    public int amountOfArmyIn(String name){
       return (getCountry(name).getArmyAmount());
    }
    public int amountOfArmyIn(Country country){
        return (country.getArmyAmount());
    }
    public void setArmy(int amount, String countryName){

        Country country = getCountry(countryName);
        if(country == null){
            System.out.println("Break");
        }
        country.addArmy(amount);


    }

    public Country getCountry(String name){

        for( Country eachCountry: dominatedCountries){

            if(eachCountry.getName() == name){
               return  eachCountry;
            }
        }
        return null;
    }

    public void removeArmy(Integer lostArmy, Country country) {
        country.removeArmy(lostArmy);

    }
    public Country verifyOwnCountries(){
        for(Country eachCountry: dominatedCountries){

            if(eachCountry.getArmyAmount() == 0){
                removeCountry(eachCountry);
                return eachCountry;
            }
        }
        return null;

    }

    public void removeCountry(Country country){
        dominatedCountries.remove(country);
    }


}
