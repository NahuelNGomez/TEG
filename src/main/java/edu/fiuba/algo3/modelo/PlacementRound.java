package edu.fiuba.algo3.modelo;


import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidPlacement;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PlacementRound {
    private ArrayList<Player> players;
    private Map map;
    protected Integer maxArmiesFirstRound = 5;
    protected Integer maxArmiesSecondRound = 3;

    public PlacementRound(ArrayList<Player> gamePlayers, Map gameMap) throws IOException {
       players = gamePlayers;
       map = gameMap;
    }

    private Integer validateAmountOfArmyToAdd(Collection<Integer> armyAmount){
        Integer total = 0;
        for ( Integer num : armyAmount){
            total = total + num;
        }
        return total;
    }

    public void placingFiveArmiesInPlacementRound(Player player, HashMap<Country,Integer> armiesToAdd) throws InvalidPlacement, NonExistentCountry, EmptyCountryParameterException {
        if(validateAmountOfArmyToAdd(armiesToAdd.values()) > maxArmiesFirstRound) throw new InvalidPlacement();

        for(Country country : armiesToAdd.keySet()){
            Country mapCountry = map.searchKeyCountryInMap(country);
            player.addArmyInCountry(armiesToAdd.get(country),mapCountry);
        }
    }

    public void placingThreeArmiesInPlacementRound(Player player, HashMap<Country,Integer> armiesToAdd) throws InvalidPlacement, NonExistentCountry, EmptyCountryParameterException {
        if(validateAmountOfArmyToAdd(armiesToAdd.values()) > maxArmiesSecondRound) throw new InvalidPlacement();

        for(Country country : armiesToAdd.keySet()){
            Country mapCountry = map.searchKeyCountryInMap(country);
            player.addArmyInCountry(armiesToAdd.get(country),mapCountry);
        }
    }
}
