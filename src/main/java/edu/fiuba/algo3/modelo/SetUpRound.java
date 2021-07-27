package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import edu.fiuba.algo3.modelo.exceptions.NonExistentPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SetUpRound extends Round{

    public SetUpRound(ArrayList<Player> gamePlayers, Map map) throws IOException {
        super(gamePlayers, map);
    }

    @Override
    public void firstRounds(Integer maxPlacement) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {

    }

    @Override
    public Player startRound() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        dealCountryCards();

        return null;
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
}
