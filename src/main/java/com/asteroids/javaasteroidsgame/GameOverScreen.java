package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameOverScreen extends HighScore {
    private int playerScore;
    private int playerRank;
    private List<Integer> playerHighScores = new ArrayList<>();

    public GameOverScreen(int playerScore) {
        this.playerScore = playerScore;

        // Get the high scores list from the HighScore class
        playerHighScores = getHighScoreList();

        // Add the player's score to the high scores list
        playerHighScores.add(playerScore);

        // Sort the high scores list in descending order
        Collections.sort(playerHighScores, Collections.reverseOrder());

        // Calculate the player's rank based on their position in the sorted list
        int rank = 1;
        for (Integer score : playerHighScores) {
            if (score == playerScore) {
                break;
            }
            rank++;
        }
        this.playerRank = rank;
    }
    //scene switching stage
    static Stage classStage = new Stage();

    @Override
    public void start(Stage stage) {
          Pane pane = new Pane();
        pane.setPrefSize(700, 500);
        pane.setStyle("-fx-background-color: black");

        // Creates a new Text node for displaying high scores
        Text highScoresText = new Text();
        highScoresText.setFont(Font.font("Digital-7", 30));
        highScoresText.setFill(Color.WHITE);
        highScoresText.setLayoutX(150);
        highScoresText.setLayoutY(200);

        /// Get the high scores list and sort it in descending order
        List<Integer> sortedHighScores = new ArrayList<>(playerHighScores);
        Collections.sort(sortedHighScores, Collections.reverseOrder());

        // Add the player's score to the sorted high scores list and sort it again
        sortedHighScores.add(playerScore);
        Collections.sort(sortedHighScores, Collections.reverseOrder());

        // Find the rank of the player's score in the sorted high scores list
        int rank = sortedHighScores.indexOf(playerScore) + 1;

        // Set the text to display the player's score and their rank
        highScoresText.setText("Your score: " + playerScore + "\nYour rank: " + rank);


        // Adds the game over Text that prints to the screen
        Text gameOverText = new Text("GAME OVER!");
        gameOverText.setFont(Font.font("Bauhaus 93", 70));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setLayoutX(150);
        gameOverText.setLayoutY(100);

        // Button style
        String buttonStyle = "-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-font-family: Monospace; -fx-font-size: 24;";

        // Play again button which should bring the player back into the game
        Button playAgainBtn = new Button("Play Again?");
        playAgainBtn.setStyle(buttonStyle);
        playAgainBtn.setOnAction(actionEvent -> {
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
        playAgainBtn.setLayoutX(150);
        playAgainBtn.setLayoutY(420);

        // View high scores button
        Button viewHighScoresBtn = new Button("View High Scores");
        viewHighScoresBtn.setStyle(buttonStyle);
        viewHighScoresBtn.setOnAction(e -> {
                HighScore highScore = new HighScore();
                try {
                    highScore.start(HighScore.classStage);
                    stage.close();
                } catch (Exception ee) {
                    throw new RuntimeException(ee);
                }
        });
        viewHighScoresBtn.setLayoutX(325);
        viewHighScoresBtn.setLayoutY(420);

        // Start screen button
        Button mainMenuBtn = new Button("Start Screen");
        mainMenuBtn.setStyle(buttonStyle);
        mainMenuBtn.setOnAction(event -> {
            StartScreen ss = new StartScreen();
            try {
                ss.start(StartScreen.classStage);
                stage.close();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
        mainMenuBtn.setLayoutX(450);
        mainMenuBtn.setLayoutY(420);


        // use an HBox layout with padding to space out the buttons
        HBox buttonBox = new HBox(20); // Space between buttons is 20
        buttonBox.setLayoutX(150);
        buttonBox.setLayoutY(420);


        pane.getChildren().addAll(buttonBox, playAgainBtn, viewHighScoresBtn, gameOverText, highScoresText, mainMenuBtn);


        Scene scene = new Scene(pane,700,500);
        stage.setScene(scene);
        stage.show();
        //assigns stage variable for call in startscreen
        classStage = stage;
    }


    public static void main(String[] args) {
        launch();
    }
}
