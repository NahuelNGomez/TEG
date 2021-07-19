package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {
    Player player = new Player("077bb");
    Country granBretaña = new Country("Gran Bretaña");

    @Test
    public void dominatedCountryByPlayer() throws EmptyCountryParameterException {
        player.addCountry(granBretaña);
        assertEquals(true, player.dominatedCountry(granBretaña));
    }

    @Test
    public void notDominatedCountryByPlayer() throws EmptyCountryParameterException {
        assertEquals(false, player.dominatedCountry(granBretaña));
    }

    @Test
    public void theAssignAmountOfArmiesPerPlayerIsCorrect() throws EmptyCountryParameterException, NonExistentCountry {
        Integer expectedAmount = 4;
        player.addCountry(granBretaña);
        player.addArmyinCountry(3, granBretaña);
        assertEquals(true, player.correctAmountOfArmyInCountry(granBretaña,expectedAmount));
    }

    @Test
    public void countryRemovedIsNotOnTheListAnyMore() throws EmptyCountryParameterException {
        player.addCountry(granBretaña);
        player.removeCountry(granBretaña);
        assertEquals(false, player.dominatedCountry(granBretaña));
    }
}