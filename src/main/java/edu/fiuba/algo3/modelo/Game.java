package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private Round rounds;


    private static String[] colors = {"07bb", "cc3311", "ee7733", "009988", "ee3377", "000000"};


    public Game(int numberOfPlayers) throws InvalidNumberOfPlayers, IOException {
        if(numberOfPlayers <= 1 || numberOfPlayers > 6){
            throw new InvalidNumberOfPlayers();
        }
        for(int i = 0; i < numberOfPlayers; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }
        map = Map.get();
        map.clean();
        rounds = new PlacementRound(players, map);
        if(countryCards.isEmpty()) getCountryCards();
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

    private Player getPlayer(Integer playerNumber) throws NonExistentPlayer {
        checkSearchedPlayer(playerNumber);
        Player searchedPlayer = players.get(playerNumber - 1);
        return (searchedPlayer != null) ?  searchedPlayer : null;
    }

    public void addCountryToPlayer(Country country , Integer playerNumber) throws NonExistentPlayer, EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country);
        Country mapCountry = map.searchKeyCountryInMap(country);
        getPlayer(playerNumber).addCountry(mapCountry);
    }


    private void getCountryCards(){
        String SEPARATOR = ",";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/java/edu/fiuba/algo3/archivos/Teg - Cartas.csv"));
            String line = reader.readLine();

            while (line != null) {
                String[] fields = line.split(SEPARATOR);
                Country mapCountry = map.searchKeyCountryInMap(new Country(fields[0]));
                countryCards.add(new CountryCard(mapCountry,fields[1]));
                line = reader.readLine();
            }
        }
        catch (IOException | NonExistentCountry e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        player.addArmyinCountry(amount, mapCountry);
    }


    private void invade(Player attacker, Country countryDefender, Country countryAttacker) throws EmptyCountryParameterException, NonExistentCountry { //JUGADOR VACIO
        Country attackCountry = map.searchKeyCountryInMap(countryAttacker);
        Country defendCountry = map.searchKeyCountryInMap(countryDefender);

        attacker.removeArmy(1, attackCountry);
        attacker.addCountry(defendCountry);
        defendCountry.addArmy(1);
    }


    public void attack(Country attackingCountry, int amountDice, Country defendingCountry) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry, UnsuccessfullAttack {
        //boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        Player attacker = this.searchCountryOwner(attackCountry);
        Player defender = this.searchCountryOwner(defendCountry);

        if(/*!isBordering || */!attacker.canInvade(attackCountry, amountDice)) throw new UnsuccessfullAttack();

        Integer[] result = battlefield.battle(amountDice, defendCountry,attackCountry);
        attacker.removeArmy(result[0], attackCountry);

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
    public void attackRound() throws IOException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        AttackRound attackRound = new AttackRound(players, map);
        attackRound.startRound(0);
    }

    public void placementRound(int maxPlacement) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        rounds.startRound(maxPlacement);
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

}