package edu.fiuba.algo3.modelo;
import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTests {

    @Test
    public void eachPlayerStartsWithOneArmyInTheirAssignedCountry() throws EmptyCountryParameterException, NonExistentCountry {

        Player player = new Player("077bb");
        Country argentina = new Country("Argentina");
        Integer expectedAmount = 1;

        player.addCountry(argentina);
        assertEquals(true,  player.correctAmountOfArmyInCountry(argentina,expectedAmount));
    }
}
