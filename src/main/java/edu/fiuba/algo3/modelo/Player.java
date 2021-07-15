package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import edu.fiuba.algo3.modelo.exceptions.NonExistentPlayer;

import java.util.ArrayList;

public class Player {
    private String color;
    private ArrayList <Country>dominatedCountries = new ArrayList<Country>();

    public Player(String color) {
        this.color = color;
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    public void checkPlayerOwnsCountry(Country country) throws NonExistentCountry {
        if(dominatedCountries.contains(country) == false){
            throw new NonExistentCountry();
        }
    }

    //rompe encapsulamiento
    public Integer countryAmount(){
        return dominatedCountries.size();
    }

    public void addCountry(Country country) throws EmptyCountryParameterException {
        checkValidCountryParameter(country);
        dominatedCountries.add(country);
    }


    public boolean correctAmountOfCountries( Integer expectedAmount){
        return (dominatedCountries.size() == expectedAmount);
    }

    public boolean dominatedCountry(Country country) throws EmptyCountryParameterException {
        checkValidCountryParameter(country);
        return dominatedCountries.contains(country);
    }

    public boolean correctAmountOfArmyInCountry(Country country, Integer expectedAmount) throws EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country);
        checkPlayerOwnsCountry(country);
        return country.correctAmountOfArmyInCountry(expectedAmount);
    }


    public void addArmyinCountry(int amount, Country country) throws EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country);
        checkPlayerOwnsCountry(country);
        if(dominatedCountries.contains(country)){
            country.addArmy(amount);
        }

    }

    public boolean isSearchedCountry (Country country) throws EmptyCountryParameterException {
        checkValidCountryParameter(country);
        for( Country eachCountry: dominatedCountries ){
            if(eachCountry == country){
                return true;
            }
        }
        return false;
    }

    public boolean removeArmy(Integer lostArmy, Country country) throws EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country);
        checkPlayerOwnsCountry(country);

        country.removeArmy(lostArmy);
        if(country.stillHasArmy() == false){
            removeCountry(country);
            return true;
        }
        return false;
    }

    public boolean canInvade(Country country, int amountDice) throws EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country);
        checkPlayerOwnsCountry(country);

        if(country.canInvade(amountDice)){
            return true;
        }
        return false;
    }

    public void removeCountry(Country country){
        dominatedCountries.remove(country);
    }

    /*public boolean canBeInvaded(String countryName){
        Country country = getCountry(countryName);
        if(country != null){
                return true;
        }
        return false;
    }*/




}