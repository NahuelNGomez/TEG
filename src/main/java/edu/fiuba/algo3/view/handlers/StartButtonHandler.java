package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.exceptions.EmptyCountryParameterException;
import edu.fiuba.algo3.modelo.exceptions.InvalidNumberOfPlayers;
import edu.fiuba.algo3.modelo.exceptions.NonExistentCountry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

public class StartButtonHandler implements EventHandler<ActionEvent> {
    Scene newScene;
    Stage firstStage;

    public StartButtonHandler(Scene scene, Stage stage) {
        newScene = scene;
        firstStage = stage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        firstStage.setScene(newScene);

    }
}