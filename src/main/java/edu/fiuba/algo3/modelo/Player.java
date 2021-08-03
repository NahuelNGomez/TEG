package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.util.ArrayList;

public class Player {
    private Color color;
    private ArrayList <Country>dominatedCountries;
    private ArrayList<CountryCard> ownCountryCards;

    public Player(Color color) {
        this.color = color;
        dominatedCountries = new ArrayList();
        ownCountryCards= new ArrayList();
    }

    public void addCountryCard(CountryCard countryCard) {
        ownCountryCards.add(countryCard);
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    public void checkPlayerOwnsCountry(Country country) throws NonExistentCountry {
        if(!dominatedCountries.contains(country)){
            throw new NonExistentCountry();
        }
    }

    public void addCountry(Country country) throws EmptyCountryParameterException {
        checkValidCountryParameter(country);
        dominatedCountries.add(country);
        country.addArmy(1);
    }

    public void addArmyInCountry(int amount, Country country) throws EmptyCountryParameterException, NonExistentCountry {
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
        if(!country.stillHasArmy()){
            removeCountry(country);
            return true;
        }
        return false;
    }

    public boolean canInvade(Country country, int amountDice) throws EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country);
        checkPlayerOwnsCountry(country);
        return (country.canInvade(amountDice));
    }

    public void removeCountry(Country country){
        dominatedCountries.remove(country);
    }

    public boolean correctAmountOfCountryCards(int expectedAmount){
        return (expectedAmount == ownCountryCards.size());
    }

    public boolean correctAmountOfCountries( Integer expectedAmount){
        return (dominatedCountries.size() == expectedAmount);
    }

    public ArrayList<Country> getDominatedCountries(){
        return dominatedCountries;
    }

    public boolean correctAmountOfArmy( Integer expectedAmount){
        Integer amount = 0;
        for(Country eachCountry: dominatedCountries){
            amount = amount + eachCountry.getArmyAmount();
        }
        return (amount.equals(expectedAmount));
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

    public boolean objectiveCompleted() {
        return (dominatedCountries.size() == 30);
    }

    public Continent dominatedContinent(ArrayList<Continent> continents) {
        for(Continent continent : continents){
            if(dominatedCountries.containsAll(continent.getCountries())){
                return continent;
            }
        }
        return null;
    }

    public Integer amountOfDominatedCountries(){
        return dominatedCountries.size();
    }

    public Integer amountOfArmiesToAddInRegroupRound() {
        if((1 <= dominatedCountries.size()) && (dominatedCountries.size() <= 6)){
            return 3;
        }
        return (dominatedCountries.size() / 2);
    }

    public boolean containsCountry(Country country) {
        boolean contains = false;
        for(Country aCountry : dominatedCountries){
            if(aCountry.getName().equals(country.getName())) {
                contains = true;
            }
        }
        return contains;
    }

}
