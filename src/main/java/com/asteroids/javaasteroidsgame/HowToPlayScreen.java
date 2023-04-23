package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.Random;

import static com.asteroids.javaasteroidsgame.GameScreen.HEIGHT;
import static com.asteroids.javaasteroidsgame.GameScreen.WIDTH;

public class HowToPlayScreen extends Application {

    private void createStars(Pane pane, int numStars) {

        pane.setPrefSize(1280, 720);
        createStars(pane, 200);
        Random random = new Random();

        for (int i = 0; i < numStars; i++) {
            double x = random.nextDouble() * pane.getPrefWidth();
            double y = random.nextDouble() * pane.getPrefHeight();
            double radius = 1 + random.nextDouble() * 2;

            Circle star = new Circle(x, y, radius, Color.WHITE);
            pane.getChildren().add(star);
        }
    }

        //scene switching stage
    static Stage classStage = new Stage();
    @Override
    public void start(Stage primaryStage) {
        // Text content
        Text text = new Text();
        text.setText("Right - Turn Right\nLeft - Turn Left\nSpace - Fire\nUp - Accelerate\nJ - Hyperspace Jump");
        text.setFont(Font.font(20));
        text.setFill(Color.WHITE);

        // Title
        Text title = new Text();
        title.setText("How to Play");
        title.setFont(Font.font(35));
        title.setFill(Color.WHITE);

        //Create a VBox to hold the title and text separately
        VBox vbox = new VBox(title, text);
        vbox.setSpacing(80); //Add space between the title and text
        vbox.setAlignment(Pos.CENTER);

        // Button style
        String buttonStyle = "-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-font-family: Monospace; -fx-font-size: 24;";

        //button to return to start screen
        Button btn = new Button();
        btn.setText("Start Screen");
        btn.setStyle(buttonStyle);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StartScreen ss = new StartScreen();
                try {
                    ss.start(StartScreen.classStage);
                    primaryStage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        vbox.getChildren().add(btn);
        btn.setLayoutX(350);
        btn.setLayoutY(800);
        btn.setFont(Font.font(35));

        // Create a StackPane to get the vbox
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black");
        root.getChildren().add(vbox);



        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("HowToPlayScreen");

        primaryStage.setScene(scene);

        primaryStage.show();
        //assigns stage variable for call in startscreen
        classStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
