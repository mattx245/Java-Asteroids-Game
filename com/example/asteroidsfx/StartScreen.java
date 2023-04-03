package com.example.asteroidsfx;
//By Matthew
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane pane = new Pane();
        pane.setPrefSize(700, 500);

        Button btn = new Button();
        btn.setText("Start Game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Start Game");
            }
        });

        Button btn2 = new Button();
        btn2.setText("High Score");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                System.out.println("High Score");
                            }
                        });

        Pane scene = new Pane();
        stage.setTitle("Asteroid Game");

        btn.setLayoutX(200);
        btn2.setLayoutX(200);
        btn.setLayoutY(150);
        btn2.setLayoutY(250);
        btn.setFont(Font.font("Bauhaus",20));
        btn2.setFont(Font.font("Bauhaus",20));
        scene.getChildren().add(btn);
        scene.getChildren().add(btn2);
        Scene scene2 = new Scene(scene,500,500);
        scene.setStyle("-fx-background-color:black");
        stage.setScene(scene2);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
