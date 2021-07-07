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
    /* Última modificación. Pide un input de cada pais al que el jugador desea colocar un ejercito.

    public void setArmy(int amount){
        for(int i = 0; i < amount; i++){
            Scanner myObj = new Scanner(System.in);

            System.out.println("Country you want to add an army:");

            String name = myObj.nextLine();

            Country country = getCountry(name);
            country.addArmy(1);

        }


    }
*/
    private Country getCountry(String name){

        for( Country eachCountry: dominatedCountries){

            if(eachCountry.getName() == name){
               return  eachCountry;
            }
        }
        return null;
    }
}
