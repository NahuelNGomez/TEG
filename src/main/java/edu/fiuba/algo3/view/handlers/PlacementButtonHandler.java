package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.exceptions.*;
import javafx.event.ActionEvent;
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

public class PlacementButtonHandler implements EventHandler<ActionEvent> {
    private Integer num;
    private ComboBox amount;
    private Text textAmount;
    private ComboBox countries;
    private Game game;
    private HashMap<Country, Integer> playerCountries;
    private Integer initialAmount;
    private Integer player;
    private Scene nextScene;
    private Stage primaryStage;

    public PlacementButtonHandler(Integer num,  Integer initialAmount,ComboBox amount,ComboBox countries,Text textAmount,Game game,Integer actualPlayer,Stage primaryStage,Scene nextScene) {
        this.num = num;
        this.amount = amount;
        this.textAmount = textAmount;
        this.game = game;
        this.countries = countries;
        playerCountries = new HashMap<Country, Integer>();
        this.initialAmount = initialAmount;
        player = actualPlayer;
        this.nextScene = nextScene;
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        num = num - (Integer)amount.getValue();

        textAmount.setText(String.valueOf (num));
        Country country = selectedCountryInComboBox(countries.getValue(), game.getCountries());

        playerCountries.put(country, (Integer)amount.getValue());

        if(num == 0 && initialAmount == 5){
            try {
                game.placingFiveArmiesInPlacementRound(player,playerCountries);
                num = 5;
                if(player.equals(game.amountOfPlayers())){
                    player = 1;
                    num = 3;
                    initialAmount = 3;
                    primaryStage.setScene(firstplacementScene(primaryStage, num));

                } else{
                    player++;
                    textAmount.setText(String.valueOf (num));
                    primaryStage.setScene(firstplacementScene(primaryStage, num));

                };

            } catch (InvalidPlacement | NonExistentCountry | EmptyCountryParameterException | NonExistentPlayer | FileNotFoundException invalidPlacement) {
                invalidPlacement.printStackTrace();
            }
        } else if(num == 0 && initialAmount == 3){
            try {
                game.placingThreeArmiesInPlacementRound(player,playerCountries);
                num = 3;
                if(player.equals(game.amountOfPlayers())){
                    player = 1;
                    primaryStage.setScene(changeScene());
                } else{
                    player++;
                    primaryStage.setScene(firstplacementScene(primaryStage, num));
                };
            } catch (InvalidPlacement | NonExistentCountry | EmptyCountryParameterException | NonExistentPlayer | FileNotFoundException invalidPlacement) {
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

    private Scene firstplacementScene(Stage primaryStage, Integer num) throws FileNotFoundException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        StackPane canvas = new StackPane();
        canvas.setStyle("-fx-background-color: rgb(242,204,133)");

        HBox nameBox = new HBox(100);
        nameBox.setAlignment(Pos.CENTER);
        nameBox.setPrefSize(200,50);

        Text name = new Text();

        Font font = new Font("verdana", 25);
        name.setText("Jugador n° " + player);
        name.setFont(font);

        nameBox.getChildren().addAll(name);

        HBox firstHBox = new HBox();
        firstHBox.setMaxHeight(100);
        firstHBox.setAlignment(Pos.TOP_RIGHT);
        firstHBox.getChildren().addAll(nameBox);

        VBox dataTurn = viewPlacementTurn(num, primaryStage);

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
        Integer amount = num;

        ComboBox ownedCountries = new ComboBox();
        for( Country country : game.getPlayer(player).getDominatedCountries()){
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
        ArrayList<Country> playerCountries = game.getPlayer(player).getDominatedCountries();

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
        PlacementButtonHandler PlacementButtonHandler = new PlacementButtonHandler(amount,num,amountArmy,countries,textAmount,game,player,primaryStage,changeScene());
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

    private Scene changeScene() throws FileNotFoundException, NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException {
        StackPane canvas = new StackPane();
        canvas.setStyle("-fx-background-color: rgb(242,204,133)");

        HBox nameBox = new HBox(100);
        nameBox.setAlignment(Pos.CENTER);
        nameBox.setPrefSize(200,50);

        Text name = new Text();

        Font font = new Font("verdana", 25);
        name.setText("Jugador n° " + player);
        name.setFont(font);

        nameBox.getChildren().addAll(name);

        HBox dataBox = new HBox();

        nameBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        Text information = new Text();

        information.setText("Posee "+ game.getPlayer(player).amountOfDominatedCountries() + " paises");
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

    private VBox viewDataTurn(Font font) throws NonExistentPlayer {
        VBox dataTurn = new VBox(15);
        dataTurn.setMaxWidth(200);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);

        ComboBox ownedCountries = new ComboBox();
        for( Country country : game.getPlayer(player).getDominatedCountries()){
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
        ArrayList<Country> playerCountries = game.getPlayer(player).getDominatedCountries();

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

        countries.setOnAction((event) -> eventComboBoxFilterCountries(countries,borderingCountries,armiesAttack));

        Text attacked = new Text();
        attacked.setText("Hacia:");
        attacked.setFont(font);

        ComboBox amountDice = new ComboBox();
        amountDice.setPromptText("Cant. de dados");

        borderingCountries.setOnAction((event) -> {
            comboBoxEventBorderingCountries(borderingCountries,countries,amountDice,armiesDefend);
        });

        Button attackButton = new Button("ATACAR");
        AttackButtonHandler attackButtonHandler = new AttackButtonHandler(borderingCountries, countries, amountDice, game, player, primaryStage, null);
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

    private void eventComboBoxFilterCountries(ComboBox countries, ComboBox borderingCountries,Text armiesAttack){
        Font newFont = new Font("verdana", 10);

        Country selectedCountry = selectedCountryInComboBox(countries.getValue(),game.getCountries());

        armiesAttack.setText(" Cant. ejércitos "+ selectedCountry.getArmyAmount());
        armiesAttack.setFont(newFont);

        ArrayList<Country> borderCountries = null;

        try {
            borderCountries = game.getOtherPlayersBorderingCountries(player,selectedCountry);
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

