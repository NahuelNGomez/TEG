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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        this.actualPlayer = 1;
        this.primaryStage = primaryStage;
        this.scene = scene;
    }
    @Override
    public void handle(Event event) {
        try {
            Country firstCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());
            Country secondCountry = selectedCountryInComboBox(borderingCountries.getValue(),game.getCountries());
            game.attack(actualPlayer,firstCountry,secondCountry,(Integer)amountDice.getValue());
        } catch (NonExistentPlayer | InvalidAttack | NonExistentCountry | EmptyCountryParameterException nonExistentPlayer) {
            nonExistentPlayer.printStackTrace();
        }
        System.out.println("jugador " + actualPlayer + " atacó");
        System.out.println("cantidad de jugadores: " + game.amountOfPlayers());
        System.out.println("de " + countries.getValue() + " hacia: " + borderingCountries.getValue() + " con " + (Integer)amountDice.getValue() +" dado");

        if(actualPlayer < game.amountOfPlayers()){
            actualPlayer++;
            try {
                primaryStage.setScene(changeScene());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NonExistentPlayer nonExistentPlayer) {
                nonExistentPlayer.printStackTrace();
            } catch (NonExistentCountry nonExistentCountry) {
                nonExistentCountry.printStackTrace();
            } catch (EmptyCountryParameterException e) {
                e.printStackTrace();
            }
        } else {
            actualPlayer = 1;
            try {
                primaryStage.setScene(changeScene());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NonExistentPlayer nonExistentPlayer) {
                nonExistentPlayer.printStackTrace();
            } catch (NonExistentCountry nonExistentCountry) {
                nonExistentCountry.printStackTrace();
            } catch (EmptyCountryParameterException e) {
                e.printStackTrace();
            }
        }
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

    private Scene changeScene() throws FileNotFoundException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        StackPane canvas = new StackPane();
        canvas.setStyle("-fx-background-color: rgb(242,204,133)");

        HBox nameBox = new HBox(100);
        nameBox.setAlignment(Pos.CENTER);
        nameBox.setPrefSize(200,50);

        Text name = new Text();

        Font font = new Font("verdana", 25);
        name.setText("Jugador n° " + actualPlayer);
        name.setFont(font);

        nameBox.getChildren().addAll(name);

        HBox dataBox = new HBox();

        nameBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        Text information = new Text();

        information.setText("Posee "+ game.getPlayer(actualPlayer).amountOfDominatedCountries()+" paises - * Datos del jugador nro. " + actualPlayer);
        information.setFont(font);
        dataBox.getChildren().add(information);
        dataBox.setPrefSize(860,50);

        dataBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        dataBox.setAlignment(Pos.CENTER);

        HBox firstHBox = new HBox();
        firstHBox.setMaxHeight(100);
        firstHBox.setAlignment(Pos.TOP_RIGHT);
        firstHBox.getChildren().addAll(nameBox,dataBox);

        VBox dataTurn = viewDataTurn(font);

        HBox map = viewMap(font);

        HBox secondHBox = new HBox();
        secondHBox.getChildren().addAll(dataTurn, map);

        VBox mainBox = new VBox(20);
        mainBox.getChildren().addAll(firstHBox, secondHBox);

        canvas.getChildren().add(mainBox);
        canvas.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(canvas, 1050, 690);
        return scene;
    }

    private HBox viewMap(Font font) throws FileNotFoundException {
        HBox map = new HBox();

        map.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        FileInputStream mapFile = new FileInputStream("src/main/java/edu/fiuba/algo3/archivos/tableroTeg.png");
        Image mapImage = new Image(mapFile);
        ImageView mapImageView = new ImageView(mapImage);
        map.getChildren().add(mapImageView);

        map.setAlignment(Pos.CENTER);
        map.setPrefWidth(500);
        map.setPrefHeight(500);

        return map;
    }

    private VBox viewDataTurn(Font font) throws NonExistentPlayer {
        VBox dataTurn = new VBox(15);
        dataTurn.setMaxWidth(200);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);

        Text textAttack = new Text();
        textAttack.setText("Ataca desde:");
        textAttack.setFont(font);

        ComboBox countries = new ComboBox();
        ArrayList<Country> playerCountries = game.getPlayer(actualPlayer).getDominatedCountries();

        for (Country playerCountry : playerCountries) {
            if(!playerCountry.correctAmountOfArmyInCountry(1)){
                countries.getItems().add(playerCountry.getName());
            }
        }

        ComboBox borderingCountries = new ComboBox();
        borderingCountries.setPromptText("Elija un pais");
        countries.setPromptText("Elija un pais");

        Text armiesAttack = new Text();
        Text armiesDefend = new Text();

        countries.setOnAction((event) -> eventComboBoxFilterCountries(playerCountries,countries,borderingCountries,armiesAttack));

        Text attacked = new Text();
        attacked.setText("Hacia:");
        attacked.setFont(font);

        ComboBox amountDice = new ComboBox();
        amountDice.setPromptText("Cant. de dados");

        borderingCountries.setOnAction((event) -> {
            comboBoxEventBorderingCountries(borderingCountries,countries,amountDice,armiesDefend);
        });

        Button attackButton = new Button("ATACAR");

        AttackButtonHandler attackButtonHandler = new AttackButtonHandler(borderingCountries, countries, amountDice, game, actualPlayer, primaryStage, null);
        attackButton.setOnAction(attackButtonHandler);
       /* attackButton.setOnAction((event) -> {
            try {
                Country firstCountry = selectedCountryInComboBox(borderingCountries.getValue(),game.getCountries());
                Country secondCountry =selectedCountryInComboBox(countries.getValue(),game.getCountries());
                game.attack(actualPlayer,firstCountry,secondCountry,(Integer)amountDice.getValue());
            } catch (NonExistentPlayer | InvalidAttack | NonExistentCountry | EmptyCountryParameterException nonExistentPlayer) {
                nonExistentPlayer.printStackTrace();
            }
        });*/

        dataTurn.getChildren().addAll(textAttack, countries,armiesAttack, attacked, borderingCountries,armiesDefend,amountDice,attackButton);
        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        return dataTurn;
    }

    private void comboBoxEventBorderingCountries(ComboBox borderingCountries, ComboBox countries, ComboBox amountDice, Text armiesDefend) {
        Font newFont = new Font("verdana", 10);

        Country attackerCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());

        if(attackerCountry.getArmyAmount() >= 3){
            amountDice.getItems().add(3);
        } else {
            for(int i = 1; i <= attackerCountry.getArmyAmount(); i++ ){
                amountDice.getItems().add(i);
            }
        }

        Country anotherCountry = selectedCountryInComboBox(borderingCountries.getValue(),game.getCountries());
        armiesDefend.setText(" Cant. ejércitos "+ anotherCountry.getArmyAmount());
        armiesDefend.setFont(newFont);
    }

    private void eventComboBoxFilterCountries(ArrayList<Country> playerCountries, ComboBox countries, ComboBox borderingCountries,Text armiesAttack){
        Font newFont = new Font("verdana", 10);

        Country selectedCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());

        armiesAttack.setText(" Cant. ejércitos "+ selectedCountry.getArmyAmount());
        armiesAttack.setFont(newFont);

        ArrayList<Country> borderCountries = null;

        try {
            borderCountries = game.getOtherPlayersBorderingCountries(actualPlayer,selectedCountry);
        } catch (NonExistentPlayer | EmptyCountryParameterException | NonExistentCountry nonExistentPlayer) {
            nonExistentPlayer.printStackTrace();
        }

        for(Country borderCountry : borderCountries) {
            borderingCountries.getItems().add(borderCountry.getName());
        }

    }

}


