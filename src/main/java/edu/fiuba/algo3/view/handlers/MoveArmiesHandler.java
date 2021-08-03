package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidRegroup;
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
import java.util.HashMap;

public class MoveArmiesHandler implements EventHandler {

    private Game game;
    private ComboBox countries;
    private ComboBox otherCountry;
    private Integer actualPlayer;
    private Stage primaryStage;
    private Scene scene;

    private Text textAmount;
    private ComboBox armies;
    private HashMap<Country, Integer> playerCountries = new HashMap<>();

    public MoveArmiesHandler(ComboBox armies, ComboBox countries, ComboBox otherCountry, Text textAmount,Game game, Integer actualPlayer, Stage primaryStage, Scene scene){
        this.armies = armies;
        this.game = game;
        this.countries = countries;
        this.otherCountry = otherCountry;
        this.actualPlayer = actualPlayer;
        this.primaryStage = primaryStage;
        this.scene = scene;

        this.textAmount = textAmount;
    }

    @Override
    public void handle(Event event) {
        Country firstCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());
        Country secondCountry = selectedCountryInComboBox(otherCountry.getValue(),game.getCountries());

        System.out.println(firstCountry.getName());
        System.out.println(secondCountry.getName());
        try {
            game.regroup(actualPlayer,firstCountry,secondCountry, (Integer) armies.getValue());
        } catch (EmptyCountryParameterException | NonExistentCountry | NonExistentPlayer | InvalidRegroup e) {
            e.printStackTrace();
        }

        if(actualPlayer < game.amountOfPlayers()){
            actualPlayer = actualPlayer + 1;
            try {
                primaryStage.setScene(changeScene());
            } catch (FileNotFoundException | NonExistentPlayer | NonExistentCountry | EmptyCountryParameterException e) {
                e.printStackTrace();
            }
        } else {
            actualPlayer = 1;
            primaryStage.setScene(scene);
        }
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

        Text amountArmyText = new Text();

        ComboBox amountArmy = new ComboBox();
        amountArmy.setPromptText("Cant. de fichas");

        //countries.setOnAction((event) -> eventComboBoxFilterCountries(countries,receivingCountries,amountArmyText,amountArmy));

        Text moveToText = new Text();
        moveToText.setText("Hacia:");
        moveToText.setFont(font);

        Text amountText = new Text();
        amountText.setText("estos ejercitos: ");
        amountText.setFont(font);

        Button moveArmyButton = new Button("REAGRUPAR");

        MoveArmiesHandler moveArmiesHandler = new MoveArmiesHandler(amountArmy,countries,receivingCountries,textAmount,game,actualPlayer,primaryStage,scene);
        moveArmyButton.setOnAction(moveArmiesHandler);

        dataTurn.getChildren().addAll(textAttack, countries,amountArmyText, moveToText, receivingCountries,amountText,amountArmy,moveArmyButton);
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
