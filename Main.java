package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StartScreen startScreen = new StartScreen();
        startScreen.start(stage);
    }
}
