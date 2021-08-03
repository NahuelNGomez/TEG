package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Continent;
import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.Player;
import edu.fiuba.algo3.modelo.exceptions.*;
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
        this.actualPlayer = actualPlayer;
        this.primaryStage = primaryStage;
        this.scene = scene;
    }
    @Override
    public void handle(Event event) {
        try {
            System.out.println("jugador " + actualPlayer + " atacó");
            System.out.println("cantidad de jugadores: " + game.amountOfPlayers());
            System.out.println("de " + countries.getValue() + " hacia: " + borderingCountries.getValue() + " con " + (Integer)amountDice.getValue() +" dado");

            Country firstCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());
            Country secondCountry = selectedCountryInComboBox(borderingCountries.getValue(),game.getCountries());
             game.attack(actualPlayer,firstCountry,secondCountry,(Integer)amountDice.getValue());

             if(game.theresAWinner()){
                 primaryStage.setScene(winnerScene());
             }
            Continent continent = game.getPlayer(actualPlayer).dominatedContinent(game.getContinents());
            if( continent != null){ //domino un pais
                primaryStage.setScene(dominatedContinentScene(continent));
            }
        } catch (NonExistentPlayer | InvalidAttack | NonExistentCountry | EmptyCountryParameterException | FileNotFoundException | NonExistentContinent nonExistentPlayer) {
            nonExistentPlayer.printStackTrace();
        }

        if(actualPlayer < game.amountOfPlayers()){
            actualPlayer = actualPlayer + 1;
            try {
                primaryStage.setScene(changeScene());
            } catch (FileNotFoundException | NonExistentPlayer | NonExistentCountry | EmptyCountryParameterException e) {
                e.printStackTrace();
            }
        } else {
            try {
                actualPlayer = 1;
                primaryStage.setScene(regroupScene());
            } catch (FileNotFoundException | NonExistentPlayer | EmptyCountryParameterException | NonExistentCountry e) {
                e.printStackTrace();
            }
        }
    }

    private Scene winnerScene() {
        StackPane canvas = new StackPane();
        canvas.setStyle("-fx-background-color: rgb(242,204,133)");

        HBox winnerBox = new HBox(100);
        winnerBox.setAlignment(Pos.CENTER);
        winnerBox.setPrefSize(200,50);

        Text winner = new Text();

        Font font = new Font("verdana", 15);
        winner.setText("EL GANADOR ES EL JUGADOR NRO " + actualPlayer);
        winner.setFont(font);

        winnerBox.getChildren().addAll(winner);

        winnerBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        canvas.getChildren().add(winnerBox);
        canvas.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(canvas, 1050, 690);
        return scene;
    }

    private Scene dominatedContinentScene(Continent continent) throws FileNotFoundException, NonExistentPlayer, NonExistentContinent {
        StackPane canvas = new StackPane();
        canvas.setStyle("-fx-background-color: rgb(242,204,133)");

        HBox nameBox = new HBox(100);
        nameBox.setAlignment(Pos.CENTER);
        nameBox.setPrefSize(200,50);

        Text name = new Text();

        Font font = new Font("verdana", 15);
        name.setText("Jugador n° " + actualPlayer);
        name.setFont(font);

        nameBox.getChildren().addAll(name);

        HBox dataBox = new HBox();

        nameBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        Text information = new Text();

        information.setText("Posee "+ game.getPlayer(actualPlayer).amountOfDominatedCountries());
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

        VBox dataTurn = viewDominatedContinent(font,continent);

        HBox map = viewMap();

        HBox secondHBox = new HBox();
        secondHBox.getChildren().addAll(dataTurn, map);

        VBox mainBox = new VBox(20);
        mainBox.getChildren().addAll(firstHBox, secondHBox);

        canvas.getChildren().add(mainBox);
        canvas.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(canvas, 1050, 690);
        return scene;
    }

    private VBox viewDominatedContinent(Font font, Continent continent) throws NonExistentPlayer, NonExistentContinent {
        VBox dataTurn = new VBox(15);
        dataTurn.setMaxWidth(100);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);

        ComboBox ownedCountries = new ComboBox();
        for( Country country : game.getPlayer(actualPlayer).getDominatedCountries()){
            String name = country.getName();
            String army = country.getArmyAmount().toString();
            String newString = name + army;
            ownedCountries.getItems().add(newString);
        }
        ownedCountries.setPromptText("Paises dominados");

        Text infoText = new Text();
        infoText.setText("Se domino un continent");
        Text textAttack = new Text();
        textAttack.setText("Agregar fichas a:");
        textAttack.setFont(font);

        ComboBox countries = new ComboBox();
        ArrayList<Country> playerCountries = game.getPlayer(actualPlayer).getDominatedCountries();
        for (Country playerCountry : playerCountries) {
            countries.getItems().add(playerCountry.getName());
        }

        ComboBox armies = new ComboBox();
        armies.setPromptText("Elija una cantidad");
        countries.setPromptText("Elija un pais");

        Text armyamount = new Text();
        armyamount.setText("esta cantidad de fichas: ");
        armyamount.setFont(font);

        while(armies.getItems().size() != 0){
            for(int i = 0; i < armies.getItems().size(); i++){
                armies.getItems().remove(i);
            }
        }

        for( int i = 1; i <= game.armyToAddByDominatedContinent(continent); i++){
            armies.getItems().add(i);
        }
        Button addButton = new Button("AGREGAR");

        Text textAmount = new Text();
        Integer initialamount = game.armyToAddByDominatedContinent(continent);
        textAmount.setText(String.valueOf(initialamount));

        AddButtonHandler addButtonHandler = new AddButtonHandler(initialamount,initialamount,armies,countries,textAmount,game,actualPlayer,primaryStage,scene);
        addButton.setOnAction(addButtonHandler);

        dataTurn.getChildren().addAll(infoText,textAmount,textAttack,countries,armyamount,armies,addButton,ownedCountries);
        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        return dataTurn;
    }

    private Scene regroupScene() throws FileNotFoundException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
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

        information.setText("Posee "+ game.getPlayer(actualPlayer).amountOfDominatedCountries()+" paises" );
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
        Font dataFont = new Font("verdana", 15);

        VBox dataTurn = viewRegroupData(dataFont);

        HBox map = viewMap();

        HBox secondHBox = new HBox();
        secondHBox.getChildren().addAll(dataTurn, map);

        VBox mainBox = new VBox(20);
        mainBox.getChildren().addAll(firstHBox, secondHBox);

        canvas.getChildren().add(mainBox);
        canvas.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(canvas, 1050, 690);
        return scene;
    }

    private VBox viewRegroupData(Font font) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, FileNotFoundException {
        VBox dataTurn = new VBox(15);
        dataTurn.setMaxWidth(100);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);

        ComboBox ownedCountries = new ComboBox();
        for( Country country : game.getPlayer(actualPlayer).getDominatedCountries()){
            String name = country.getName();
            String army = country.getArmyAmount().toString();
            String newString = name + army;
            ownedCountries.getItems().add(newString);
        }
        ownedCountries.setPromptText("Paises dominados");

        Text textAttack = new Text();
        textAttack.setText("Agregar fichas a:");
        textAttack.setFont(font);

        ComboBox countries = new ComboBox();
        ArrayList<Country> playerCountries = game.getPlayer(actualPlayer).getDominatedCountries();
        for (Country playerCountry : playerCountries) {
            countries.getItems().add(playerCountry.getName());
        }

        ComboBox armies = new ComboBox();
        armies.setPromptText("Elija una cantidad");
        countries.setPromptText("Elija un pais");

        Text armyamount = new Text();
        armyamount.setText("esta cantidad de fichas: ");
        armyamount.setFont(font);

        while(armies.getItems().size() != 0){
            for(int i = 0; i < armies.getItems().size(); i++){
                armies.getItems().remove(i);
            }
        }

        for( int i = 1; i <= game.getPlayer(actualPlayer).amountOfArmiesToAddInRegroupRound(); i++){
            armies.getItems().add(i);
        }

        Integer initialamount = game.getPlayer(actualPlayer).amountOfArmiesToAddInRegroupRound();

        Button regroupButton = new Button("AGREGAR");

        Text textAmount = new Text();
        Integer amount = game.getPlayer(actualPlayer).amountOfArmiesToAddInRegroupRound();
        textAmount.setText(String.valueOf(amount));

        RegroupButtonHandler regroupButtonHandler = new RegroupButtonHandler(amount,initialamount,countries,armies,textAmount,game,actualPlayer,primaryStage,changeScene());
        regroupButton.setOnAction(regroupButtonHandler);

        dataTurn.getChildren().addAll(textAmount,textAttack,countries,armyamount,armies,regroupButton,ownedCountries);
        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        return dataTurn;
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

        information.setText("Posee "+ game.getPlayer(actualPlayer).amountOfDominatedCountries()+" paises");
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

        HBox map = viewMap();

        HBox secondHBox = new HBox();
        secondHBox.getChildren().addAll(dataTurn, map);

        VBox mainBox = new VBox(20);
        mainBox.getChildren().addAll(firstHBox, secondHBox);

        canvas.getChildren().add(mainBox);
        canvas.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(canvas, 1050, 690);
        return scene;
    }

    private HBox viewMap() throws FileNotFoundException {
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

        ComboBox ownedCountries = new ComboBox();
        for( Country country : game.getPlayer(actualPlayer).getDominatedCountries()){
            String name = country.getName();
            String army = country.getArmyAmount().toString();
            String newString = name + army;
            ownedCountries.getItems().add(newString);
        }
        ownedCountries.setPromptText("Paises dominados");

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

        dataTurn.getChildren().addAll(textAttack, countries,armiesAttack, attacked, borderingCountries,armiesDefend,amountDice,attackButton,ownedCountries);
        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        return dataTurn;
    }

    private void comboBoxEventBorderingCountries(ComboBox borderingCountries, ComboBox countries, ComboBox amountDice, Text armiesDefend) {
        Font newFont = new Font("verdana", 10);

        Country attackerCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());

        while(amountDice.getItems().size() != 0){
            for(int i = 0; i < amountDice.getItems().size(); i++){
                amountDice.getItems().remove(i);
            }
        }

        if(attackerCountry.getArmyAmount() > 3){
            for(int i = 1; i <= 3; i++ ){
                amountDice.getItems().add(i);
            }
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
        while(borderingCountries.getItems().size() != 0){
            for(int i = 0; i < borderingCountries.getItems().size(); i++){
                borderingCountries.getItems().remove(i);
            }
        }

        for(Country borderCountry : borderCountries) {
            borderingCountries.getItems().add(borderCountry.getName());
        }

    }

}


