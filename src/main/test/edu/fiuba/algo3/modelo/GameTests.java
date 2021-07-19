package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    @Test
    public void assignCardNoCountriesLeft() throws NonExistentPlayer, InvalidNumberOfPlayers, EmptyCountryParameterException, IOException, NonExistentCountry {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;
        Integer expectedAmountOfCountries = 25;

        Game game = new Game(numberOfPlayers);
        game.dealCountryCards();

        assertEquals(true, game.correctAmountOfCountries(firstPlayerNumber, expectedAmountOfCountries ));
    }

    @Test
    public void assignCountryCardsWithLeftCountries() throws NonExistentPlayer, InvalidNumberOfPlayers, EmptyCountryParameterException, IOException, NonExistentCountry {
        Integer numberOfPlayers = 3;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;
        Integer thirdPlayerNumber = 3;

        Integer expectedAmountOfCountriesFirstPlayer = 17;
        Integer expectedAmountOfCountriesSecondPlayer = 17;
        Integer expectedAmountOfCountriesThirdPlayer = 16;

        Game game = new Game(numberOfPlayers);
        game.dealCountryCards();

        assertEquals(true, game.correctAmountOfCountries(firstPlayerNumber, expectedAmountOfCountriesFirstPlayer ));
        assertEquals(true, game.correctAmountOfCountries(secondPlayerNumber, expectedAmountOfCountriesSecondPlayer ));
        assertEquals(true, game.correctAmountOfCountries(thirdPlayerNumber, expectedAmountOfCountriesThirdPlayer ));
    }

    @Test
    public void successFullAttack() throws EmptyCountryParameterException, NonExistentPlayer, InvalidNumberOfPlayers, NonExistentCountry, InvalidNumberOfDices, IOException {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;

        Game game = new Game(numberOfPlayers);

        Country francia = new Country("Francia");
        Country granBretaña = new Country("Gran Bretania");

        game.addCountryToPlayer(francia,firstPlayerNumber);
        game.addCountryToPlayer(granBretaña,secondPlayerNumber);

        game.playersSetArmies(5, francia);
        game.playersSetArmies(1, granBretaña);

        game.attack(francia, 3,granBretaña);

        //CON BATTLEFIELD Y DADO MOCK SIEMPRE GANA EL ATACANTE
        assertEquals(false, (granBretaña.hasALargerArmy(2))/* || francia.getArmyAmount() < 6)*/);
    }
    @Test
    public void attackerCountryConquers() throws EmptyCountryParameterException, NonExistentPlayer, InvalidNumberOfPlayers, NonExistentCountry, InvalidNumberOfDices, IOException {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;

        Game game = new Game(numberOfPlayers);

        Country francia = new Country("Francia");
        Country alemania = new Country("Alemania");

        game.addCountryToPlayer(francia,firstPlayerNumber);
        game.addCountryToPlayer(alemania,secondPlayerNumber);

        game.playersSetArmies(5, francia);
        game.attack(francia, 1,alemania);

        assertEquals(true, game.playerDominatedCountry(firstPlayerNumber,alemania));
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

    @Test
    public void oneRoundGameCountryPlacement() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;

        Game game = new Game(numberOfPlayers);  //stackoverflow si el map es un singleton
        game.dealCountryCards();
        game.firstPlacementRound();

    }

}
