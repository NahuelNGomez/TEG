package edu.fiuba.algo3.modelo;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Round {
    protected ArrayList<Player> players;
    protected Map map = new Map();

    public Round(ArrayList<Player> gamePlayers) throws IOException {
        players = gamePlayers;
    }

    public abstract void firstPlacementRound(Integer maxPlacement);
}
