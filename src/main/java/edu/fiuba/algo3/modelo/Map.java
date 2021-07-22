package edu.fiuba.algo3.modelo;

<<<<<<< HEAD
public class Map {
    private Country Pais1;
    private Country Pais2;
}
=======
import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Map {
    private HashMap<Country, ArrayList<Country>> countries;
    private ArrayList<Continent> continents;
    private static Map map;

    private Map() throws IOException {
        countries = new HashMap<Country, ArrayList<Country>>();
        continents = new ArrayList<Continent>();
        this.getCountriesAndBorders(countries);
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

   public void addContinent(Continent addingContinent, Country newCountry){
        boolean searchedContinent = false;

        for(int i = 0; continents.size() > i; i++){

            if(addingContinent.getName().equals(continents.get(i).getName())){
                continents.get(i).addCountry(newCountry);
                searchedContinent = true;
            }
        }
        if(!searchedContinent){
            addingContinent.addCountry(newCountry);
            continents.add(addingContinent);
        }

   }

    public void getCountriesAndBorders(HashMap<Country, ArrayList<Country>> countries) throws IOException {
        Country newCountry = null;
        String SEPARATOR = ",";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/java/edu/fiuba/algo3/archivos/Teg - Fronteras.csv"));
            String line = reader.readLine();

            while (line != null) {
                String[] fields = line.split(SEPARATOR);
                ArrayList<Country> borderings = new ArrayList<>();

                for( int i = 2; i < fields.length; i++){
                    Country addingCountry = new Country(fields[i]);
                    borderings.add(addingCountry);
                }

                Continent addingContinent = new Continent(fields[1]);

                newCountry = new Country(fields[0]);
                addContinent(addingContinent, newCountry);

                countries.put(newCountry,borderings);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clean() {
        for(Country country : countries.keySet()){
            country.cleanArmy();
        }
    }
}

>>>>>>> develop
