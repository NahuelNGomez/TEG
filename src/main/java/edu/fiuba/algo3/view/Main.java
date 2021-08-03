package edu.fiuba.algo3.view;

import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.exceptions.*;
import edu.fiuba.algo3.view.handlers.PlacementButtonHandler;
import edu.fiuba.algo3.view.handlers.StartButtonHandler;
import javafx.application.Application;
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


public class Main extends Application {
    Game game;
    Integer actualPlayer = 1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane canvas = new StackPane();
        canvas.setStyle("-fx-background-color: rgb(242,204,133)");

        VBox mainBox = new VBox(100);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPrefSize(200,50);

        Scene scene = new Scene(canvas, 1050, 690);

        Text name = new Text();
        Font font = new Font("verdana", 25);
        name.setText("T.    E.    G.");
        name.setFont(font);

        mainBox.getChildren().addAll(name);

        mainBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        /*ComboBox players = new ComboBox();

        //Set text of the ComboBox
        Text numberOfPlayers = new Text();
        //numberOfPlayers.setFont(font);

        for(Integer i = 2; i <= 6 ; i ++){
            players.getItems().add(i);
        }
        mainBox.getChildren().add(players);*/

        Button button = new Button("Jugar");
        try {
            game = new Game(2);
        } catch (InvalidNumberOfPlayers invalidNumberOfPlayers) {
            invalidNumberOfPlayers.printStackTrace();
        }

        StartButtonHandler sendButtonEventHandler = new StartButtonHandler(firstPlacementScene(primaryStage) ,primaryStage, game);
        button.setOnAction(sendButtonEventHandler);

        button.setDefaultButton(true);
        button.setPrefSize(100, 50);
        button.setLayoutX(105);
        button.setLayoutY(110);
        mainBox.getChildren().add(button);

        canvas.getChildren().add(mainBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("T.E.G.");
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    private Scene firstPlacementScene(Stage primaryStage) throws FileNotFoundException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
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

        HBox firstHBox = new HBox();
        firstHBox.setMaxHeight(100);
        firstHBox.setAlignment(Pos.TOP_RIGHT);
        firstHBox.getChildren().addAll(nameBox);

        VBox dataTurn = viewPlacementTurn(5, primaryStage);

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

    private VBox viewPlacementTurn(Integer num, Stage primaryStage) throws NonExistentPlayer, FileNotFoundException, NonExistentCountry, EmptyCountryParameterException {
        Font font = new Font("verdana", 15);
        VBox dataTurn = new VBox(15);
        dataTurn.setMaxWidth(200);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);
        Integer amount = 5;


        ComboBox ownedCountries = new ComboBox();
        for( Country country : game.getPlayer(actualPlayer).getDominatedCountries()){
            String name = country.getName();
            String army = country.getArmyAmount().toString();
            String newString = name + army;
            ownedCountries.getItems().add(newString);
        }
        ownedCountries.setPromptText("Paises dominados");

        Text textPlacement = new Text();
        textPlacement.setText("Agregue " + num + " ejercitos");
        textPlacement.setFont(font);

        ComboBox countries = new ComboBox();
        countries.setPromptText("Elija un pais");
        ArrayList<Country> playerCountries = game.getPlayer(actualPlayer).getDominatedCountries();

        for (Country playerCountry : playerCountries) {
            countries.getItems().add(playerCountry.getName());
        }


        ComboBox amountArmy = new ComboBox();
        amountArmy.setPromptText("Cant. de ejercitos");

        while(amountArmy.getItems().size() != 0){
            for(int i = 0; i < amountArmy.getItems().size(); i++){
                amountArmy.getItems().remove(i);
            }
        }

        for(int i = 1; i <= num; i++){
            amountArmy.getItems().add(i);
        }

        Text textAmount = new Text();

        textAmount.setText(String.valueOf(amount));

        Button acceptButton = new Button("ACEPTAR");
        PlacementButtonHandler PlacementButtonHandler = new PlacementButtonHandler(amount,num,amountArmy,countries,textAmount,game,actualPlayer,primaryStage,null);
        acceptButton.setOnAction(PlacementButtonHandler);

        dataTurn.getChildren().addAll(textAmount ,textPlacement,countries,amountArmy,acceptButton,ownedCountries);

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



    public static void main(String[] args) {
        launch(args);
    }
}