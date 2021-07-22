package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;

public class AttackRound extends Round{
    private MockBattlefield battlefield = new MockBattlefield();

    public AttackRound(ArrayList<Player> gamePlayers, Map map) throws IOException {
        super(gamePlayers, map);

    }

    @Override
    public void startRound(Integer maxPlacement) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException{
        for(Player eachPlayer: players){
           if(players.indexOf(eachPlayer) == players.size()-1){
              attack(eachPlayer.getADominatedCountry(),1 ,players.get(0).getADominatedCountry());

           } else{
               attack(eachPlayer.getADominatedCountry(), 1,players.get((players.indexOf(eachPlayer)+1)).getADominatedCountry());
           }
        }
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
        attacker.addArmyinCountry(1, defendCountry);
    }

    public void attack(Country attackingCountry, int amountDice, Country defendingCountry) throws EmptyCountryParameterException, NonExistentPlayer, NonExistentCountry {
        //boolean isBordering = this.validateBorderingCountry(attackingCountry, defendingCountry);
        Country attackCountry = map.searchKeyCountryInMap(attackingCountry);
        Country defendCountry = map.searchKeyCountryInMap(defendingCountry);

        Player attacker = this.searchCountryOwner(attackCountry);
        Player defender = this.searchCountryOwner(defendCountry);

        if(/*isBordering && */attacker.canInvade(attackCountry, amountDice)) {
            Integer[] result = battlefield.battle(amountDice, defendCountry, attackCountry );
            attacker.removeArmy(result[1], attackCountry);

            if(defender.removeArmy(result[0], defendCountry)){
                this.invade(attacker, defendCountry, attackCountry);
            };
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
