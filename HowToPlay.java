package com.asteroids.javaasteroidsgame;

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
public class HowToPlay extends Application {

    //scene switching stage
    static Stage classStage = new Stage();
    @Override
    public void start(Stage primaryStage) {
        // Text content
        Text text = new Text();
        text.setText("D - Rotate Right\nA - Rotate Left\nSpace - Fire\n1- Apply Thrust\n2 - Hyperspace Jump");
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

        //button to return to title
        Button btn = new Button();
        btn.setText("Title Screen");

        // Create a StackPane to get the vbox
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black");
        root.getChildren().add(vbox);


        Scene scene = new Scene(root, 950, 700);
        primaryStage.setTitle("HowToPlay");

        primaryStage.setScene(scene);

        primaryStage.show();
        //assigns stage variable for call in startscreen
        classStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
