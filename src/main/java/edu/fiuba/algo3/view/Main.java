package edu.fiuba.algo3.view;

import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.exceptions.*;
import edu.fiuba.algo3.view.handlers.AttackButtonHandler;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main extends Application {
    Game game;
    Integer actualPlayer = 1;

    private void setArch(Rectangle rectangle, double height, double width){
        rectangle.setArcHeight(height);
        rectangle.setArcWidth(width);
    }

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
        name.setText("Marque la cantidad de jugadores");
        name.setFont(font);

        mainBox.getChildren().addAll(name);

        mainBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        /*ComboBox players = new ComboBox();

        //Set text of the ComboBox
        String numberOfPlayers = new String();
        //numberOfPlayers.setFont(font);

        for(int i = 2; i <= 6 ; i ++){
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

    private VBox viewPlacementTurn(Integer num, Stage primaryStage, Scene scene) throws NonExistentPlayer, FileNotFoundException, NonExistentCountry, EmptyCountryParameterException {
        Font font = new Font("verdana", 15);
        VBox dataTurn = new VBox(15);
        dataTurn.setMaxWidth(200);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);
        Integer amount = 5;


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

        for(int i = 1; i <= num; i++){
            amountArmy.getItems().add(i);
        }

        Text textAmount = new Text();

        textAmount.setText(String.valueOf(amount));

        Button acceptButton = new Button("ACEPTAR");

        PlacementButtonHandler PlacementButtonHandler = new PlacementButtonHandler(amount ,amountArmy, countries,textAmount, game, num,actualPlayer, primaryStage, changeScene());


        acceptButton.setOnAction(PlacementButtonHandler);

        dataTurn.getChildren().addAll(textAmount ,textPlacement,countries,amountArmy,acceptButton);
        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        return dataTurn;
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

        information.setText("*Datos del jugador nro. " + actualPlayer);
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

    private VBox viewDataTurn(Font font, Stage primaryStage) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, FileNotFoundException {
        actualPlayer = 1;
        VBox dataTurn = new VBox(15);
        dataTurn.setMaxWidth(200);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);

        Text textAttack = new Text();
        textAttack.setText("Ataca desde:");
        textAttack.setFont(font);

        ComboBox countries = new ComboBox();
        ArrayList<Country> playerCountries = game.getPlayer(actualPlayer).getDominatedCountries();
        ComboBox borderingCountries = new ComboBox();
        borderingCountries.setPromptText("Elija un pais");
        countries.setPromptText("Elija un pais");

        Text armiesAttack = new Text();
        Text armiesDefend = new Text();
        for (Country playerCountry : playerCountries) {
            if(!playerCountry.correctAmountOfArmyInCountry(1)){
                countries.getItems().add(playerCountry.getName());
            }
        }

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
        AttackButtonHandler attackButtonHandler = new AttackButtonHandler(borderingCountries, countries, amountDice, game, actualPlayer, primaryStage, changeScene());
        attackButton.setOnAction(attackButtonHandler);

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
            //amountDice.getItems().removeAll(amountDice.getItems());
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

    private Country selectedCountryInComboBox(Object country, ArrayList<Country> list){
        Country selectedCountry = null;
        for(Country aCountry : list){
            if(aCountry.getName().equals(country)){
                selectedCountry = aCountry;
            }
        }
        return selectedCountry;
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

    public static void main(String[] args) {
        launch(args);
    }
}