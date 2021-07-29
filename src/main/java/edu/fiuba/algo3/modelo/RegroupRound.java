package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidRegroup;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import edu.fiuba.algo3.modelo.exceptions.NonExistentPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RegroupRound {
    private ArrayList<Player> players;
    private Map map;
    private ArmyMover armyMover;

    public RegroupRound(ArrayList<Player> gamePlayers, Map gameMap) throws IOException {
        players = gamePlayers;
        map = gameMap;
        armyMover = new ArmyMover();
    }

    void regroup(Player player, Country firstCountry, Country secondCountry, Integer armyToRegroup) throws EmptyCountryParameterException, NonExistentCountry, InvalidRegroup {
        Country mapFirstCountry = map.searchKeyCountryInMap(firstCountry);
        Country mapSecondCountry = map.searchKeyCountryInMap(secondCountry);

        if(!checkRegroup(mapFirstCountry,mapSecondCountry,player,armyToRegroup)){
            throw new InvalidRegroup();
        }
        ArmyMover.moveArmy(mapFirstCountry,mapSecondCountry,armyToRegroup);
    }

    private boolean checkRegroup(Country firstCountry, Country secondCountry, Player player,Integer armyToRegroup) throws NonExistentCountry, EmptyCountryParameterException {
        boolean ownsFirstCountry = player.dominatedCountry(firstCountry);
        boolean ownsSecondCountry = player.dominatedCountry(secondCountry);
        boolean areBordering = map.validateBorderingCountry(firstCountry, secondCountry);
        boolean correctAmount = (firstCountry.getArmyAmount()) > armyToRegroup;

        return (ownsFirstCountry && ownsSecondCountry && areBordering && correctAmount);
    }

    public Integer amountOfArmiesToAddInRegroupRound(Player player){
        return player.amountOfArmiesToAddInRegroupRound();
    }

    public void addArmiesInRound(Player player, HashMap<Country, Integer> armiesToAdd) throws InvalidRegroup, NonExistentCountry, EmptyCountryParameterException {
        Integer total = 0;
        for(Integer num : armiesToAdd.values()){
            total = total + num;
        }
        if(total > amountOfArmiesToAddInRegroupRound(player)){
            throw new InvalidRegroup();
        }
        for(Country country : armiesToAdd.keySet()){
            Country mapCountry = map.searchKeyCountryInMap(country);
            player.addArmyInCountry(armiesToAdd.get(country),mapCountry);
        }

    }
}
