package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String color;
    private ArrayList <Country>dominatedCountries;

    public Player(String color) {
        this.color = color;
        dominatedCountries = new ArrayList();
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

    public boolean correctAmountOfArmy( Integer expectedAmount){
        Integer amount = 0;
         for(Country eachCountry: dominatedCountries){
             amount = amount + eachCountry.getArmyAmount();
         }

         return (amount == expectedAmount);
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

    public void firstPlacementRound(Integer maxPlacement) { //SE ELIGEN LOS PAISES Y CANTIDAD AL AZAR
        Random rand = new Random();
        while(maxPlacement > 0){

            Integer randIndex = rand.nextInt(dominatedCountries.size());
            Integer randIndex2 = rand.nextInt(maxPlacement) + 1;

            Country countryToAdd = dominatedCountries.get(randIndex);
            countryToAdd.addArmy(randIndex2);

            maxPlacement -= randIndex2;
        }
    }

    /*public boolean canBeInvaded(String countryName){
        Country country = getCountry(countryName);
        if(country != null){
                return true;
        }
        return false;
    }*/

    public Integer cantidadPaises(){
        return dominatedCountries.size();
    }


    public ArrayList<Country> paisesDominados() {
        return dominatedCountries;
    }

    public void setDominatedCountries(ArrayList<Country> continent) {
        dominatedCountries = continent;
    }

    public Country randomDominatedCountry() {
        Random rand = new Random();
        return(dominatedCountries.get(rand.nextInt(dominatedCountries.size())));
    }
}
