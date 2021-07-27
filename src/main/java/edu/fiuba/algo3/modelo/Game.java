package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Game {
    private Map map;
    //private Battlefield battlefield = new Battlefield();
    private MockBattlefield battlefield = new MockBattlefield(); //Battlefield Mock for the Dice

    private ArrayList<Player> players = new ArrayList<Player>();
    private static ArrayList<ObjectiveCard> objectiveCardsCards = new ArrayList<ObjectiveCard>();
    private static ArrayList<CountryCard> countryCards = new ArrayList<CountryCard>();

    private ArrayList<Round> rounds;
    private Round placementRound = new PlacementRound(players,map);
    private Player winner = null;
    private SettingGame set = new SettingGame();

    private ArrayList<Color> colorsArray;
    private static String[] colors = {"07bb", "cc3311", "ee7733", "009988", "ee3377", "000000"};


    public Game(int numberOfPlayers) throws InvalidNumberOfPlayers, IOException {
        if(numberOfPlayers <= 1 || numberOfPlayers > 6){
            throw new InvalidNumberOfPlayers();
        }
        colorsArray = new ArrayList<>();
        for(int i = 0 ; i < colors.length; i++){
            colorsArray.add(new Color(colors[i]));
        }
        for(int i = 0; i < numberOfPlayers; i++){
            Player player = new Player(colorsArray.get(i));
            players.add(player);
        }
        map = Map.get();
        map.clean();

        rounds = new ArrayList<>();
        rounds.add(new AttackRound(players,map));
        rounds.add(new RegroupRound(players,map));

        if(countryCards.size() < 50) getCountryCards();
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    private void checkSearchedPlayer(Integer playerNumber) throws NonExistentPlayer {
        if( (playerNumber > players.size()) || (playerNumber < 1) ) {
            throw new NonExistentPlayer();
        }
    }

    public Player getPlayer(Integer playerNumber) throws NonExistentPlayer {
        checkSearchedPlayer(playerNumber);
        return players.get(playerNumber - 1);
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

    private boolean checkIfAttackerDominatedAContinent(Player player) throws EmptyCountryParameterException {
        return map.checkIfAttackerDominatedAContinent(player);
    }

    public Continent continentDominatedByPlayer(Player player) throws EmptyCountryParameterException {
        return map.continentDominatedByPlayer(player);
    }


    private void getCountryCards(){
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


    private Player searchCountryOwner(Country country) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);

        checkValidCountryParameter(mapCountry);
        Player owner = null;
        for( Player player : players ){
            if(player.isSearchedCountry(mapCountry)){
                owner = player;
            }
        }
        if(owner == null) throw new NonExistentPlayer();
        return owner;
    }


    public void playersSetArmies(int amount, Country country) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);
        Player player = searchCountryOwner(mapCountry);
        player.addArmyInCountry(amount, mapCountry);
    }

    public void giveACountryCard(Player attacker){
        Random random = new Random();
        CountryCard countryCard = countryCards.get(random.nextInt(countryCards.size()));
        Player.addCountryCard(countryCard);
       countryCards.remove(countryCard);
    }


    private void invade(Player attacker, Country countryDefender, Country countryAttacker) throws EmptyCountryParameterException, NonExistentCountry { //JUGADOR VACIO
        Country attackCountry = map.searchKeyCountryInMap(countryAttacker);
        Country defendCountry = map.searchKeyCountryInMap(countryDefender);

        attacker.removeArmy(1, attackCountry);
        attacker.addCountry(defendCountry);
        defendCountry.addArmy(1);
        giveACountryCard(attacker);
    }


    public void attack(Country attackingCountry, int amountDice, Country defendingCountry, Integer winner) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry, InvalidAttack {
        //boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        Player attacker = this.searchCountryOwner(attackCountry);
        Player defender = this.searchCountryOwner(defendCountry);

        if(/*!isBordering || */!attacker.canInvade(attackCountry, amountDice)) throw new InvalidAttack();

        Integer[] result = battlefield.battle(amountDice, defendCountry, winner);

        if(attacker.removeArmy(result[1], attackCountry)) {
            this.invade(defender, attackCountry, defendCountry);
        }
        if(defender.removeArmy(result[0], defendCountry)){
             this.invade(attacker, defendCountry, attackCountry);
        };
    }

    private boolean validateBorderingCountry(Country attackingCountry, Country defendingCountry) throws EmptyCountryParameterException, NonExistentCountry {
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        return map.validateBorderingCountry(attackCountry, defendCountry);
    }

    //ROUNDS
    public Player winner(){
        return winner;
    }

    public boolean isWinner(Integer playerNumber) throws NonExistentPlayer {
        return (winner == getPlayer(playerNumber));
    }

    public Player play() throws NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer, IOException {
        int firstPlacement = 5;
        int secondPlacement = 3;

        dealCountryCards();
        placementRound(firstPlacement);
        placementRound(secondPlacement);

        int i = 0;
        //Player loser = null;

        while (winner == null && playersStillHaveCountries()) {
            if (i >= (rounds.size())) {
                i = 0;
            }
            winner = rounds.get(i).startRound();

            /*for(Player player: players){
                if(player.correctAmountOfCountries(0)){
                    loser = player;
                }
            }*/
            i++;
        }
        //if(loser != null) players.remove(loser);
        return winner;
    }

    public void regroup(Integer playerNumber, Country country1, Country country2, Integer armyToRegroup) throws EmptyCountryParameterException, NonExistentCountry, NonExistentPlayer {
        checkValidCountryParameter(country1);
        checkValidCountryParameter(country2);

        Country mapCountry1 = map.searchKeyCountryInMap(country1);
        Country mapCountry2 = map.searchKeyCountryInMap(country2);

        if(checkRegroup(mapCountry1, mapCountry2, playerNumber)){
            mapCountry1.removeArmy(armyToRegroup);
            mapCountry2.addArmy(armyToRegroup);
        }
    }

    private boolean checkRegroup(Country country1, Country country2, Integer firstPlayerNumber) throws NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer {
        Player player = getPlayer(firstPlayerNumber);
        return (player.dominatedCountry(country1) && player.dominatedCountry(country2) && map.validateBorderingCountry(country1,country2));
    }


    public void attackRound() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        rounds.get(0).startRound();
    }

    public void placementRound(int maxPlacement) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        placementRound.firstRounds(maxPlacement);
    }

    public boolean playersStillHaveCountries(){
        for( Player player : players){
            if(player.amountOfDominatedCountries() == 0) return false;
        }
        return true;
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

    public void addContinentToPlayer(Integer firstPlayerNumber, Continent continent) throws NonExistentPlayer, NonExistentContinent, EmptyCountryParameterException, EmptyContinentParameterException {
        checkValidContintParameter(continent);
        getPlayer(firstPlayerNumber).setDominatedCountries(map.getContinent(continent));
    }

    private void checkValidContintParameter(Continent continent) throws EmptyContinentParameterException {
        if(continent == null){
            throw new EmptyContinentParameterException();
        }
    }

    public boolean correctAmountOfCountryCards(Integer firstPlayerNumber, Integer expectedAmount) throws NonExistentPlayer {
       return getPlayer(firstPlayerNumber).correctAmountOfCountryCards(expectedAmount);
    }

    public boolean correctRemainingNumberOfCountryCards(int expected) {
        return (expected == countryCards.size());
    }

    public boolean correctAmountOfArmyInCountry(Integer firstPlayerNumber, Country polonia, Integer expectedAmountPoland) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        Player player = getPlayer(firstPlayerNumber);
        Country mapCountry = map.searchKeyCountryInMap(polonia);
       return( player.correctAmountOfArmyInCountry(mapCountry, expectedAmountPoland));

    }
    //AttackRoundTests
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Map getMap() {
        return map;
    }
}