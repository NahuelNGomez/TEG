package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Map map = new Map();
    private static ArrayList<ObjectiveCard> objectiveCardsCards = new ArrayList<ObjectiveCard>();
    private static ArrayList<CountryCard> countryCards = new ArrayList<CountryCard>();
    private static String[] colors = {"07bb", "cc3311", "ee7733", "009988", "ee3377", "000000"};
    private static String[] countriesNames = {"Canada", "Alaska", "Oregon", "Terranova", "California", "Nueva York", "Islandia", "Mexico", "China", "Francia"};

    public Game(int numberOfPlayers){
        //validar que no juegue un soo jugador
        for(int i = 0; i < numberOfPlayers; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }
    }
    public Player getPlayer(int i){
        return players.get(i);
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

            if(player.cantidadPaises() < numberOfCountriesPerPlayer) {
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

    private Player searchCountryOwner(Country country){
        Player owner = null;
        for( Player player : players ){
            if(player.dominatedCountry(country) != null){
                owner = player.dominatedCountry(country);
            }
        }
        return owner;
    }

    public boolean attack(Country pais1, Country pais2){
        //this.validarLimitrofes(pais1,pais2);
        Player owner1 = this.searchCountryOwner(pais1);
        Player owner2 = this.searchCountryOwner(pais2);

        return ((owner1 != null) && (owner2 != null) && (owner1 != owner1));

    }

}
