package com.example.asteroidsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

import java.io.IOException;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane pane = new Pane();
        pane.setPrefSize(950, 700);

        // Rocket
        //Polygon ship = new Polygon(-5, -10, 25, 0, -5, 10);
        //ship.setTranslateX(450);
        //ship.setTranslateY(350);
        //ship.setStroke(Color.WHITE);
        Rocketship rocket = new Rocketship();
        Polygon ship = rocket.getElement();

        pane.getChildren().add(ship);

        Scene scene = new Scene(pane);
        stage.setTitle("Asteroid Game");
        stage.setScene(scene);
        scene.setFill(Color.BLACK);
        stage.show();


        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                rocket.turnLeft();
            }

            if (event.getCode() == KeyCode.RIGHT) {
                rocket.turnRight();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}