package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingGame {
    private CSVReader buffer = new CSVReader("src/main/java/edu/fiuba/algo3/archivos/Teg - Cartas.csv");

    public ArrayList<CountryCard> getCountryCards(){
        ArrayList<ArrayList<String>> readerReturn = buffer.readCSV();
        ArrayList<CountryCard> countryCards = new ArrayList<>();


        for(int i = 0; i < readerReturn.size(); i++){
            Country newCountry = new Country(readerReturn.get(i).get(0));
            CountryCard newCard = new CountryCard(newCountry,readerReturn.get(i).get(1));
            countryCards.add(newCard);
        }
        return countryCards;
    }

}
