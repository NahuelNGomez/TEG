package edu.fiuba.algo3.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {
    Player player = new Player("077bb");
    Country country = new Country("Argentina");

    @Test
    public void dominatedCountryByPlayer(){
        player.addCountry(country);
        assertEquals(player, player.dominatedCountry(country));
    }

    @Test
    public void notDominatedCountryByPlayer(){
        assertNull(player.dominatedCountry(country));
    }

    @Test
    public void theAssignAmountOfArmiesPerPlayerIsCorrect(){
        player.addCountry(country);
        player.setArmy(3, "Argentina");
        assertEquals(4, country.getArmyAmount());
    }
}