package com.example.helloworld;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NameChangeScene extends Application {

    @Override
    public void start(Stage stage) {

        // creating a VBox layout with black backgroung
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: black;");

        // creating a label for the input field
        Label newNameLabel = new Label("Enter New Name:");
        newNameLabel.setTextFill(Color.WHITE);
        newNameLabel.setFont(Font.font("Arial", 18));

        // text field for user input
        TextField newNameField = new TextField();
        newNameField.setMaxWidth(200);
        newNameField.setFont(Font.font("Arial", 16));

        // adding the label and text field to the layout
        layout.getChildren().addAll(newNameLabel, newNameField);

        // creating a Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setFont(Font.font("Arial", 16));
        confirmButton.setOnAction(event -> {
            String newName = newNameField.getText();
            System.out.println("Name changed to: " + newName);
            newNameLabel.setText("Your new name is: " + newName);
            // code to update the name in the game to come :)
        });

        // adding the button to the layout
        layout.getChildren().add(confirmButton);

        // creating a scene with the created layout
        Scene newNameScene = new Scene(layout, 400, 300);
        newNameScene.setFill(Color.BLACK);

        // seting the scene for the stage
        stage.setScene(newNameScene);
        stage.setTitle("Name Change");
        stage.show();
    }
 public static void main(String[] args) {
        launch(args);
    }
}
