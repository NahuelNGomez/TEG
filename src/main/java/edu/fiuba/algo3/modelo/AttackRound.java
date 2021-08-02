package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AttackRound{
    private MockBattlefield battlefield = new MockBattlefield();
    private WinnerDefiner winnerDefiner;
    private ArrayList<Player> players;
    private Map map;
    private ArrayList<CountryCard> countryCards;

    public AttackRound(ArrayList<Player> gamePlayers, Map gameMap, ArrayList<CountryCard> cards) throws IOException {
        players = gamePlayers;
        map = gameMap;
        countryCards = cards;
        this.winnerDefiner = new WinnerDefiner(players);
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


    public void giveACountryCard(Player attacker){
        Random random = new Random();
        CountryCard countryCard = countryCards.get(random.nextInt(countryCards.size()));
        attacker.addCountryCard(countryCard);
        countryCards.remove(countryCard);
    }

    private boolean checkIfAttackerDominatedAContinent(Player player) throws EmptyCountryParameterException {
        return map.checkIfAttackerDominatedAContinent(player);
    }

    public void playerDominatedAContinent(Player player, HashMap<Country,Integer> armiesToAdd) throws EmptyCountryParameterException, InvalidPlacement, NonExistentCountry {
        Integer total = 0;
        for(Integer num : armiesToAdd.values()){
            total = total + num;
        }
        Continent dominatedByPlayer = map.continentDominatedByPlayer(player);

        if( (dominatedByPlayer == null) || (total > dominatedByPlayer.getAmountOfArmyWhenDominated())){
            throw new InvalidPlacement();
        }

        if(checkIfAttackerDominatedAContinent(player)){
            for(Country country : armiesToAdd.keySet()){
                Country mapCountry = map.searchKeyCountryInMap(country);
                player.addArmyInCountry(armiesToAdd.get(country),mapCountry);
            }
        }
    }

    private void invade(Player attacker, Country countryDefender, Country countryAttacker) throws EmptyCountryParameterException, NonExistentCountry { //JUGADOR VACIO
        attacker.removeArmy(1, countryAttacker);
        attacker.addCountry(countryDefender);
        giveACountryCard(attacker);
    }

    private boolean validateBorderingCountry(Country attackingCountry, Country defendingCountry) throws EmptyCountryParameterException, NonExistentCountry {
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);
        return map.validateBorderingCountry(attackCountry, defendCountry);
    }

    public boolean checkValidAttack(Country attackingCountry,Country defendingCountry,Player attackerPlayer,Player defenderPlayer) throws NonExistentCountry, EmptyCountryParameterException, NonExistentPlayer {
        boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);
        boolean validArmiesInAttacker = attackingCountry.validArmiesToAttack();
       // boolean validArmiesInDefender = defendingCountry.validArmiesToAttack();
        return (isBordering && (defenderPlayer != attackerPlayer) && validArmiesInAttacker/* && validArmiesInDefender*/);
    }

    public Player attack(Player attackerPlayer,Country attackingCountry, Country defendingCountry, Integer amountDice,Integer winner) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry, InvalidAttack {
        Country mapAttackingCountry = map.searchKeyCountryInMap(attackingCountry);
        Country mapDefendingCountry = map.searchKeyCountryInMap(defendingCountry);
        Player defenderPlayer = this.searchCountryOwner(mapDefendingCountry);

        if(!checkValidAttack(mapAttackingCountry,mapDefendingCountry,attackerPlayer,defenderPlayer) ||
                (!attackerPlayer.canInvade(mapAttackingCountry, amountDice))){
            throw new InvalidAttack();
        }
        Integer[] result = battlefield.battle(amountDice, mapDefendingCountry, winner);

        if(attackerPlayer.removeArmy(result[1], mapAttackingCountry)) {
            this.invade(defenderPlayer, mapAttackingCountry, mapDefendingCountry);
        }
        if(defenderPlayer.removeArmy(result[0], mapDefendingCountry)){
            System.out.println("El jugador atacante invadio");
            this.invade(attackerPlayer, mapDefendingCountry, mapAttackingCountry);
        }

        if(winnerDefiner.theresAWinner()){
            return winnerDefiner.winner();
        }
        return null;
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

    public boolean correctAmountOfArmyInCountry(Country country, Integer expectedAmount) throws NonExistentCountry {
        return (map.searchKeyCountryInMap(country).correctAmountOfArmyInCountry(expectedAmount));
    }
}
