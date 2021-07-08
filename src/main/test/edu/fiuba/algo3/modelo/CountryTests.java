package edu.fiuba.algo3.modelo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTests {

    @Test
    public void eachPlayerStartsWithOneArmyInTheirAssignedCountry(){

        Player player = new Player("077bb");
        Country country = new Country("Argentina");
        player.addCountry(country);
        assertEquals(1,  player.amountOfArmyIn("Argentina"));
    }
}
