package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;

import java.io.IOException;
import java.util.ArrayList;

public class PlacementRound extends Round{

    public PlacementRound(ArrayList<Player> gamePlayers, Map map) throws IOException {
        super(gamePlayers, map);
    }

    @Override
    public void firstRounds(Integer maxPlacement) {
        for( Player player : players){
            player.firstPlacementRound(maxPlacement);
        }
    }

    @Override
    public Player startRound() throws NonExistentCountry, EmptyCountryParameterException {
        return null;
    }
}
