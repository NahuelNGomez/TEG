package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Country;
import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidPlacement;
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

public class AddButtonHandler implements EventHandler {
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

    public AddButtonHandler(Integer num,  Integer initialAmount,ComboBox amount,ComboBox countries,Text textAmount,Game game,Integer actualPlayer,Stage primaryStage,Scene nextScene) {
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
    public void handle(Event event) {
        num = num - (Integer)amount.getValue();

        textAmount.setText(String.valueOf (num));
        Country country = selectedCountryInComboBox(countries.getValue(), game.getCountries());

        playerCountries.put(country, (Integer)amount.getValue());

        if(num == 0) {
            try {
                game.playerDominatedContinent(player,playerCountries);
                num = 5;
                if (player.equals(game.amountOfPlayers())) {
                    player = 1;
                    num = 3;
                    initialAmount = 3;
                    //primaryStage.setScene(firstplacementScene(primaryStage, num));

                } else {
                    player++;
                    textAmount.setText(String.valueOf(num));
                    primaryStage.setScene(placementScene(primaryStage, num));
                }
            } catch (InvalidPlacement | NonExistentCountry | EmptyCountryParameterException | NonExistentPlayer | FileNotFoundException invalidPlacement) {
                invalidPlacement.printStackTrace();
            }
        }
    }

    private Scene placementScene(Stage stage, Integer num) throws NonExistentPlayer, NonExistentCountry, EmptyCountryParameterException, FileNotFoundException {
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
        PlacementButtonHandler PlacementButtonHandler = new PlacementButtonHandler(amount,num,amountArmy,countries,textAmount,game,player,primaryStage,null);
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
}
