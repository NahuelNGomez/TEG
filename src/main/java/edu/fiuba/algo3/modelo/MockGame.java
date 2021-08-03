package edu.fiuba.algo3.modelo;


import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.*;

public class MockGame {

    private Map map;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player winner;
    private SettingGame set;

    private PlacementRound placementRound;
    private MockAttackRound attackRound;
    private RegroupRound regroupRound;

    private ArrayList<CountryCard> countryCards = new ArrayList<CountryCard>();

    private ArrayList<Color> colorsArray;
    private String[] colors = {"07bb", "cc3311", "ee7733", "009988", "ee3377", "000000"};


    public MockGame(int numberOfPlayers) throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException {
        if (numberOfPlayers <= 1 || numberOfPlayers > 6) {
            throw new InvalidNumberOfPlayers();
        }
        colorsArray = new ArrayList<>();
        for (int i = 0; i < colors.length; i++) {
            colorsArray.add(new Color(colors[i]));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player(colorsArray.get(i));
            players.add(player);
        }
        map = Map.get();
        map.clean();
        set = new SettingGame();
        if (countryCards.size() < 50) setCountryCards();

        placementRound = new PlacementRound(players, map);
        attackRound = new MockAttackRound(players, map, countryCards);
        regroupRound = new RegroupRound(players, map);
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    public Player getPlayer(Integer playerNumber) throws NonExistentPlayer {
        if( (playerNumber > players.size()) || (playerNumber < 1) ) {
            throw new NonExistentPlayer();
        }
        return players.get(playerNumber - 1);
    }


    private boolean checkIfAttackerDominatedAContinent(Player player) {
        return map.checkIfAttackerDominatedAContinent(player);
    }

    public void playerSetArmies(Integer numberPlayer, int amount, Country country) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);
        Player player = getPlayer(numberPlayer);
        player.addArmyInCountry(amount, mapCountry);
    }

    private void setCountryCards(){
        countryCards = set.getCountryCards();
    }


    //ROUNDS

    public void addArmiesInRegroupRound(Integer playerNumber, HashMap<Country, Integer> armiesToAdd) throws NonExistentPlayer, InvalidRegroup, NonExistentCountry, EmptyCountryParameterException {
        regroupRound.addArmiesInRound(getPlayer(playerNumber),armiesToAdd);
    }

    public void regroup(Integer playerNumber, Country firstCountry, Country secondCountry, Integer armyToRegroup) throws EmptyCountryParameterException, NonExistentCountry, NonExistentPlayer, InvalidRegroup {
        checkValidCountryParameter(firstCountry);
        checkValidCountryParameter(secondCountry);

        Player player = getPlayer(playerNumber);
        regroupRound.regroup(player,firstCountry,secondCountry,armyToRegroup);
    }

    public void attack(Integer numberAttackerPlayer, Country attackerCountry, Country defenderCountry, Integer amountOfAttackerDice) throws NonExistentPlayer, EmptyCountryParameterException, NonExistentCountry, InvalidAttack {
        checkValidCountryParameter(attackerCountry);
        checkValidCountryParameter(defenderCountry);
        winner = attackRound.attack(getPlayer(numberAttackerPlayer),attackerCountry,defenderCountry,amountOfAttackerDice,numberAttackerPlayer-1);
    }

    public void playerDominatedContinent(Integer playerNumber,HashMap<Country,Integer> armiesToAdd) throws NonExistentPlayer, EmptyCountryParameterException, InvalidPlacement, NonExistentCountry {
        Player player = getPlayer(playerNumber);
        attackRound.playerDominatedAContinent(player,armiesToAdd);
    }

    public void placingFiveArmiesInPlacementRound(Integer numberPlayer, HashMap<Country,Integer> armiesToAdd) throws InvalidPlacement, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer {
        placementRound.placingFiveArmiesInPlacementRound(getPlayer(numberPlayer),armiesToAdd);
    }

    public void placingThreeArmiesInPlacementRound(Integer numberPlayer, HashMap<Country, Integer> armiesToAdd) throws InvalidPlacement, NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer {
        placementRound.placingThreeArmiesInPlacementRound(getPlayer(numberPlayer),armiesToAdd);
    }

    public ArrayList<Country> getCountries(){
        return map.getCountries();
    }

    ///////////////////////////////////////////////////// PARA PRUEBAS  /////////////////////////////////////////////

    public Player winner(){
        return winner;
    }

    public boolean playerDominatedCountry(Integer playerNumber, Country country) throws NonExistentPlayer, EmptyCountryParameterException, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);
        return getPlayer(playerNumber).dominatedCountry(mapCountry);
    }

    public boolean correctAmountOfArmy(Integer firstPlayerNumber, Integer expectedAmount) throws NonExistentPlayer {
        Player player = getPlayer(firstPlayerNumber);
        return (player.correctAmountOfArmy(expectedAmount));
    }

    public boolean correctAmountOfCountryCards(Integer firstPlayerNumber, Integer expectedAmount) throws NonExistentPlayer {
        return getPlayer(firstPlayerNumber).correctAmountOfCountryCards(expectedAmount);
    }

    public boolean correctAmountOfArmyInCountry(Country country, Integer expectedAmount) throws NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);
        return (mapCountry.correctAmountOfArmyInCountry(expectedAmount));
    }

    public void addCountryToPlayer(Country country , Integer playerNumber) throws NonExistentPlayer, EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country);
        Country mapCountry = map.searchKeyCountryInMap(country);
        Player player =  getPlayer(playerNumber);
        player.addCountry(mapCountry);

        if (checkIfAttackerDominatedAContinent(player)) {
            Continent dominatedByPlayer = map.continentDominatedByPlayer(player);
            player.addArmyInCountry(dominatedByPlayer.getAmountOfArmyWhenDominated(), mapCountry);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //AttackRoundTests
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<CountryCard> getCountryCards(){
        return countryCards;
    }



}

