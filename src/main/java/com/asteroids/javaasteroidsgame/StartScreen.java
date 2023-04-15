package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartScreen extends Application {

    static Stage classStage = new Stage();

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.setPrefSize(950, 700);

        // Game title
        Text title = new Text("Asteroids!");
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 72));
        title.setFill(Color.WHITE);
        title.setX(330);
        title.setY(150);

        // Subheading
        Text subheading = new Text("by Group 6");
        subheading.setFont(Font.font("Monospace", FontWeight.BOLD, 36));
        subheading.setFill(Color.WHITE);
        subheading.setX(375);
        subheading.setY(200);

        // Button container
        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setLayoutX(375);
        buttonContainer.setLayoutY(250);

        // Button style
        String buttonStyle = "-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-font-family: Monospace; -fx-font-size: 24;";

        // Start game button
        Button btn = new Button("Start Game");
        btn.setStyle(buttonStyle);
        btn.setPrefWidth(200);

        btn.setOnAction(actionEvent -> {
            AsteroidsApplication game = new AsteroidsApplication();
            try {
                game.start(AsteroidsApplication.classStage);
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // High score button
        Button btn2 = new Button("High Score");
        btn2.setStyle(buttonStyle);
        btn2.setPrefWidth(200);

        btn2.setOnAction(actionEvent -> {
            HighScore hs = new HighScore();
            try {
                hs.start(HighScore.classStage);
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // How to play button
        Button btn3 = new Button("How to Play");
        btn3.setStyle(buttonStyle);
        btn3.setPrefWidth(200);

        btn3.setOnAction(actionEvent -> {
            HowToPlay htp = new HowToPlay();
            try {
                htp.start(HowToPlay.classStage);
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Add buttons to the container
        buttonContainer.getChildren().addAll(btn, btn2, btn3);

        // Set up the scene
        pane.getChildren().addAll(title, subheading, buttonContainer);
        Scene scene = new Scene(pane, 950, 700);
        pane.setStyle("-fx-background-color: black");
        stage.setScene(scene);
        stage.setTitle("Asteroid Game");

        classStage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
