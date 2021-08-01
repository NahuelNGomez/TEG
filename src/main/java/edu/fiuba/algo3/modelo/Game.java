package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Game {
    private Map map;
    //private Battlefield battlefield = new Battlefield();
    private MockBattlefield battlefield = new MockBattlefield(); //Battlefield Mock for the Dice
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player winner;
    private SettingGame set;

    private PlacementRound placementRound;
    private AttackRound attackRound;
    private RegroupRound regroupRound;

    private ArrayList<CountryCard> countryCards = new ArrayList<CountryCard>();

    private ArrayList<Color> colorsArray;
    private String[] colors = {"07bb", "cc3311", "ee7733", "009988", "ee3377", "000000"};

    //private static ArrayList<ObjectiveCard> objectiveCardsCards = new ArrayList<ObjectiveCard>();

    public Game(int numberOfPlayers) throws InvalidNumberOfPlayers, IOException, NonExistentCountry, EmptyCountryParameterException {
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
        attackRound = new AttackRound(players, map, countryCards);
        regroupRound = new RegroupRound(players, map);
        dealCountryCards();
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


    private boolean checkIfAttackerDominatedAContinent(Player player) throws EmptyCountryParameterException {
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

    private void shuffleCards(){
        Collections.shuffle(countryCards);
    }

    public void dealCountryCards() throws EmptyCountryParameterException, NonExistentCountry {
        this.shuffleCards();

        int leftCountries = (countryCards.size()) % (this.players.size());
        int numberOfCountriesPerPlayer = (countryCards.size()) / (this.players.size());

        Random rand = new Random();
        int i;
        int k = 0;

        for (i = 0; i < (countryCards.size() - leftCountries); i++) {
            Player player = players.get(rand.nextInt(players.size()));

            if(player.amountOfDominatedCountries() < numberOfCountriesPerPlayer) {
                player.addCountry(map.searchKeyCountryInMap(countryCards.get(i).getCountryCard()));
            } else {
                i--;
            }
        }
        for(int j = i ; j < this.countryCards.size() ; j++){
            players.get(k).addCountry(map.searchKeyCountryInMap(countryCards.get(i).getCountryCard()));
            k++;
        }
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

    public ArrayList<Country> getOtherPlayersBorderingCountries(Integer playerNumber, Country country) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Player player = getPlayer(playerNumber);

        ArrayList<Country> playersCountries = player.getDominatedCountries();
        ArrayList<Country> borderingCountries = new ArrayList<>();

        /*for(Country pais : playersCountries){
            System.out.println(pais.getName());
        }*/

        for(CountryCard card : countryCards){
            System.out.println(card.getCountryCard().getName());
            System.out.println(!playersCountries.contains(card.getCountryCard()));
            System.out.println(map.validateBorderingCountry(country,card.getCountryCard()));

            if( (country != card.getCountryCard()) && (!playersCountries.contains(card.getCountryCard())) && (map.validateBorderingCountry(country,card.getCountryCard())) ){
                borderingCountries.add(card.getCountryCard());
            }
        }
        return borderingCountries;
    }

    ///////////////////////////////////////////////////// PARA PRUEBAS  /////////////////////////////////////////////
    public Player winner(){
        return winner;
    }

    public Continent continentDominatedByPlayer(Integer playerNumber) throws EmptyCountryParameterException, NonExistentPlayer {
        return map.continentDominatedByPlayer(getPlayer(playerNumber));
    }

    public boolean playerDominatedCountry(Integer playerNumber, Country country) throws NonExistentPlayer, EmptyCountryParameterException, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);
        return getPlayer(playerNumber).dominatedCountry(mapCountry);
    }

    public boolean correctAmountOfCountries(Integer playerNumber, Integer expectedAmount ) throws NonExistentPlayer {
        return getPlayer(playerNumber).correctAmountOfCountries(expectedAmount);
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
