package edu.fiuba.algo3.modelo;

import java.io.IOException;
import java.util.ArrayList;

public class PlacementRound extends Round{

    public PlacementRound(ArrayList<Player> gamePlayers, Map map) throws IOException {
        super(gamePlayers, map);
    }

    @Override
    public void startRound(Integer maxPlacement) {
        for( Player player : players){
            player.firstPlacementRound(maxPlacement);
        }
    }
}
