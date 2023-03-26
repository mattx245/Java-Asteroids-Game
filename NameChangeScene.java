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
        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));

        // creating a label for the input field
        Label NewNameLabel = new Label("Enter New Name:");
        NewNameLabel.setTextFill(Color.WHITE);
        NewNameLabel.setFont(Font.font("Arial", 18));

        // text field for user input
        TextField newNameField = new TextField();
        newNameField.setMaxWidth(200);
        newNameField.setFont(Font.font("Arial", 16));

        // adding the label and text field to the layout
        layout.getChildren().addAll(NewNameLabel, newNameField);

        // creating a Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setFont(Font.font("Arial", 16));
        confirmButton.setOnAction(event -> {
            String newName = newNameField.getText();
            System.out.println("Name changed to: " + newName);
            // code to update the name in the game to come :)
        });

        // add the Confirm button to the layout
        layout.getChildren().add(confirmButton);

        // creating a scene with the created layout
        Scene scene = new Scene(layout, 400, 300);
        scene.setFill(Color.BLACK);

        // set the scene for the stage
        stage.setScene(scene);
        stage.setTitle("Name Change");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

