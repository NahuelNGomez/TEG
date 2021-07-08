package edu.fiuba.algo3.modelo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapTests {
    Map map = new Map();
    @Test
    public void twoCountriesAreBordering(){
        assertEquals(true, map.validateBorderingCountry("Canada", "New York"));
    }
    @Test
    public void twoCountriesAreNotBordering(){
        assertEquals(false, map.validateBorderingCountry("Canada", "Mexico"));
    }
}
