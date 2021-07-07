package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void assignCardNoCountriesLeft(){
        Game game = new Game(2);
        game.dealCountryCards();
        assertEquals(5, game.getPlayer(0).countryAmount());
        assertEquals(5, game.getPlayer(1).countryAmount());
    }
    @Test
    public void assignCountryCardsWithLeftCountries(){
        Game game = new Game(3);
        game.dealCountryCards();
        assertEquals(4, game.getPlayer(0).countryAmount());
        assertEquals(3, game.getPlayer(1).countryAmount());
        assertEquals(3, game.getPlayer(2).countryAmount());
    }
}
