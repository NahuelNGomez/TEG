package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;

public class AttackRound extends Round{
    private MockBattlefield battlefield = new MockBattlefield();
    private WinnerDefiner winnerDefiner;

    public AttackRound(ArrayList<Player> gamePlayers, Map map) throws IOException {
        super(gamePlayers, map);
        this.winnerDefiner = new WinnerDefiner(players);
    }

    @Override
    public void firstRounds(Integer maxPlacement){
        System.out.println("no hace nada");
    }

    @Override
    public Player startRound() throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException{
        int i = 0;

        while (i < players.size() && !winnerDefiner.theresAWinner() && playersStillHaveCountries()) {
            if (players.indexOf(players.get(i)) == players.size() - 1) {
                if((players.get(i).amountOfDominatedCountries() != 0) && (players.get(0).amountOfDominatedCountries() != 0)){
                    attack(players.get(i).getADominatedCountry(), 1, players.get(0).getADominatedCountry(), i);
                } else {
                    System.out.println("ALGUNO SE QUEDO SIN COUNTRIES");
                }
            } else {
                if((players.get(i).amountOfDominatedCountries() != 0) && (players.get((players.indexOf(players.get(i)) + 1)).amountOfDominatedCountries() != 0)){
                    attack(players.get(i).getADominatedCountry(), 1, players.get((players.indexOf(players.get(i)) + 1)).getADominatedCountry(), i);
                } else {
                    System.out.println("ALGUNO SE QUEDO SIN COUNTRIES");
                }
            }
            i++;
            if (winnerDefiner.theresAWinner()) {
                return winnerDefiner.winner();
            }
        }
        return null;
    }

    public boolean playersStillHaveCountries(){
        for( Player player : players){
            if(player.amountOfDominatedCountries() == 0) return false;
        }
        return true;
    }

    private Player searchCountryOwner(Country country) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry {
        Country mapCountry = map.searchKeyCountryInMap(country);

        checkValidCountryParameter(mapCountry);
        Player owner = null;
        for( Player player : players ){
            if(player.isSearchedCountry(mapCountry)){
                owner = player;
            }
        }
        checkSearchedPlayer(owner);
        return owner;
    }

    private boolean validateBorderingCountry(Country attackingCountry, Country defendingCountry) throws EmptyCountryParameterException, NonExistentCountry {
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        return map.validateBorderingCountry(attackCountry, defendCountry);
    }


    private void invade(Player attacker, Country countryDefender, Country countryAttacker) throws EmptyCountryParameterException, NonExistentCountry { //JUGADOR VACIO
        Country attackCountry = map.searchKeyCountryInMap(countryAttacker);
        Country defendCountry = map.searchKeyCountryInMap(countryDefender);

        attacker.removeArmy(1, attackCountry);
        attacker.addCountry(defendCountry);
        attacker.addArmyInCountry(1, defendCountry);

        if(checkIfAttackerDominatedAContinent(attacker)){
            Continent dominatedByPlayer = map.continentDominatedByPlayer(attacker);
            attacker.addArmyInCountry(dominatedByPlayer.getAmountOfArmyWhenDominated(),countryAttacker);
        }
    }

    private boolean checkIfAttackerDominatedAContinent(Player player) throws EmptyCountryParameterException {
        return map.checkIfAttackerDominatedAContinent(player);
    }

    public void attack(Country attackingCountry, int amountDice, Country defendingCountry, Integer winner) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry {
        //boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        Player attacker = this.searchCountryOwner(attackCountry);
        Player defender = this.searchCountryOwner(defendCountry);

        //CHEQUEAR QUE SON LIMITROFES EN LA INTERFAZ CON PICKERS

        if(attacker.canInvade(attackCountry, amountDice)) {
            Integer[] result = battlefield.battle(amountDice, defendCountry, winner);

            if(attacker.removeArmy(result[1], attackCountry)) {
                this.invade(defender, attackCountry, defendCountry);
            }
            if(defender.removeArmy(result[0], defendCountry) /*&& (defendCountry.getArmyAmount() > 1)*/){
                this.invade(attacker, defendCountry, attackCountry);
            }
        }
    }

    private void checkValidCountryParameter(Country country) throws EmptyCountryParameterException {
        if(country == null) {
            throw new EmptyCountryParameterException();
        }
    }

    private void checkSearchedPlayer(Player player) throws NonExistentPlayer {
        if(player == null) {
            throw new NonExistentPlayer();
        }
    }
}
