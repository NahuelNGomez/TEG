package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {
    Player player = new Player("077bb");
    Country argentina = new Country("Argentina");

    @Test
    public void dominatedCountryByPlayer() throws EmptyCountryParameterException {
        player.addCountry(argentina);
        assertEquals(true, player.dominatedCountry(argentina));
    }

    @Test
    public void notDominatedCountryByPlayer() throws EmptyCountryParameterException {
        assertEquals(false, player.dominatedCountry(argentina));
    }

    @Test
    public void theAssignAmountOfArmiesPerPlayerIsCorrect() throws EmptyCountryParameterException, NonExistentCountry {
        Integer expectedAmount = 4;
        player.addCountry(argentina);
        player.addArmyinCountry(3, argentina);
        assertEquals(true, player.correctAmountOfArmyInCountry(argentina,expectedAmount));
    }

    @Test
    public void countryRemovedIsNotOnTheListAnyMore() throws EmptyCountryParameterException {
        player.addCountry(argentina);
        player.removeCountry(argentina);
        assertEquals(false, player.dominatedCountry(argentina));
    }
}