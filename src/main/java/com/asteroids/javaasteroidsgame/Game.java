package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.IOException;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane pane = new Pane();
        int prefWidth = 950;
        int prefHeight = 700;
        pane.setPrefSize(prefWidth, prefHeight);

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

        // Text score
        Text score = new Text();
        score.setText("Score: 0000");
        score.setFont(Font.font(20));
        score.setFill(Color.WHITE);

        // Text score
        Text help = new Text();
        help.setText("Help");
        help.setFont(Font.font(20));
        help.setFill(Color.WHITE);

        // Create VBox to hold score
        VBox vbox_score = new VBox(score);
        vbox_score.setAlignment(Pos.CENTER);
        vbox_score.setPrefWidth(prefWidth);

        // Create VBox to hold help
        VBox vbox_help = new VBox(help);
        vbox_help.setAlignment(Pos.CENTER);

        // Add VBox to Pane
        pane.getChildren().add(vbox_score);
        pane.getChildren().add(vbox_help);

        // Set position of VBox to top of Pane
        vbox_score.setLayoutY(20);
        vbox_help.setLayoutY(20);
        vbox_help.setLayoutX(880);


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