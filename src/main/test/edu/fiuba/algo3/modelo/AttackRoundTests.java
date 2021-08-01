package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AttackRoundTests {
    int numberOfPlayers = 2;
    Game game = new Game(numberOfPlayers);

    AttackRound attackRound = new AttackRound(game.getPlayers(), game.getMap(), game.getCountryCards());

    Integer firstPlayerNumber = 1;
    Integer secondPlayerNumber = 2;

    public AttackRoundTests() throws IOException, InvalidNumberOfPlayers, NonExistentCountry, EmptyCountryParameterException {
    }

    @Test
    public void successFullAttack() throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry, InvalidAttack {
        Country francia = new Country("Francia");
        Country alemania = new Country("Alemania");

        Integer expectedAmount = 1;

        game.addCountryToPlayer(francia,firstPlayerNumber);
        game.addCountryToPlayer(alemania,secondPlayerNumber);

        game.playerSetArmies(firstPlayerNumber,5, francia);
        game.playerSetArmies(secondPlayerNumber,1, alemania);

        attackRound.attack(game.getPlayer(firstPlayerNumber),francia, alemania,3,0);

        assertTrue(attackRound.correctAmountOfArmyInCountry(alemania,expectedAmount));
    }

    @Test
    public void attackerCountryConquers() throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry, IOException, InvalidAttack {

        Country francia = new Country("Francia");
        Country alemania = new Country("Alemania");

        game.addCountryToPlayer(francia,firstPlayerNumber);
        game.addCountryToPlayer(alemania,secondPlayerNumber);

        game.playerSetArmies(firstPlayerNumber,5, francia);
        game.playerSetArmies(secondPlayerNumber,1, alemania);

        attackRound.attack(game.getPlayer(firstPlayerNumber),francia, alemania,2,0);
        assertTrue(game.playerDominatedCountry(firstPlayerNumber, alemania));
    }

    @Test
    public void conquerAndWinACountryCard() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, InvalidAttack {

        Integer expectedAmount = 1;

        Country francia = new Country("Francia");
        Country alemania = new Country("Alemania");

        game.addCountryToPlayer(francia,firstPlayerNumber);
        game.addCountryToPlayer(alemania,secondPlayerNumber);

        game.playerSetArmies(firstPlayerNumber,5, francia);
        game.playerSetArmies(secondPlayerNumber,1, alemania);

        attackRound.attack(game.getPlayer(firstPlayerNumber),francia, alemania,2,0);

        assertTrue(game.playerDominatedCountry(firstPlayerNumber, alemania));
        assertTrue(game.correctAmountOfCountryCards(firstPlayerNumber, expectedAmount));
    }

   @Test
    public void anAttackRoundWithTwoPlayersWinsTheFirstOne() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, InvalidPlacement, InvalidAttack {
       Country francia = new Country("Francia");Country granBretania = new Country("Gran Bretania");Country tartaria = new Country("Tartaria");Country mongolia = new Country("Mongolia");Country zaire = new Country("Zaire");Country polonia = new Country("Polonia");Country oregon = new Country("Oregon");Country etiopia = new Country("Etiopia");Country chile = new Country("Chile");Country australia = new Country("Australia");Country kamtchatka = new Country("Kamtchatka");Country egipto = new Country("Egipto");
       Country turquia = new Country("Turquia");Country nuevaYork = new Country("Nueva York");Country terranova = new Country("Terranova");Country iran = new Country("Iran");Country madagascar = new Country("Madagascar");Country argentina = new Country("Argentina");
       Country israel = new Country("Israel");Country rusia = new Country("Rusia");Country borneo = new Country("Borneo");Country california = new Country("California");Country taymir = new Country("Taymir");Country aral = new Country("Aral");Country siberia = new Country("Siberia");Country canada = new Country("Canada");
       Country sahara = new Country("Sahara");Country yukon = new Country("Yukon");Country uruguay = new Country("Uruguay");Country groenlandia  = new Country("Groenlandia");Country japon = new Country("Japon");Country sumatra = new Country("Sumatra");
       Country alaska = new Country("Alaska");Country brasil = new Country("Brasil");Country gobi = new Country("Gobi");Country italia = new Country("Italia");Country espania = new Country("Espania");Country colombia = new Country("Colombia");
       Country suecia = new Country("Suecia");Country sudafrica = new Country("Sudafrica");Country arabia = new Country("Arabia");Country india = new Country("India");Country java = new Country("Java");Country mexico = new Country("Mexico");
       Country peru = new Country("Peru");Country alemania = new Country("Alemania");Country china = new Country("China");Country labrador = new Country("Labrador");Country islandia = new Country("Islandia");Country malasia = new Country("Malasia");

       ArrayList<Country> allCountries = new ArrayList<>()
       {{ add(francia); add(granBretania); add(tartaria);add(mongolia);add(zaire);add(polonia);add(oregon);add(etiopia);add(chile);add(australia);add(kamtchatka);
           add(egipto);add(turquia);add(nuevaYork);add(iran);add(madagascar);add(argentina);add(israel);add(rusia);add(borneo);add(california);add(taymir);add(aral);add(siberia);add(canada);
           add(sahara);add(yukon);add(uruguay);add(groenlandia);add(japon);add(sumatra);add(alaska);add(brasil);add(gobi);add(italia);
           add(espania);add(colombia);add(suecia);add(sudafrica);add(arabia);add(india);add(java);add(mexico);add(peru);add(alemania);add(china);add(labrador);add(islandia);add(malasia);add(terranova); }};

       for(int i = 0 ; i < 29; i++){
           game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
       }
       for(int i = 29 ; i < (allCountries.size()); i++){
           game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
       }

       game.playerSetArmies(firstPlayerNumber,5,francia);
       game.playerSetArmies(secondPlayerNumber,1,italia);

       Player winner = attackRound.attack(game.getPlayer(firstPlayerNumber),francia,italia,3,0);
       assertEquals(game.getPlayer(firstPlayerNumber), winner);
    }
}
