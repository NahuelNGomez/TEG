package edu.fiuba.algo3.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args){
       launch(args);

    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("T.E.G");
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout);
        Button playButton = new Button("Jugar");
        layout.setTop(playButton);
        stage.setScene(scene);
        stage.show();
    }
}
