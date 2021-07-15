package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Map map = new Map();
    private Battlefield battlefield = new Battlefield();
    private static ArrayList<ObjectiveCard> objectiveCardsCards = new ArrayList<ObjectiveCard>();
    private static ArrayList<CountryCard> countryCards = new ArrayList<CountryCard>();
    private static String[] colors = {"07bb", "cc3311", "ee7733", "009988", "ee3377", "000000"};
    private static String[] countriesNames = {"Canada", "Alaska", "Oregon", "Terranova", "California", "New York", "Islandia", "Mexico", "China", "Francia"};

    public Game(int numberOfPlayers) throws InvalidNumberOfPlayers {
        if(numberOfPlayers <= 1 || numberOfPlayers > 6){
            throw new InvalidNumberOfPlayers();
        }
        for(int i = 0; i < numberOfPlayers; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    private void checkSearchedPlayer(Player player) throws NonExistentPlayer {
        if(player == null) {
            throw new NonExistentPlayer();
        }
    }

    private Player getPlayer(Integer playerNumber) throws NonExistentPlayer {
        Player searchedPlayer = players.get(playerNumber - 1);
        checkSearchedPlayer(searchedPlayer);
        return (searchedPlayer != null) ?  searchedPlayer : null;
    }

    public boolean playerDominatedCountry(Integer playerNumber, Country country) throws NonExistentPlayer, EmptyCountryParameterException {
        return getPlayer(playerNumber).dominatedCountry(country);
    }

    public void addCountryToPlayer(Country country , Integer playerNumber) throws NonExistentPlayer, EmptyCountryParameterException {
        getPlayer(playerNumber).addCountry(country);
    }


    public boolean correctAmountOfCountries(Integer playerNumber, Integer expectedAmount ) throws NonExistentPlayer {
        return getPlayer(playerNumber).correctAmountOfCountries(expectedAmount);
    }

    private void shuffleCards(){
        Random rand = new Random();
        for (int i = 0; i < countriesNames.length; i++) {
            int randomIndexToSwap = rand.nextInt(countriesNames.length);
            String temp = countriesNames[randomIndexToSwap];
            countriesNames[randomIndexToSwap] = countriesNames[i];
            countriesNames[i] = temp;
        }
    }

    public void dealCountryCards() throws EmptyCountryParameterException {
        this.shuffleCards();
        int leftCountries = (this.countriesNames.length) % (this.players.size());
        int numberOfCountriesPerPlayer = (this.countriesNames.length) / (this.players.size());
        Random rand = new Random();
        int i;
        int k = 0;

        for (i = 0; i < (this.countriesNames.length - leftCountries); i++) {
            Player player = players.get(rand.nextInt(players.size()));

            if(player.countryAmount() < numberOfCountriesPerPlayer) {
                player.addCountry(new Country(countriesNames[i]));
            } else {
                i--;
            }
        }
        for(int j = i ; j < this.countriesNames.length ; j++){
            players.get(k).addCountry(new Country(countriesNames[j]));
            k++;
        }
    }


    private Player searchCountryOwner(Country country) throws EmptyCountryParameterException, NonExistentPlayer {
        checkValidCountryParameter(country);
        Player owner = null;
        for( Player player : players ){
            if(player.isSearchedCountry(country)){
                owner = player;
            }
        }
        checkSearchedPlayer(owner);
        return owner;
    }


    public void playersSetArmies(int amount, Country country) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry {
        Player player = searchCountryOwner(country);
        player.addArmyinCountry(amount, country);
    }


    private void invade(Player attacker, Country countryDefender, Country countryAttacker) throws EmptyCountryParameterException, NonExistentCountry { //JUGADOR VACIO
        attacker.removeArmy(1, countryAttacker);
        attacker.addCountry(countryDefender);
    }


    public void attack(Country attackingCountry, int amountDice, Country defendingCountry) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry, InvalidNumberOfDices {
        boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);

        Player attacker = this.searchCountryOwner(attackingCountry);
        Player defender = this.searchCountryOwner(defendingCountry);

        if(isBordering && attacker.canInvade(attackingCountry, amountDice)) {
            Integer[] result = battlefield.battle(amountDice, defendingCountry);
            attacker.removeArmy(result[0], attackingCountry);

            if(defender.removeArmy(result[1], defendingCountry)){
                 this.invade(attacker, defendingCountry, attackingCountry);
            };
        }
    }


    private boolean validateBorderingCountry(Country attackingCountry, Country defendingCountry) throws EmptyCountryParameterException {
        return map.validateBorderingCountry(attackingCountry, defendingCountry);
    }


}
