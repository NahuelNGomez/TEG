package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Map {
    private HashMap<Country, ArrayList<Country>> countries;
    //private HashMap<Continent, ArrayList<Country>> continents;


    public Map() throws IOException {
        countries = new HashMap<Country, ArrayList<Country>>();
        this.getCountriesAndBorders(countries);
    }

    public void checkValidCountryParameter(Country country) throws EmptyCountryParameterException, NonExistentCountry {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
        searchKeyCountryInMap(country);
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


        /*for ( int i = 0; i < keys.size(); i++ ) {

        }*/
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


    public void getCountriesAndBorders(HashMap<Country, ArrayList<Country>> countries) throws IOException {
        Country newCountry = null;
        String SEPARADOR = ",";
        BufferedReader bufferLectura = null;
        try {
            bufferLectura = new BufferedReader(new FileReader("src/main/java/edu/fiuba/algo3/archivos/Teg - Fronteras.csv"));
            String linea = bufferLectura.readLine();

            while (linea != null) {
                String[] campos = linea.split(SEPARADOR);
                ArrayList<Country> limitrofes = new ArrayList<>();
                for( int i = 2; i < campos.length; i++){
                    Country addingCountry = new Country(campos[i]);
                    limitrofes.add(addingCountry);
                }
                newCountry = new Country(campos[0]);
                countries.put(newCountry,limitrofes);
                linea = bufferLectura.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

