package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreen extends Application {

    //creates variable for startscreen
    static Stage classStage = new Stage();

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane pane = new Pane();
        pane.setPrefSize(950, 700);

        //button to start game proper
        Button btn = new Button();
        btn.setText("Start Game");

            btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AsteroidsApplication game = new AsteroidsApplication();
                try {
                    game.start(AsteroidsApplication.classStage);
                    stage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //button to go to high score screen
        Button btn2 = new Button();
        btn2.setText("High Score");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HighScore hs = new HighScore();
                try {
                    hs.start(HighScore.classStage);
                    stage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //button to go to how to play screen
        Button btn3 = new Button();
        btn3.setText("How to Play");

        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HowToPlay htp = new HowToPlay();
                try {
                    htp.start(HowToPlay.classStage);
                    stage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Pane scene = new Pane();
        stage.setTitle("Asteroid Game");

        btn.setLayoutX(350);
        btn2.setLayoutX(350);
        btn3.setLayoutX(350);
        btn.setLayoutY(150);
        btn2.setLayoutY(250);
        btn3.setLayoutY(350);
        btn.setFont(Font.font(35));
        btn2.setFont(Font.font(35));
        btn3.setFont(Font.font(35));
        scene.getChildren().add(btn);
        scene.getChildren().add(btn2);
        scene.getChildren().add(btn3);
        Scene scene2 = new Scene(scene,950,700);
        scene.setStyle("-fx-background-color:black");
        stage.setScene(scene2);

        //assigns stage variable for call in startscreen
        classStage = stage;
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
