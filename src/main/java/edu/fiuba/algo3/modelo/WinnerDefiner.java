package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public class WinnerDefiner {
    private ArrayList<Player> players = new ArrayList<Player>();

    public WinnerDefiner(ArrayList<Player> players){
        this.players = players;
    }

    public boolean theresAWinner() {
        for(Player player : players){
            if(player.objectiveCompleted()){
                return true;
            }
        }
        return false;
    }
    public Player winner() {
        for(Player player : players){
            if(player.objectiveCompleted()){
                return player;
            }
        }
        return null;
    }
}
