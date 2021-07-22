package edu.fiuba.algo3.modelo;

<<<<<<< HEAD
=======
import edu.fiuba.algo3.modelo.exceptions.*;
>>>>>>> develop
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {
<<<<<<< HEAD
    Player player = new Player("077bb");
    Country country = new Country("Argentina");
    @Test
    public void paisDominadoPorJugador(){
        player.addCountry(country);
        assertEquals(player, player.dominatedCountry(country));
    }

    @Test
    public void paisNoDominadoPorJugador(){
        assertNull(player.dominatedCountry(country));
    }
}
=======
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
        player.addArmyinCountry(3, granBretaña);
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
        assertThrows(NonExistentCountry.class, () -> player.addArmyinCountry(1,granBretaña));
    }

    @Test
    public void emptyCountryRaisesException(){
        assertThrows(EmptyCountryParameterException.class, () -> player.addArmyinCountry(1,null));
    }
}
>>>>>>> develop
