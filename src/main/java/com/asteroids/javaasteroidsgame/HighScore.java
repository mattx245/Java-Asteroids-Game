package com.asteroids.javaasteroidsgame;

//based off howtoplay. thanks yiming!
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HighScore extends Application {

    public static String filepath = "score.txt";
    //turns text file to hashmap
    public static LinkedHashMap<String, Integer> toHash() {
        Map<String, Integer> fromFile = new HashMap<String, Integer>();
        BufferedReader br = null;

        try {

            File file = new File(filepath);
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String name = parts[0].trim();
                Integer points = Integer.parseInt(parts[1].trim());

                if (!name.equals("") && !points.equals(""))
                    fromFile.put(name, points);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }

        //sorts hashmap by value after
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : fromFile.entrySet()) {
            list.add(entry.getValue());
    }
        Collections.sort(list, Collections.reverseOrder());
        for (int num:list) {
            for (Map.Entry<String, Integer> entry : fromFile.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        return sortedMap;
    }

    public static String getHighScoreText() {
        StringBuilder toAdd = new StringBuilder();
        LinkedHashMap<String, Integer> iterMap = toHash();
        int count = 0;

        // iterates through sorted map and displays first 10 entries
        for (Map.Entry<String, Integer> entry : iterMap.entrySet()) {
            toAdd.append("Name: " + entry.getKey() + ", Score: " + entry.getValue() + "\n");
            count++;

            if (count == 10) {
                break;
            }
        }
        return toAdd.toString();
    }

    public List<Integer> getHighScoreList() {
        List<Integer> highScores = new ArrayList<>();
        LinkedHashMap<String, Integer> iterMap = toHash();

        // Add the high scores to the list
        for (Map.Entry<String, Integer> entry : iterMap.entrySet()) {
            highScores.add(entry.getValue());
        }

        return highScores;
    }
    //scene switching stage
    static Stage classStage = new Stage();
    @Override
    public void start(Stage primaryStage) {

        //High score title
        Text title = new Text();
        title.setText("High Score");
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 72));
        title.setFill(Color.WHITE);
        title.setX(330);
        title.setY(100);

        Text text = new Text();
        StringBuilder toAdd = new StringBuilder();
        LinkedHashMap<String, Integer> iterMap = toHash();
        int count = 0;

        //iterates through sorted map and displays first 10 entries
        for (Map.Entry<String, Integer> entry : iterMap.entrySet()) {
            toAdd.append("Name: " + entry.getKey() + ", Score: " + entry.getValue() + "\n");
            count++;

            if (count == 10) {
                break;
            }
        }
        String add = toAdd.toString();
        text.setText(add);
        text.setFont(Font.font("Monospace", FontWeight.BOLD, 24));
        text.setFill(Color.WHITE);
        text.setX(375);
        text.setY(50);

        //VBox to hold title and text
        VBox vbox = new VBox(title, text);
        vbox.setSpacing(80); //Add space between the title and text
        vbox.setAlignment(Pos.TOP_CENTER);

        // Button style
        String buttonStyle = "-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-font-family: Monospace; -fx-font-size: 24;";


        //button to return to start screen
        Button btn = new Button();
        btn.setText("Start Screen");
        btn.setStyle(buttonStyle);
        btn.setTranslateY(-100);




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

        // Puts button at bottom and title/text up top
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vbox);
        borderPane.setBottom(btn);
        BorderPane.setAlignment(btn, Pos.CENTER);
        btn.setLayoutX(375);
        btn.setLayoutY(650);

        // Create a StackPane to get the vbox
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black");
        root.getChildren().add(borderPane);


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
