package edu.fiuba.algo3.modelo;

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

    public Game(int numberOfPlayers){
        //validate the numberOfPlayer > 1
        for(int i = 0; i < numberOfPlayers; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }
    }
    public Player getPlayer(String color){
        for( Player aPlayer: players){

            if(aPlayer.getColor() == color){
                return  aPlayer;
            }
        }
        return null;
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

    public void dealCountryCards() {
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

    private Player searchCountryOwner(String countryName){
        Player owner = null;
        for( Player player : players ){
            Country country = player.getCountry(countryName);
            if(country != null){
                owner = player.dominatedCountry(country);
            }
        }
        return owner;
    }

    public void playersSetArmies(int amount, String countryName, String playerColor){
        Player player = getPlayer(playerColor);
        player.setArmy(amount, countryName);
    }


    public void attack(String attackingCountry, int amountDice, String defendingCountry){
        boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);
        if(isBordering) {
            Player attacker = this.searchCountryOwner(attackingCountry);
            Player defender = this.searchCountryOwner(defendingCountry);
            Country countryAttacker = attacker.getCountry(attackingCountry);
            Country countryDefender = defender.getCountry(defendingCountry);
            Integer[] result = battlefield.battle(countryAttacker, amountDice, countryDefender);
            attacker.removeArmy(result[0], countryAttacker);
            defender.removeArmy(result[1], countryDefender);
        }

    }

    private boolean validateBorderingCountry(String attackingCountry, String defendingCountry) {
        return map.validateBorderingCountry(attackingCountry, defendingCountry);
    }


}
