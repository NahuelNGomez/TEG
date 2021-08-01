package edu.fiuba.algo3.view.handlers;

import edu.fiuba.algo3.modelo.Game;
import edu.fiuba.algo3.modelo.exceptions.InvalidNumberOfPlayers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.io.IOException;

public class StartButtonHandler implements EventHandler<ActionEvent> {
    ComboBox comboBox;
    Scene newScene;
    Stage firstStage;
    Game game;

    public StartButtonHandler(ComboBox firstComboBox, Scene scene, Stage stage, Game newGame) {
        comboBox = firstComboBox;
        newScene = scene;
        firstStage = stage;
        game = newGame;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            game = new Game((Integer) comboBox.getValue());
            firstStage.setScene(newScene);

        } catch (InvalidNumberOfPlayers invalidNumberOfPlayers) {
            invalidNumberOfPlayers.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}