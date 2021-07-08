package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void assignCardNoCountriesLeft(){
        Game game = new Game(2);
        game.dealCountryCards();
        assertEquals(5, game.getPlayer("07bb").countryAmount());
        assertEquals(5, game.getPlayer("cc3311").countryAmount());
    }
    @Test
    public void assignCountryCardsWithLeftCountries(){
        Game game = new Game(3);
        game.dealCountryCards();
        assertEquals(4, game.getPlayer("07bb").countryAmount());
        assertEquals(3, game.getPlayer("cc3311").countryAmount());
        assertEquals(3, game.getPlayer("ee7733").countryAmount());
    }

    @Test
    public void attackerCountryConquers(){
        Game game = new Game(2);
        Player player1 = game.getPlayer("07bb");
        Country canada = new Country("Canada");
        player1.addCountry(canada);
        Player player2 = game.getPlayer("cc3311");
        Country newYork = new Country("New York");
        player2.addCountry(newYork);
        game.playersSetArmies(5, "Canada", "07bb");
        game.playersSetArmies(1, "New York", "cc3311");
        game.attack("Canada", 3,"New York");

        assertEquals(true, (newYork.getArmyAmount() < 2 || canada.getArmyAmount() < 6));
    }

}
