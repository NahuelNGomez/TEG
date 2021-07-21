package edu.fiuba.algo3.modelo;

import java.io.IOException;
import java.util.ArrayList;

public class PlacementRound extends Round{

    public PlacementRound(ArrayList<Player> gamePlayers, Map map) throws IOException {
        super(gamePlayers, map);

    }

    @Override
    public void placementRound(Integer maxPlacement) {
        Integer num = 1;
        for( Player player : players){
            player.firstPlacementRound(maxPlacement);
            num++;
        }
    }
}
