package edu.fiuba.algo3.modelo;
import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapTests {
    Map map = new Map();
    @Test
    public void twoCountriesAreBordering() throws EmptyCountryParameterException {
        Country canada = new Country("Canada");
        Country newYork = new Country("New York");
        assertEquals(true, map.validateBorderingCountry(canada, newYork));
    }
    @Test
    public void twoCountriesAreNotBordering() throws EmptyCountryParameterException {
        Country canada = new Country("Canada");
        Country mexico = new Country("Mexico");
        assertEquals(false, map.validateBorderingCountry(canada, mexico));
    }
}
