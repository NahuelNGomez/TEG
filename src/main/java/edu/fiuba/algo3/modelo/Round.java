package edu.fiuba.algo3.modelo;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Round {
    protected ArrayList<Player> players;
    protected Map map;

    public Round(ArrayList<Player> gamePlayers, Map map) throws IOException {
        players = gamePlayers;
        this.map = map;
    }

    public abstract void placementRound(Integer maxPlacement);
}
