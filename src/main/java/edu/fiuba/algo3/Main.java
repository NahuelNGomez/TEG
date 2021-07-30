package edu.fiuba.algo3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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


public class Main extends Application {

    private void setArch(Rectangle rectangle, double height, double width){
        rectangle.setArcHeight(height);
        rectangle.setArcWidth(width);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        StackPane canvas = new StackPane();
        canvas.setStyle("-fx-background-color: rgb(242,204,133)");

        HBox nameBox = new HBox(100);
        nameBox.setAlignment(Pos.CENTER);
        nameBox.setPrefSize(200,50);

        Text name = new Text();
        //name.setTextAlignment(TextAlignment.RIGHT);

        Font font = new Font("verdana", 25);
        name.setText("Player1");
        name.setFont(font);

        nameBox.getChildren().addAll(name);

        HBox dataBox = new HBox();
        //dataBox.setAlignment(Pos.CENTER);

        nameBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");

        Text information = new Text();

        information.setText("*Datos jugador 1 *");
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

        primaryStage.setScene(scene);

        primaryStage.setTitle("T.E.G.");
        primaryStage.setResizable(false);

        primaryStage.show();

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

    private VBox viewDataTurn(Font font) {
        VBox dataTurn = new VBox();
        dataTurn.setMaxWidth(200);
        dataTurn.setPrefHeight(500);
        dataTurn.setAlignment(Pos.CENTER);
        Text textAttack = new Text();
        textAttack.setText("Ataca desde:");
        textAttack.setFont(font);
        ComboBox countries = new ComboBox();

        //Set text of the ComboBox
        Text attackCountry = new Text();
        //textAttack.setText("pais atacante:");

        textAttack.setFont(font);
        for(int i = 1; i <= 3 ; i ++){
            //Text choice = new Text();
            //choice.setText("choice" + i);
            //choice.setFont(font);
            countries.getItems().add("choice" + i);

        }
        Text choice2 = new Text();
        choice2.setText("choice2");
        choice2.setFont(font);

        Text choice3 = new Text();
        choice3.setText("choice3");
        choice3.setFont(font);

        // countries.setPromptText(String.valueOf(attackCountry));
        countries.setOnAction((event) -> {
            int selectedIndex = countries.getSelectionModel().getSelectedIndex();
            Object selectedItem = countries.getSelectionModel().getSelectedItem();

            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            System.out.println("   ComboBox.getValue(): " + countries.getValue());
        });
        //countries.getItems().add(choice2);
        //countries.getItems().add(choice3);

        Text attacked = new Text();
        attacked.setText("Hacia:");
        attacked.setFont(font);

        ComboBox availableCountries = new ComboBox();

        //availableCountries.setPromptText("Elige un pais para atacar");

        availableCountries.getItems().add(choice2);
        availableCountries.getItems().add(choice3);

        dataTurn.getChildren().addAll(textAttack, countries, attacked, availableCountries);

        dataTurn.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: darkred;");
        return dataTurn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}