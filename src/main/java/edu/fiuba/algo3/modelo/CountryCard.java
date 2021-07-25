package edu.fiuba.algo3.modelo;

public class CountryCard extends Card {
    private Country cardCountry;
    private String image;

    public CountryCard(Country country, String cardImage) {
        cardCountry = country;
        image = cardImage;
    }

    public Country getCountryCard(){
        return cardCountry;
    }

}
