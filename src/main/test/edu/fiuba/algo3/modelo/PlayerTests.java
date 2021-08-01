package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {
    Color firstColor = new Color("077bb");
    Player player = new Player(firstColor);
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
        player.addArmyInCountry(3, granBretaña);
        assertEquals(true, player.correctAmountOfArmyInCountry(granBretaña,expectedAmount));
    }

    @Test
    public void countryRemovedIsNotOnTheListAnyMore() throws EmptyCountryParameterException {
        player.addCountry(granBretaña);
        player.removeCountry(granBretaña);
        assertEquals(false, player.dominatedCountry(granBretaña));
    }

    @Test
    public void nonExistentCountryRaisesException(){
        assertThrows(NonExistentCountry.class, () -> player.addArmyInCountry(1,granBretaña));
    }

    @Test
    public void emptyCountryRaisesException(){
        assertThrows(EmptyCountryParameterException.class, () -> player.addArmyInCountry(1,null));
    }
}
