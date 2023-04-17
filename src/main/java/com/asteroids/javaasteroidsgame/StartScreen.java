package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.Random;


public class StartScreen extends Application {

    static Stage classStage = new Stage();

    private void createStars(Pane pane, int numStars) {
        Random random = new Random();

        for (int i = 0; i < numStars; i++) {
            double x = random.nextDouble() * pane.getPrefWidth();
            double y = random.nextDouble() * pane.getPrefHeight();
            double radius = 1 + random.nextDouble() * 2;

            Circle star = new Circle(x, y, radius, Color.WHITE);
            pane.getChildren().add(star);
        }
    }


    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.setPrefSize(1280, 720);
        createStars(pane, 200);

        // Game title
        Text title = new Text("Asteroids!");
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 100));
        title.setFill(Color.WHITE);
        title.setX(185);
        title.setY(150);

        // Subheading
        Text subheading = new Text("by Group 6");
        subheading.setFont(Font.font("Monospace", FontWeight.BOLD, 36));
        subheading.setFill(Color.WHITE);
        subheading.setX(365);
        subheading.setY(210);

        // Button container
        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setLayoutX(325);
        buttonContainer.setLayoutY(300);

        // Button style
        String buttonStyle = "-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-font-family: Monospace; -fx-font-size: 24; -fx-pref-height: 60;";

        // Start game button
        Button btn = new Button("Start Game");
        btn.setStyle(buttonStyle);
        btn.setPrefWidth(300);

        btn.setOnAction(actionEvent -> {
            AsteroidsApplication game = null;
            try {
                game = new AsteroidsApplication();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        btn2.setPrefWidth(300);

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
        btn3.setPrefWidth(300);

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

        // Background: creates 200 stars randomly distributed across the pane
        pane.setPrefSize(1280, 720);
        createStars(pane, 200); // The second parameter is the number of stars

        Image startImage_left = new Image(getClass().getResourceAsStream("/img/Startscreen1.png"));
        Image startImage_right = new Image(getClass().getResourceAsStream("/img/Startscreen2.png"));

        ImageView startImageView_left = new ImageView(startImage_left);
        ImageView startImageView_right = new ImageView(startImage_right);

        // Set the dimensions of the images
        startImageView_left.setFitWidth(300);
        startImageView_left.setFitHeight(200);
        startImageView_right.setFitWidth(300);
        startImageView_right.setFitHeight(200);

        // Position the images on the left and right sides
        startImageView_left.setX(50);
        startImageView_right.setX(pane.getPrefWidth() - 650); // 950 - 300 (width) - 50 (margin)

// Center the images on the Y-axis
        double centerY = (pane.getPrefHeight() - 200) / 2; // (720 - 200) / 2
        startImageView_left.setY(centerY);
        startImageView_right.setY(centerY);


        // Set up the scene
        pane.getChildren().addAll(startImageView_left, startImageView_right);
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
