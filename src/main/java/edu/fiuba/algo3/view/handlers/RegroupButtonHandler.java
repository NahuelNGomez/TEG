package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
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
import java.util.HashMap;

public class RegroupButtonHandler implements EventHandler {
    private Game game;
    private ComboBox countries;
    private ComboBox armies;
    private Integer actualPlayer;
    private Stage primaryStage;
    private Scene scene;

    private Text textAmount;
    private Integer num;
    private Integer initialAmount;
    private HashMap<Country, Integer> playerCountries = new HashMap<>();

    public RegroupButtonHandler(Integer num,  Integer initialAmount,ComboBox countries, ComboBox armies, Text textAmount,Game game, Integer actualPlayer, Stage primaryStage, Scene scene){
        this.num = num;
        this.initialAmount = initialAmount;
        this.game = game;
        this.countries = countries;
        this.armies = armies;
        this.actualPlayer = actualPlayer;
        this.primaryStage = primaryStage;
        this.scene = scene;

        this.textAmount = textAmount;
    }

    @Override
    public void handle(Event event) {
        num = num - (Integer)armies.getValue();

        textAmount.setText(String.valueOf (num));
        Country country = selectedCountryInComboBox(countries.getValue(), game.getCountries());

        playerCountries.put(country, (Integer)armies.getValue());

        if(num == 0){
            try {
                game.addArmiesInRegroupRound(actualPlayer,playerCountries);
                if(actualPlayer.equals(game.amountOfPlayers())){
                    actualPlayer = 1;
                    primaryStage.setScene(changeScene());
                } else{
                    actualPlayer++;
                    primaryStage.setScene(regroupScene());
                };
            } catch (NonExistentCountry | EmptyCountryParameterException | NonExistentPlayer | FileNotFoundException | InvalidRegroup invalidPlacement) {
                invalidPlacement.printStackTrace();
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


    private Scene regroupScene() throws FileNotFoundException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
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

        VBox dataTurn = viewRegroupData(font);

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

        num = game.getPlayer(actualPlayer).amountOfArmiesToAddInRegroupRound();
        textAmount.setText(String.valueOf(num));

        Button regroupButton = new Button("AGREGAR");

        RegroupButtonHandler regroupButtonHandler = new RegroupButtonHandler(num,initialAmount,countries,armies,textAmount,game,actualPlayer,primaryStage,scene);
        regroupButton.setOnAction(regroupButtonHandler);

        dataTurn.getChildren().addAll(textAmount,textAttack,countries,armies,armyamount,regroupButton,ownedCountries);
        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        return dataTurn;
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

        VBox dataTurn = viewMovingArmiesData(font);

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

    private VBox viewMovingArmiesData(Font font) throws NonExistentPlayer {
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
        textAttack.setText("Mover desde:");
        textAttack.setFont(font);

        ComboBox countries = new ComboBox();
        ArrayList<Country> playerCountries = game.getPlayer(actualPlayer).getDominatedCountries();

        for (Country playerCountry : playerCountries) {
            if(!playerCountry.correctAmountOfArmyInCountry(1)){
                countries.getItems().add(playerCountry.getName());
            }
        }

        ComboBox receivingCountries = new ComboBox();
        receivingCountries.setPromptText("Elija un pais");
        countries.setPromptText("Elija un pais");

        ComboBox amountArmy = new ComboBox();
        amountArmy.setPromptText("Cant. de fichas");

        Text amountArmyText = new Text();
        countries.setOnAction((event) -> eventComboBoxFilterCountries(countries,receivingCountries,amountArmyText,amountArmy));

        Text moveToText = new Text();
        moveToText.setText("Hacia:");
        moveToText.setFont(font);

        Text amountText = new Text();
        amountText.setText("estos ejercitos: ");
        amountText.setFont(font);

        Button moveArmyButton = new Button("REAGRUPAR");

        MoveArmiesHandler attackButtonHandler = new MoveArmiesHandler(amountArmy,countries,receivingCountries,textAmount,game,actualPlayer,primaryStage,scene);
        moveArmyButton.setOnAction(attackButtonHandler);

        dataTurn.getChildren().addAll(textAttack, countries,amountArmyText, moveToText, receivingCountries,amountText,amountArmy,moveArmyButton,ownedCountries);
        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        return dataTurn;
    }

    private void eventComboBoxFilterCountries(ComboBox countries, ComboBox borderingCountries,Text armiesText,ComboBox armies){
        Font newFont = new Font("verdana", 10);

        Country selectedCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());

        armiesText.setText(" Cant. ejércitos "+ selectedCountry.getArmyAmount());
        armiesText.setFont(newFont);

        ArrayList<Country> borderCountries = null;

        try {
            borderCountries = game.getSamePlayersBorderingCountries(actualPlayer,selectedCountry);
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

        while(armies.getItems().size() != 0){
            for(int i = 0; i < armies.getItems().size(); i++){
                armies.getItems().remove(i);
            }
        }
        for(int i = 1; i < selectedCountry.getArmyAmount(); i++){
            armies.getItems().add(i);
        }
    }
}
