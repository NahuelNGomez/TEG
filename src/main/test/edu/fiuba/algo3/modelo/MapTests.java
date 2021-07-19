package edu.fiuba.algo3.modelo;
import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MapTests {
    Map map = new Map();

    public MapTests() throws IOException {
    }

    @Test
    public void twoCountriesAreBordering() throws EmptyCountryParameterException, NonExistentCountry {
        Country granBretaña = new Country("Gran Bretania");
        Country islandia = new Country("Islandia");
        assertEquals(true, map.validateBorderingCountry(granBretaña, islandia));
    }
    @Test
    public void twoCountriesAreNotBordering() throws EmptyCountryParameterException, NonExistentCountry {
        Country granBretaña = new Country("Gran Bretania");
        Country polonia = new Country("Polonia");
        assertEquals(false, map.validateBorderingCountry(granBretaña, polonia));
    }
}
