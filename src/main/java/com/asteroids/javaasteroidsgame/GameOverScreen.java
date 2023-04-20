package com.asteroids.javaasteroidsgame;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.VBox;

public class GameOverScreen extends HighScore {
    public int playerScore;
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

    public GameOverScreen(int playerScore) {
        this.playerScore = playerScore;

        // Get the high scores list from the HighScore class
        List<Integer> playerHighScores = getHighScoreList();

        // Add the player's score to the high scores list
        playerHighScores.add(playerScore);

        // Sort the high scores list in descending order
        Collections.sort(playerHighScores, Collections.reverseOrder());

    }
    //scene switching stage
    static Stage classStage = new Stage();

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        createStars(pane, 200);
        pane.setPrefSize(950, 700);
        pane.setStyle("-fx-background-color: black");

        // Load the "gameover.png" image
        Image gameoverImage = new Image(getClass().getResourceAsStream("/img/gameover.png"));

        // Create a new ImageView object to display the image
        ImageView gameoverImageView = new ImageView(gameoverImage);

        // Set the position and size of the ImageView
        gameoverImageView.setFitWidth(400);
        gameoverImageView.setFitHeight(400);
        gameoverImageView.setX((pane.getPrefWidth() - gameoverImageView.getFitWidth()) / 2);
        gameoverImageView.setY((pane.getPrefHeight() - gameoverImageView.getFitHeight()) / 2);

        // Adds the game over Text that prints to the screen
        Text gameOverText = new Text("GAME OVER!");
        gameOverText.setFont(Font.font("Bauhaus 93", 100));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setX((pane.getPrefWidth() - gameOverText.getLayoutBounds().getWidth()) / 2);
        gameOverText.setY(gameOverText.getLayoutBounds().getHeight() + 10);

        // Creates a new Text node for displaying high scores
        Text highScoresText = new Text("Your score: " + playerScore);
        highScoresText.setFont(Font.font("Verdana", 60));
        highScoresText.setFill(Color.WHITE);
        highScoresText.setX((pane.getPrefWidth() - highScoresText.getLayoutBounds().getWidth()) / 2);
        highScoresText.setY(gameoverImageView.getY() + gameoverImageView.getFitHeight() - 30);

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

        // uses an HBox layout with padding to space out the buttons
        HBox buttonBox = new HBox(15); // Space between buttons is 15
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        buttonBox.getChildren().addAll(playAgainBtn, viewHighScoresBtn, mainMenuBtn);

// set the alignment of the buttonBox to center within the HBox
        HBox.setHgrow(buttonBox, javafx.scene.layout.Priority.ALWAYS);
        HBox.setMargin(buttonBox, new javafx.geometry.Insets(0, 50, 0, 50));
        buttonBox.setAlignment(Pos.CENTER);

// Add the button box at the bottom of the pane
        VBox vbox = new VBox();
        vbox.setPrefSize(pane.getPrefWidth(), pane.getPrefHeight());
        vbox.getChildren().addAll(gameOverText, highScoresText, gameoverImageView, buttonBox);
        VBox.setVgrow(buttonBox, javafx.scene.layout.Priority.ALWAYS);
        vbox.setAlignment(Pos.CENTER);

// Add spacing to the VBox
        vbox.setSpacing(-20);

        pane.getChildren().addAll(vbox);

// Set the stage size to (950, 700)
        stage.setWidth(950);
        stage.setHeight(700);

        Scene scene = new Scene(pane, 950, 700); // Set the new scene size
        stage.setScene(scene);
        stage.show();
//assigns stage variable for call in startscreen
        classStage = stage;
    }


    public static void main(String[] args) {
        launch();
    }
}
