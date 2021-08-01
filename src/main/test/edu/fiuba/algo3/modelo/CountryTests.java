package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTests {
    Color firstColor = new Color("077bb");

    @Test
    public void eachPlayerStartsWithOneArmyInTheirAssignedCountry() throws EmptyCountryParameterException, NonExistentCountry {

        Player player = new Player(firstColor);
        Country francia = new Country("Francia");
        Integer expectedAmount = 1;

        player.addCountry(francia);
        assertEquals(true,  player.correctAmountOfArmyInCountry(francia,expectedAmount));
    }

    @Test
    public void wrongNumberOfDiceReturnsFalse(){
        Country francia = new Country("Francia");
        francia.addArmy(1);

        assertEquals(false, francia.canInvade(3));
    }

}
