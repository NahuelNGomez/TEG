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
    public void oneRoundGameCountryPlacement() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer armyToPlace = 5;
        Integer expectedAmount = 30;
        Game game = new Game(numberOfPlayers);  //stackoverflow si el map es un singleton
        game.dealCountryCards();
        game.placementRound(armyToPlace);

        assertEquals(true ,game.correctAmountOfArmy(firstPlayerNumber, expectedAmount));
    }

    @Test
    public void oneRoundGameCountryPlacementAndSecondPlayerControlsAsia() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer, NonExistentContinent {
        Integer numberOfPlayers = 3;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;
        Integer thirdPlayerNumber = 3;
        Integer armyToPlace = 8;
        Integer expectedAmount = 23;

        Game game = new Game(numberOfPlayers);  //stackoverflow si el map es un singleton
        Continent continent = new Continent("Asia");


        game.addContinentToPlayer(firstPlayerNumber, continent);
        game.addCountryToPlayer(new Country("Suecia"), secondPlayerNumber);
        game.addCountryToPlayer(new Country("Brasil"), thirdPlayerNumber);

        game.placementRound(armyToPlace);

        assertEquals(true ,game.correctAmountOfArmy(firstPlayerNumber, expectedAmount));
    }

    @Test
    public void oneAttackRound() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer, InvalidNumberOfDices {
        Integer numberOfPlayers = 2;
        Integer firstPlayerNumber = 1;
        Integer secondPlayerNumber = 2;
        Game game = new Game(numberOfPlayers);
        Country china = new Country("China");
        Country iran = new Country("Iran");
        Country siberia = new Country("Siberia");
        Country gobi = new Country("Gobi");
        game.addCountryToPlayer(china, firstPlayerNumber);
        game.addCountryToPlayer(iran, secondPlayerNumber);
        game.addCountryToPlayer(siberia, secondPlayerNumber);
        game.addCountryToPlayer(gobi, secondPlayerNumber);
        game.playersSetArmies(5, china);

        game.attackRound();
        game.attackRound();
        assertEquals(true ,game.playerDominatedCountry(firstPlayerNumber, siberia) || game.playerDominatedCountry(firstPlayerNumber, iran) || game.playerDominatedCountry(firstPlayerNumber, gobi));
       // assertEquals(true ,game.playerDominatedCountry(firstPlayerNumber, iran));
       // assertEquals(false ,game.playerDominatedCountry(secondPlayerNumber, siberia));
       // assertEquals(false ,game.playerDominatedCountry(secondPlayerNumber, iran));

    }



}
