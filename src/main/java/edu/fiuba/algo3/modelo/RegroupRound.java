package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import edu.fiuba.algo3.modelo.exceptions.NonExistentPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class RegroupRound extends Round{

    public RegroupRound(ArrayList<Player> gamePlayers, Map map) throws IOException {
        super(gamePlayers, map);
    }

    @Override
    public void firstRounds(Integer maxPlacement){
        System.out.println("NO HACE NADA");
    }

    @Override
    public Player startRound() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        for( Player player : players){
            regroup(players.indexOf(player), player.getRandomOwnCountry(), player.getRandomOwnCountry(), 1);
        }
        return null;
    }

    public void regroup(Integer firstPlayerNumber, Country country1, Country country2, Integer armyToRegroup) throws EmptyCountryParameterException, NonExistentCountry, NonExistentPlayer {

        checkValidCountryParameter(country1);
        checkValidCountryParameter(country2);
        if(checkRegroup(country1, country2, firstPlayerNumber)){
            map.regroup(country1, country2, armyToRegroup);
        }
    }

    private boolean checkRegroup(Country country1, Country country2, Integer numberPlayer) throws NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer {

        Player player = players.get(numberPlayer);

        Country countryAux1 = country2;
        Country countryAux2 = country2;
        Integer maxMovements = 100;

        while(!map.validateBorderingCountry(countryAux1,country1) && maxMovements > 0){
            while(!map.validateBorderingCountry(countryAux2,countryAux1) && maxMovements > 0){
                countryAux2 = player.getRandomOwnCountry();
                maxMovements--;
            }
            countryAux1 = countryAux2;
        }
        return (maxMovements > 0);
    }

    /*private void regroup(Integer firstPlayerNumber, Country country1, Country country2, Integer armyToRegroup) throws EmptyCountryParameterException, NonExistentCountry {
        checkValidCountryParameter(country1);
        checkValidCountryParameter(country2);

        Country mapCountry1 = map.searchKeyCountryInMap(country1);
        Country mapCountry2 = map.searchKeyCountryInMap(country2);

        if(checkRegroup(mapCountry1, mapCountry2, firstPlayerNumber) && () && ()){
            mapCountry1.removeArmy(armyToRegroup);
            mapCountry2.addArmy(armyToRegroup);
        }
    }

    private boolean checkRegroup(Country country1, Country country2, Integer playerNumber) throws NonExistentCountry, EmptyCountryParameterException {
        Player player = players.get(playerNumber);
        return (player.dominatedCountry(country1) && player.dominatedCountry(country2) && map.validateBorderingCountry(country1, country2));
    }*/

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }
}
