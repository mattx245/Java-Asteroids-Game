package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.stage.Stage;


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
