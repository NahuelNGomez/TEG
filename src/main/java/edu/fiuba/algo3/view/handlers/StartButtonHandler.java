package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StartButtonHandler implements EventHandler<ActionEvent> {
    Scene newScene;
    Stage firstStage;
    Game game;

    public StartButtonHandler(Scene scene, Stage stage, Game game) {
        newScene = scene;
        firstStage = stage;
        this.game = game;
    }


    @Override
    public void handle(ActionEvent actionEvent) {

        firstStage.setScene(newScene);

    }
}