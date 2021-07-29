package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingMap {
    private CSVReader buffer = new CSVReader("src/main/java/edu/fiuba/algo3/archivos/Teg - Fronteras.csv");
    private ArrayList<Continent> continents;
    private ArrayList<Continent> addedArmies;

    public SettingMap(){
        continents = new ArrayList<>();
        addedArmies = new ArrayList<>();

        addedArmies.add(new Continent("Asia",7));
        addedArmies.add(new Continent("Europa",5));
        addedArmies.add(new Continent("America del norte",5));
        addedArmies.add(new Continent("America del sur",3));
        addedArmies.add(new Continent("Africa",3));
        addedArmies.add(new Continent("Oceania",2));
    }

    public void addContinent(Continent addingContinent, Country newCountry) {
        boolean searchedContinent = false;

        for (int i = 0; continents.size() > i; i++) {
            if (addingContinent.getName().equals(continents.get(i).getName())) {
                continents.get(i).addCountry(newCountry);
                searchedContinent = true;
            }
        }
        if (!searchedContinent) {
            addingContinent.addCountry(newCountry);
            continents.add(addingContinent);
        }
    }

    public HashMap<Country, ArrayList<Country>> getCountriesAndBorders() {
        ArrayList<ArrayList<String>> readerReturn = buffer.readCSV();
        HashMap<Country, ArrayList<Country>> countries = new HashMap<>();
        Country newCountry = null;
        Integer amountOfArmyToAdd = 0;

        for (int i = 0; i < readerReturn.size(); i++) {
            ArrayList<Country> borderings = new ArrayList<>();
            for(int j = 2; j < readerReturn.get(i).size() ; j++) {
                Country addingCountry = new Country(readerReturn.get(i).get(j));
                borderings.add(addingCountry);
            }
            newCountry = new Country(readerReturn.get(i).get(0));

            for(Continent continent : addedArmies){
                if(continent.getName().equals(readerReturn.get(i).get(1))){
                    amountOfArmyToAdd = continent.getAmountOfArmyWhenDominated();
                }
            }
            Continent addingContinent = new Continent(readerReturn.get(i).get(1),amountOfArmyToAdd);
            addContinent(addingContinent, newCountry);

            countries.put(newCountry,borderings);
        }
        return countries;
    }

    public ArrayList<Continent> getContinents() {
        return continents;
    }
}