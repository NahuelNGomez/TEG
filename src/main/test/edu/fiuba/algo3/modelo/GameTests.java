package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    Integer firstPlayerNumber = 1;
    Integer secondPlayerNumber = 2;
    Integer thirdPlayerNumber = 3;

    /*@Test
    public void assignCardNoCountriesLeft() throws NonExistentPlayer, InvalidNumberOfPlayers, EmptyCountryParameterException, IOException, NonExistentCountry {
        Integer numberOfPlayers = 2;

        Integer expectedAmountOfCountries = 25;

        Game game = new Game(numberOfPlayers);
        game.dealCountryCards();

        assertEquals(true, game.correctAmountOfCountries(firstPlayerNumber, expectedAmountOfCountries));
    }

    @Test
    public void assignCountryCardsWithLeftCountries() throws NonExistentPlayer, InvalidNumberOfPlayers, EmptyCountryParameterException, IOException, NonExistentCountry {
        Integer numberOfPlayers = 3;

        Integer expectedAmountOfCountriesFirstPlayer = 17;
        Integer expectedAmountOfCountriesSecondPlayer = 17;
        Integer expectedAmountOfCountriesThirdPlayer = 16;

        Game game = new Game(numberOfPlayers);
        game.dealCountryCards();

        assertEquals(true, game.correctAmountOfCountries(firstPlayerNumber, expectedAmountOfCountriesFirstPlayer));
        assertEquals(true, game.correctAmountOfCountries(secondPlayerNumber, expectedAmountOfCountriesSecondPlayer));
        assertEquals(true, game.correctAmountOfCountries(thirdPlayerNumber, expectedAmountOfCountriesThirdPlayer));
    }*/

    //pruebas de excepciones
    @Test
    public void gameWithoutEnoughPlayersRaisesException() {
        Integer numberOfPlayers = 1;
        assertThrows(InvalidNumberOfPlayers.class, () -> new Game(numberOfPlayers));
    }

    @Test
    public void gameWithTooManyPlayersRaisesException() {
        Integer numberOfPlayers = 8;
        assertThrows(InvalidNumberOfPlayers.class, () -> new Game(numberOfPlayers));
    }

    @Test
    public void nonExistentPlayerRaisesException() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;

        MockGame game = new MockGame(numberOfPlayers);
        Country francia = new Country("Francia");

        assertThrows(NonExistentPlayer.class, () -> game.addCountryToPlayer(francia, thirdPlayerNumber));
    }

    @Test
    public void nonExistentCountryRaisesException() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;

        MockGame game = new MockGame(numberOfPlayers);
        Country croacia = new Country("Croacia");

        assertThrows(NonExistentCountry.class, () -> game.addCountryToPlayer(croacia, firstPlayerNumber));
    }
    //terminan pruebas de excepciones


    //pruebas de rondas de ubicacion de 8 fichas
    @Test
    public void oneRoundGameCountryFirstPlacement() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer, InvalidPlacement {
        Integer numberOfPlayers = 2;
        Integer expectedAmount = 30;
        MockGame game = new MockGame(numberOfPlayers);

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


        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        HashMap<Country,Integer> armiesToAddFirst = new HashMap<>();
        armiesToAddFirst.put(francia,2);
        armiesToAddFirst.put(tartaria,1);
        armiesToAddFirst.put(zaire,2);

        game.placingFiveArmiesInPlacementRound(firstPlayerNumber,armiesToAddFirst);
        assertTrue(game.correctAmountOfArmy(firstPlayerNumber, expectedAmount));


        HashMap<Country,Integer> armiesToAddSecond = new HashMap<>();
        armiesToAddSecond.put(islandia,2);
        armiesToAddSecond.put(brasil,1);
        armiesToAddSecond.put(arabia,2);

        game.placingFiveArmiesInPlacementRound(secondPlayerNumber,armiesToAddSecond);
        assertTrue(game.correctAmountOfArmy(secondPlayerNumber, expectedAmount));
    }

    @Test
    public void oneRoundGameCountrySecondPlacement() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer, InvalidPlacement {
        Integer numberOfPlayers = 2;
        Integer expectedAmount = 28;
        MockGame game = new MockGame(numberOfPlayers);

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


        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        HashMap<Country,Integer> armiesToAddFirst = new HashMap<>();
        armiesToAddFirst.put(francia,1);
        armiesToAddFirst.put(tartaria,1);
        armiesToAddFirst.put(zaire,1);

        game.placingThreeArmiesInPlacementRound(firstPlayerNumber,armiesToAddFirst);
        assertTrue(game.correctAmountOfArmy(firstPlayerNumber, expectedAmount));


        HashMap<Country,Integer> armiesToAddSecond = new HashMap<>();
        armiesToAddSecond.put(islandia,1);
        armiesToAddSecond.put(brasil,1);
        armiesToAddSecond.put(arabia,1);

        game.placingThreeArmiesInPlacementRound(secondPlayerNumber,armiesToAddSecond);
        assertTrue(game.correctAmountOfArmy(secondPlayerNumber, expectedAmount));
    }

    @Test
    public void firstPlacementRoundRaisesExceptionWithMoraThanFiveArmiesToAdd() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        HashMap<Country,Integer> armiesToAddFirst = new HashMap<>();
        armiesToAddFirst.put(francia,2);
        armiesToAddFirst.put(tartaria,2);
        armiesToAddFirst.put(zaire,2);

        assertThrows(InvalidPlacement.class, () -> game.placingFiveArmiesInPlacementRound(firstPlayerNumber,armiesToAddFirst));
    }

    @Test
    public void secondPlacementRoundRaisesExceptionWithMoraThanThreeArmiesToAdd() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        HashMap<Country,Integer> armiesToAddFirst = new HashMap<>();
        armiesToAddFirst.put(francia,2);
        armiesToAddFirst.put(tartaria,2);
        armiesToAddFirst.put(zaire,2);

        assertThrows(InvalidPlacement.class, () -> game.placingThreeArmiesInPlacementRound(firstPlayerNumber,armiesToAddFirst));
    }
    // terminan las pruebas de las rondas de ubicar fichas

    //pruebas de attack
    @Test
    public void attackRoundWithTwoPlayersPlayerOneDominatesACountryOfPlayerTwo() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer, InvalidAttack {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        game.playerSetArmies(firstPlayerNumber,5,francia);
        game.playerSetArmies(secondPlayerNumber,1,alemania);

        game.attack(firstPlayerNumber,francia,alemania,3);

        assertEquals(true ,game.playerDominatedCountry(firstPlayerNumber, alemania));
        assertEquals(false ,game.playerDominatedCountry(secondPlayerNumber, alemania));
    }

    @Test
    public void attackRoundWithTwoPlayersPlayerOneDominatesTwoCountriesOfPlayerTwo() throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer, InvalidAttack {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        game.playerSetArmies(firstPlayerNumber,5,francia);
        game.playerSetArmies(secondPlayerNumber,1,alemania);

        game.playerSetArmies(firstPlayerNumber,5,etiopia);
        game.playerSetArmies(secondPlayerNumber,1,sudafrica);

        game.attack(firstPlayerNumber,francia,alemania,3);
        game.attack(firstPlayerNumber,etiopia,sudafrica,3);

        assertTrue(game.playerDominatedCountry(firstPlayerNumber, alemania));
        assertFalse(game.playerDominatedCountry(secondPlayerNumber, alemania));

        assertTrue(game.playerDominatedCountry(firstPlayerNumber, sudafrica));
        assertFalse(game.playerDominatedCountry(secondPlayerNumber, sudafrica));
    }

    @Test
    public void playerTriesToAttackWithoutEnoughArmiesInAttackerCountry() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }
        game.playerSetArmies(secondPlayerNumber,1,alemania);
        assertThrows(InvalidAttack.class, () -> game.attack(firstPlayerNumber,francia,alemania,1));
    }

    @Test
    public void playerTriesToAttackANonBorderingCoutry() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }
        game.playerSetArmies(firstPlayerNumber,5,francia);
        game.playerSetArmies(secondPlayerNumber,5,terranova);

        assertThrows(InvalidAttack.class, () -> game.attack(firstPlayerNumber,francia,terranova,1));
    }

    @Test
    public void playerTriesToAttackWithAWrongAmountOfDices() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }
        game.playerSetArmies(firstPlayerNumber,2,francia);
        game.playerSetArmies(secondPlayerNumber,5,alemania);

        assertThrows(InvalidAttack.class, () -> game.attack(firstPlayerNumber,francia,alemania,6));
    }
    //terminan las pruebas de attack

    //pruebas de regroup
    @Test
    public void playerRegroupOneArmy() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, InvalidRegroup {
        Integer numberOfPlayers = 2;
        Integer armyToRegroup = 1;
        Integer expectedAmountTartaria= 2;
        Integer expectedAmountSiberia = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        game.playerSetArmies(firstPlayerNumber,2,tartaria);

        game.regroup(firstPlayerNumber, tartaria, siberia, armyToRegroup);

        assertTrue(game.playerDominatedCountry(firstPlayerNumber, tartaria));
        assertTrue(game.playerDominatedCountry(firstPlayerNumber, siberia));

        assertTrue(game.correctAmountOfArmyInCountry(tartaria, expectedAmountTartaria));
        assertTrue(game.correctAmountOfArmyInCountry(siberia, expectedAmountSiberia));
    }

    @Test
    public void playerTriesToRegroupNonBorderingCountries() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException{
        Integer numberOfPlayers = 2;
        Integer armyToRegroup = 1;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        game.playerSetArmies(firstPlayerNumber,2,tartaria);
        assertThrows(InvalidRegroup.class, () -> game.regroup(firstPlayerNumber, tartaria, turquia, armyToRegroup));
    }

    @Test
    public void playerTriesToRegroupMoreArmiesThanTheAmountOfTheCountry() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;
        Integer armyToRegroup = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }
        assertThrows(InvalidRegroup.class, () -> game.regroup(firstPlayerNumber, tartaria, turquia, armyToRegroup));
    }

    @Test
    public void playerTriesToRegroupWithACountryNotDominatedByThem() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Integer numberOfPlayers = 2;

        Integer armyToRegroup = 2;

        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }
        assertThrows(InvalidRegroup.class, () -> game.regroup(firstPlayerNumber, tartaria, italia, armyToRegroup));
    }

    @Test
    public void playerDominatesAContinentAndAddsArmyToIt() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, InvalidAttack, InvalidPlacement {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }
        Integer numberOfArmy = 34;
        game.playerSetArmies(firstPlayerNumber,7,australia);
        game.playerSetArmies(secondPlayerNumber,1,sumatra);
        game.playerSetArmies(secondPlayerNumber,1,java);


        game.attack(firstPlayerNumber,australia,sumatra,3);
        game.attack(firstPlayerNumber,australia,java,3);

        HashMap<Country,Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(francia,2);

        game.playerDominatedContinent(firstPlayerNumber,armiesToAdd);

        assertTrue(game.correctAmountOfArmy(firstPlayerNumber,numberOfArmy));
    }
    //terminan test de regroup

    @Test
    public void onlyFirstPlayerAttacksAndWinsGame() throws InvalidNumberOfPlayers, IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, InvalidAttack {
        Integer numberOfPlayers = 2;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        game.playerSetArmies(firstPlayerNumber,7,australia);
        game.playerSetArmies(secondPlayerNumber,1,sumatra);
        game.playerSetArmies(secondPlayerNumber,1,java);

        game.playerSetArmies(firstPlayerNumber,7,nuevaYork);
        game.playerSetArmies(secondPlayerNumber,1,groenlandia);
        game.playerSetArmies(secondPlayerNumber,1,terranova);

        game.playerSetArmies(firstPlayerNumber,7,mongolia);
        game.playerSetArmies(secondPlayerNumber,1,china);

        game.attack(firstPlayerNumber,australia,sumatra,3);
        game.attack(firstPlayerNumber,australia,java,3);

        game.attack(firstPlayerNumber,nuevaYork,groenlandia,3);
        game.attack(firstPlayerNumber,nuevaYork,terranova,3);

        game.attack(firstPlayerNumber,mongolia,china,3);

        assertEquals(game.getPlayer(firstPlayerNumber), game.winner());
    }

    @Test
    public void playerAddsArmiesInRegroupRound() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, InvalidNumberOfPlayers, IOException, InvalidRegroup {
        Integer numberOfPlayers = 2;
        Integer expectedAmount = 36;
        MockGame game = new MockGame(numberOfPlayers);

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

        for(int i = 0 ; i < (allCountries.size()/2); i++){
            game.addCountryToPlayer(allCountries.get(i), firstPlayerNumber);
        }
        for(int i = (allCountries.size()/2) ; i < (allCountries.size()); i++){
            game.addCountryToPlayer(allCountries.get(i), secondPlayerNumber);
        }

        HashMap<Country,Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(francia,9);
        armiesToAdd.put(tartaria,1);
        armiesToAdd.put(zaire,1);

        game.addArmiesInRegroupRound(firstPlayerNumber,armiesToAdd);
        assertTrue(game.correctAmountOfArmy(firstPlayerNumber, expectedAmount));
    }

}