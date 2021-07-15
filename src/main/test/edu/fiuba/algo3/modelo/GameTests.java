package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void assignCardNoCountriesLeft() throws NonExistentPlayer, InvalidNumberOfPlayers, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer expectedAmountOfCountries = 5;

        Game game = new Game(numberOfPlayers);
        game.dealCountryCards();

        assertEquals(true, game.correctAmountOfCountries(firstPlayerNumber, expectedAmountOfCountries ));
    }
    @Test
    public void assignCountryCardsWithLeftCountries() throws NonExistentPlayer, InvalidNumberOfPlayers, EmptyCountryParameterException {
        Integer numberOfPlayers = 3;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;
        Integer thirdPlayerNumber = 3;

        Integer expectedAmountOfCountriesFirstPlayer = 4;
        Integer expectedAmountOfCountriesSecondPlayer = 3;
        Integer expectedAmountOfCountriesThirdPlayer = 3;

        Game game = new Game(numberOfPlayers);
        game.dealCountryCards();

        assertEquals(true, game.correctAmountOfCountries(firstPlayerNumber, expectedAmountOfCountriesFirstPlayer ));
        assertEquals(true, game.correctAmountOfCountries(secondPlayerNumber, expectedAmountOfCountriesSecondPlayer ));
        assertEquals(true, game.correctAmountOfCountries(thirdPlayerNumber, expectedAmountOfCountriesThirdPlayer ));
    }

    @Test
    public void successFullAttack() throws EmptyCountryParameterException, NonExistentPlayer, InvalidNumberOfPlayers, NonExistentCountry, InvalidNumberOfDices {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;

        Game game = new Game(numberOfPlayers);

        Country canada = new Country("Canada");
        Country newYork = new Country("New York");

        game.addCountryToPlayer(canada,firstPlayerNumber);
        game.addCountryToPlayer(newYork,secondPlayerNumber);

        game.playersSetArmies(5, canada);
        game.playersSetArmies(1, newYork);

        game.attack(canada, 3,newYork);

        assertEquals(true, (newYork.getArmyAmount() < 2 || canada.getArmyAmount() < 6));
    }
    @Test
    public void attackerCountryConquers() throws EmptyCountryParameterException, NonExistentPlayer, InvalidNumberOfPlayers, NonExistentCountry, InvalidNumberOfDices {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;

        Game game = new Game(numberOfPlayers);

        Country canada = new Country("Canada");
        Country newYork = new Country("New York");

        game.addCountryToPlayer(canada,firstPlayerNumber);
        game.addCountryToPlayer(newYork,secondPlayerNumber);

        game.playersSetArmies(5, canada);
        game.attack(canada, 3,newYork);

        assertEquals(true, game.playerDominatedCountry(firstPlayerNumber,newYork));
    }

    @Test
    public void gameWithoutEnoughPlayersRaisesException(){
        Integer numberOfPlayers = 1;
        assertThrows(InvalidNumberOfPlayers.class, () -> new Game(numberOfPlayers));
    }

    @Test
    public void gameWithTooManyPlayersRaisesException(){
        Integer numberOfPlayers = 8;
        assertThrows(InvalidNumberOfPlayers.class, () -> new Game(numberOfPlayers));
    }

}
