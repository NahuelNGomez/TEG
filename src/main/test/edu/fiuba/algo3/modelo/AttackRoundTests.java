package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackRoundTests {
    int numberOfPlayers = 2;
    Game game = new Game(numberOfPlayers);
    AttackRound attackRound = new AttackRound(game.getPlayers(), game.getMap());
    Integer firstPlayerNumber = 1;
    Integer secondPlayerNumber = 2;

    public AttackRoundTests() throws IOException, InvalidNumberOfPlayers {
    }
    @Test
    public void successFullAttack() throws EmptyCountryParameterException, NonExistentPlayer, InvalidNumberOfPlayers, NonExistentCountry, IOException, InvalidAttack {

        Country francia = new Country("Francia");
        Country alemania = new Country("Alemania");

        game.addCountryToPlayer(francia,firstPlayerNumber);
        game.addCountryToPlayer(alemania,secondPlayerNumber);

        game.playersSetArmies(5, francia);
        game.playersSetArmies(1, alemania);

        attackRound.attack(francia, 3,alemania,0);

        assertEquals(false, (alemania.hasALargerArmy(2)));
    }

    @Test
    public void attackerCountryConquers() throws EmptyCountryParameterException, NonExistentPlayer, InvalidNumberOfPlayers, NonExistentCountry, IOException, InvalidAttack {

        Country francia = new Country("Francia");
        Country alemania = new Country("Alemania");

        game.addCountryToPlayer(francia,firstPlayerNumber);
        game.addCountryToPlayer(alemania,secondPlayerNumber);

        game.playersSetArmies(5, francia);
        attackRound.attack(francia, 1,alemania,0);

        assertEquals(true, game.playerDominatedCountry(firstPlayerNumber,alemania));
    }

   /*@Test
    public void anAttackRoundWithTwoPlayersWinsTheFirstOne() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        int firstPlacement = 5;
        int secondPlacement = 3;

        game.dealCountryCards();
        game.placementRound(firstPlacement);
        game.placementRound(secondPlacement);
        for(int i = 0; i < 4; i++){
            attackRound.startRound();;
        }
        Player winner = attackRound.startRound();
        int playerNumberOfTheExpectedWinner = 1;
        assertEquals(game.getPlayer(playerNumberOfTheExpectedWinner), winner);
    }*/
}
