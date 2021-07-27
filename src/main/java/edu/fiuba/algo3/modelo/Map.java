package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Map {
    private HashMap<Country, ArrayList<Country>> countries;
    private ArrayList<Continent> continents;
    private static Map map;
    private SettingMap set;
    private ArmyMover armyMover;

    private Map() throws IOException {
        countries = new HashMap<Country, ArrayList<Country>>();
        continents = new ArrayList<Continent>();
        if(set == null)  set = new SettingMap();
        this.getCountriesAndBorders();
    }

    public static Map get() throws IOException {
        if(map == null){
            map = new Map();
        }
        return map;
    }


    public void checkValidCountryParameter(Country country) throws EmptyCountryParameterException, NonExistentCountry {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
        searchKeyCountryInMap(country);
    }

    public ArrayList<Country> getContinent(Continent continent) throws NonExistentContinent {
        Continent newContinent = searchContinentInMap(continent);
        return (newContinent.getCountries());
    }

    public Country searchKeyCountryInMap(Country country) throws NonExistentCountry {
        Set<Country> keys = countries.keySet();
        Country searchedCountry = null;

        Country[] myArray = keys.toArray(new Country[keys.size()]);
        for(int index = 0 ; index < myArray.length; index++){
            if(myArray[index].getName().equals(country.getName())){
                searchedCountry = myArray[index];
            }
        }
        if(searchedCountry == null ) throw new NonExistentCountry();
        return searchedCountry;
    }

    private Country searchValueCountryInMap(Country keyCountry, Country searchedCountry){
        Country valueCountry = null;

        for( Country value : countries.get(keyCountry)){
            if(value.getName().equals(searchedCountry.getName())){
                valueCountry = value;
            }
        }
        return valueCountry;
    }


    public boolean validateBorderingCountry(Country attackingCountry, Country defendingCountry) throws EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(attackingCountry);
        checkValidCountryParameter(defendingCountry);

        Country keyAttackerCountry = searchKeyCountryInMap(attackingCountry);
        Country valueDefendingCountry = searchValueCountryInMap(keyAttackerCountry,defendingCountry);

        return countries.get(keyAttackerCountry).contains(valueDefendingCountry);
    }

    public Continent searchContinentInMap(Continent continent) throws NonExistentContinent {
        Continent newContinent = null;

        for( Continent value : continents) {
            if (value.getName().equals(continent.getName())) {
                newContinent = value;
            }
        }
        if(newContinent == null){
            throw new NonExistentContinent();
        }
        return newContinent;
    }

    public boolean sameAmountOfCountries(Continent continent, int expectedAmount) throws EmptyContinentParameterException, NonExistentContinent {
        if(continent == null){
            throw new EmptyContinentParameterException();
        }
        Continent newContinent = searchContinentInMap(continent);
        return (newContinent.sameNumberOfCountries(expectedAmount));

    }

    public void getCountriesAndBorders() throws IOException {
        countries = set.getCountriesAndBorders();
        continents = set.getContinents();
    }

    public void clean() {
        for(Country country : countries.keySet()){
            country.cleanArmy();

        }
    }

    public void regroup(Country country1, Country country2, Integer armyToRegroup) throws NonExistentCountry {

        Country mapCountry1 = searchKeyCountryInMap(country1);
        Country mapCountry2 = searchKeyCountryInMap(country2);

        ArmyMover.moveArmy(mapCountry1, mapCountry2, armyToRegroup);
    }
}
