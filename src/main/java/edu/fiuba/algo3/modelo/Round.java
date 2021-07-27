package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Round {
    protected ArrayList<Player> players;
    protected Map map;
    protected ArrayList<CountryCard> countryCards = new ArrayList<CountryCard>();
    private SettingGame set = new SettingGame();

    public Round(ArrayList<Player> gamePlayers, Map map) throws IOException {
        players = gamePlayers;
        this.map = map;
        getCountryCards();
    }
    private void getCountryCards(){
        countryCards = set.getCountryCards();
    }
    public abstract void firstRounds(Integer maxPlacement) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException;

    public abstract Player startRound() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException;
}
