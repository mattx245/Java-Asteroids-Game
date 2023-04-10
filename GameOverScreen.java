package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class GameOverScreen extends Application {
    private ArrayList<Integer> playerHighScores = new ArrayList<Integer>();

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        pane.setPrefSize(700, 500);
        pane.setStyle("-fx-background-color: black");

        // Load the image
        Image image = new Image("C:\\Users\\Windows10\\Desktop\\MODULES\\Java Sem 2\\Java-Asteroids-Game-main\\Java-Asteroids-Game-main\\images\\gameoverasteroid.png.jpeg");

// Create a new ImageView for the image and set its size and position
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
        imageView.setLayoutX((pane.getPrefWidth() - imageView.getFitWidth()) / 2);
        imageView.setLayoutY((pane.getPrefHeight() - imageView.getFitHeight()) / 2);

// Add the ImageView to the Pane
        pane.getChildren().add(imageView);



        Text gameOverText = new Text("GAME OVER!");
        gameOverText.setFont(Font.font("Bauhaus 93", 70));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setLayoutX(150);
        gameOverText.setLayoutY(100);

        // Create a new Text node for displaying high scores
        Text highScoresText = new Text();
        highScoresText.setFont(Font.font("Digital-7", 30));
        highScoresText.setFill(Color.WHITE);
        highScoresText.setLayoutX(150);
        highScoresText.setLayoutY(250);

        // Loop through player's high scores and add them to the highScoresText node
        for (int score : playerHighScores) {
            highScoresText.setText(highScoresText.getText() + score + "\n");
        }

        Button btn = new Button();
        btn.setText("Play Again?");
        btn.setFont(Font.font("Bauhaus 93"));
        btn.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20pt; -fx-padding: 20px 20px;");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Play Again");
            }
        });
        btn.setLayoutX(500);
        btn.setLayoutY(420); // set the Y coordinate to 420 pixels


        Button changeNameBtn = new Button();
        changeNameBtn.setText("Change Name");
        changeNameBtn.setStyle("-fx-background-color: black; -fx-font-family: 'Digital-7'; -fx-text-fill: white; -fx-font-size: 20pt; -fx-padding: 20px 20px;");
        changeNameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Change Name");
            }
        });
        changeNameBtn.setLayoutX(50); // set the X coordinate to 50 pixels
        changeNameBtn.setLayoutY(420); // set the Y coordinate to 420 pixels


        Button highScoresBtn = new Button();
        highScoresBtn.setText("High Scores");
        highScoresBtn.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20pt; -fx-padding: 20px 20px;");
        highScoresBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("High Scores");
            }
        });
        highScoresBtn.setLayoutX(275); // set the X coordinate to 275 pixels
        highScoresBtn.setLayoutY(420); // set the Y coordinate to 420 pixels


        pane.getChildren().addAll(gameOverText, highScoresText, btn, changeNameBtn, highScoresBtn);

        Scene scene = new Scene(pane,700,500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
