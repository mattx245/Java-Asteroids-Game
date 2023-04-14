package com.asteroids.javaasteroidsgame;

//based off howtoplay. thanks yiming!
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HighScore extends Application {

    private AtomicInteger pts = new AtomicInteger();

    public void setpts(AtomicInteger add){
        pts = add;
    }

    //scene switching stage
    static Stage classStage = new Stage();
    @Override
    public void start(Stage primaryStage) {

        //asteroidsapplication class to access points
        //AsteroidsApplication pointsapp = new AsteroidsApplication();

        //points from current game session
        //AtomicInteger currentpoints = pointsapp.getpoints();
        //creates high score hashmap
        //HashMap<String, Integer> hiscore = new HashMap<String, Integer>();
        //gets content from sorted hashmap and displays as high score

        Text text = new Text();
        text.setText(String.valueOf(pts));
        text.setFont(Font.font(20));
        text.setFill(Color.WHITE);

        //High score title
        Text title = new Text();
        title.setText("High Score");
        title.setFont(Font.font(35));
        title.setFill(Color.WHITE);

        //VBox to hold title and text
        VBox vbox = new VBox(title, text);
        vbox.setSpacing(80); //Add space between the title and text
        vbox.setAlignment(Pos.CENTER);

        // Create a StackPane to get the vbox
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black");
        root.getChildren().add(vbox);


        Scene scene = new Scene(root, 950, 700);
        primaryStage.setTitle("High score");

        primaryStage.setScene(scene);

        primaryStage.show();
        //assigns stage variable for call in startscreen
        classStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
