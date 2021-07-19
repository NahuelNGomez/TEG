package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Game {
    private Map map = new Map();
    //private Battlefield battlefield = new Battlefield();
    private MockBattlefield battlefield = new MockBattlefield(); //mock de battlefield para mockear el dado

    private ArrayList<Player> players = new ArrayList<Player>();
    private static ArrayList<ObjectiveCard> objectiveCardsCards = new ArrayList<ObjectiveCard>();
    private static ArrayList<CountryCard> countryCards = new ArrayList<CountryCard>();
    private Round rounds;/*, new AttackRound()*/;

    private static String[] colors = {"07bb", "cc3311", "ee7733", "009988", "ee3377", "000000"};


    public Game(int numberOfPlayers) throws InvalidNumberOfPlayers, IOException {
        if(numberOfPlayers <= 1 || numberOfPlayers > 6){
            throw new InvalidNumberOfPlayers();
        }
        for(int i = 0; i < numberOfPlayers; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }
        rounds = new PlacementRound(players);
        if(countryCards.isEmpty()) getCountryCards();
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

    public boolean playerDominatedCountry(Integer playerNumber, Country country) throws NonExistentPlayer, EmptyCountryParameterException, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);
        return getPlayer(playerNumber).dominatedCountry(mapCountry);
    }

    public void addCountryToPlayer(Country country , Integer playerNumber) throws NonExistentPlayer, EmptyCountryParameterException, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);
        getPlayer(playerNumber).addCountry(mapCountry);
    }


    public boolean correctAmountOfCountries(Integer playerNumber, Integer expectedAmount ) throws NonExistentPlayer {
        return getPlayer(playerNumber).correctAmountOfCountries(expectedAmount);
    }

    private void getCountryCards(){
        String SEPARADOR = ",";
        BufferedReader bufferLectura = null;
        try {
            bufferLectura = new BufferedReader(new FileReader("src/main/java/edu/fiuba/algo3/archivos/Teg - Cartas.csv"));

            String linea = bufferLectura.readLine();

            while (linea != null) {
                String[] campos = linea.split(SEPARADOR);
                Country mapCountry = map.searchKeyCountryInMap(new Country(campos[0]));
                /*if(checkIfCardIsAlreadyAdded(mapCountry)){
                    //System.out.println("YA EXISTE LA CARTA");*/
                    countryCards.add(new CountryCard(mapCountry,campos[1]));
                /*}*/
                linea = bufferLectura.readLine();
            }
        }
        catch (IOException | NonExistentCountry e) {
            e.printStackTrace();
        }
        finally {
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*private boolean checkIfCardIsAlreadyAdded(Country country) {
        return !countryCards.contains(country);
    }*/

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

            if(player.countryAmount() < numberOfCountriesPerPlayer) {
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
        checkSearchedPlayer(owner);
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
    }


    public void attack(Country attackingCountry, int amountDice, Country defendingCountry) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry, InvalidNumberOfDices {
        boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        Player attacker = this.searchCountryOwner(attackCountry);
        Player defender = this.searchCountryOwner(defendCountry);

        if(isBordering && attacker.canInvade(attackCountry, amountDice)) {
            Integer[] result = battlefield.battle(amountDice, defendCountry);
            attacker.removeArmy(result[0], attackCountry);

            if(defender.removeArmy(result[0], defendCountry)){
                 this.invade(attacker, defendCountry, attackCountry);
            };
        }
    }


    private boolean validateBorderingCountry(Country attackingCountry, Country defendingCountry) throws EmptyCountryParameterException, NonExistentCountry {
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        return map.validateBorderingCountry(attackCountry, defendCountry);
    }

    public void firstPlacementRound(){
        Integer  maxPlacement = 5;
        rounds.firstPlacementRound(maxPlacement);
    }



    public Integer cantidadPaisesDelJugador(Integer numeroJugador) throws NonExistentPlayer {
        return getPlayer(numeroJugador).cantidadPaises();
    }

    public ArrayList<Country> playerDominatedCountries(Integer playerNumber) throws NonExistentPlayer {
        return getPlayer(playerNumber).paisesDominados();
    }

}
