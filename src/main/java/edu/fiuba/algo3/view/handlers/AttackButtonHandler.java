package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.Player;
import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidAttack;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import edu.fiuba.algo3.modelo.exceptions.NonExistentPlayer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AttackButtonHandler implements EventHandler {
    private Game game;
    private ComboBox borderingCountries;
    private ComboBox countries;
    private ComboBox amountDice;
    private Integer actualPlayer;
    private Stage primaryStage;
    private Scene scene;

    public AttackButtonHandler(ComboBox borderingCountries, ComboBox countries, ComboBox amountDice, Game game, Integer actualPlayer, Stage primaryStage, Scene scene){
        this.game = game;
        this.borderingCountries = borderingCountries;
        this.countries = countries;
        this.amountDice = amountDice;
        this.actualPlayer = actualPlayer;
        this.primaryStage = primaryStage;
        this.scene = scene;
    }
    @Override
    public void handle(Event event) {
        try {
            Country firstCountry = selectedCountryInComboBox(borderingCountries.getValue(),game.getCountries());
            Country secondCountry =selectedCountryInComboBox(countries.getValue(),game.getCountries());
            game.attack(actualPlayer,firstCountry,secondCountry,(Integer)amountDice.getValue());
        } catch (NonExistentPlayer | InvalidAttack | NonExistentCountry | EmptyCountryParameterException nonExistentPlayer) {
            nonExistentPlayer.printStackTrace();
        }
        actualPlayer++;
        primaryStage(setScene(scene));
    }
    private Country selectedCountryInComboBox(Object country, ArrayList<Country> list){
        Country selectedCountry = null;
        for(Country aCountry : list){
            if(aCountry.getName().equals(country)){
                selectedCountry = aCountry;
            }
        }
        return selectedCountry;
    }

}
