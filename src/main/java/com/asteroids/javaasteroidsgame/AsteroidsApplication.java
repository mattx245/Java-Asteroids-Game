package com.asteroids.javaasteroidsgame;

import javafx.animation.Interpolatable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
//imports for the animation of turning
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javafx.animation.AnimationTimer;
//movement imports
import javafx.geometry.Point2D;
//array of asteroids imports
//projectiles removing asteroids
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
//shooting projectiles
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;







//created following the mooc.fi tutorial: https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
public class AsteroidsApplication extends Application {

    //setting the rotation angle for the on click event
    private double rotationAngle = 0.0;
    //size of screen
    public static int WIDTH = 950;
    public static int HEIGHT = 700;
    //declaring the projectile list and leaving it empty at the beginning
    List<Projectile> projectiles = new ArrayList<>();

    //movement variable
    Point2D movement = new Point2D(1, 0);
    //variable for controlling the shooting of projectiles
    private boolean canShoot = true;
    private boolean canShootUFO = true;

    // create a boolean variable to keep track of whether the UFO has entered the screen or not
    private boolean ufoEntered = false;


    //creates variable for startscreen
    static Stage classStage = new Stage();

    //points int
    HighScore hs = new HighScore();
    private AtomicInteger pts = new AtomicInteger();

    //high score hashmap nd filepath
    final static String outputpath = "score.txt";
    HashMap<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();

    //name for high score and setter
    String name;
    public void setName (String nm)  {
        this.name = nm;
    }

    int level = 1; //initialize level
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        pane.setStyle("-fx-background-color: black;");

        //text for points
        Text points = new Text(10, 20, "Points: 0");

        // point test design & position
        points.setFont(Font.font(20));
        VBox vbox_points = new VBox(points);
        vbox_points.setAlignment(Pos.CENTER);
        vbox_points.setPrefWidth(WIDTH);
        pane.getChildren().add(vbox_points);
        vbox_points.setLayoutY(20);

        // text for level
        Text levelText = new Text(6, 20, "Level: 1");
        levelText.setFont(Font.font(20));
        levelText.setFill(Color.WHITE);
        VBox vbox_level = new VBox(levelText);
        vbox_level.setPrefWidth(WIDTH);
        pane.getChildren().add(vbox_level);
        vbox_level.setLayoutY(20);

        // text for lives
        AtomicInteger ls = new AtomicInteger();
        ls.set(3);
        Text healthText = new Text(200, 39, "Lives:" + ls);
        healthText.setFont(Font.font(20));
        healthText.setFill(Color.WHITE);
        pane.getChildren().add(healthText);

        UFO ufo = new UFO(WIDTH / 3, HEIGHT / 3);
        pane.getChildren().add(ufo.getCharacter());
        ufo.getCharacter().setVisible(false);


        //old ship
        //Polygon ship = new Polygon(0,0,-10,20,10,20);
        //ship.setTranslateX(300);
        //ship.setTranslateY(200);
        //ship.setRotate(rotationAngle);
        //ship.setRotate(-90); // rotating the ship 90 degrees counterclockwise

        //new ship from its own class
        //setting the position to center
        Ship ship = new Ship(300, 200);
        ship.setHealth(3);
        //adding the asteroids as a list
        List<Asteroid> asteroids = new ArrayList<>();
        // List of projectiles for UFO bullets
        List<Projectile> ufoProjectiles = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }

        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        // asteroid movement
        /*for (Asteroid asteroid : asteroids) {
            asteroid.turnRight();
            asteroid.turnRight();
            asteroid.accelerate();
            asteroid.accelerate();
        }*/


        Scene scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        points.setFill(Color.WHITE);

        // Text help
        Text help = new Text();
        help.setText("Help");
        help.setFont(Font.font(20));
        help.setFill(Color.WHITE);

        // Create VBox to hold help
        VBox vbox_help = new VBox(help);
        vbox_help.setAlignment(Pos.CENTER);
        pane.getChildren().add(vbox_help);

        // Set position of VBox to top of Pane
        vbox_help.setLayoutY(20);
        vbox_help.setLayoutX(880);

        //rotating the ship
        //creating an animation timer that needs a pressedKeys hashmap to smooth the animation
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        //getting the pressed keys from the hashmap for faster/smoother animation
        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {

            private long lastSpawnTime = System.nanoTime();

            private long lastUfoUpdateTime = 0;

            private long lastVisibleTime = 0;
            private boolean ufoSpawned = false;

            @Override
            public void handle(long now) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }
                if (pressedKeys.getOrDefault(KeyCode.DOWN, false)) {
                    ship.reverse();
                }
                //adding projectiles
                // shoot only if there are less than three projectiles on the screen
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 3 && canShoot) {
                    // Inside the AnimationTimer where the ship shoots
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY(), ship.getCharacter().getRotate(), Projectile.ProjectileOrigin.SHIP);
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    //projectile movement
                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());

                    // Set canShoot to false and start a 1-second delay before enabling shooting again
                    canShoot = false;
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> canShoot = true));
                    timeline.play();
                }

                // UFO behavior
                if (now - lastSpawnTime >= 5_000_000_000L) {
                    if (!ufoSpawned) {
                        // if the UFO has not entered the screen yet, move it to the right edge of the screen
                        if (!ufoEntered) {
                            ufo.getCharacter().setTranslateX(WIDTH);
                            ufo.getCharacter().setTranslateY(new Random().nextInt(HEIGHT));
                            ufoEntered = true;
                        }

                        ufo.getCharacter().setVisible(true);
                        ufoSpawned = true;
                        lastVisibleTime = now; // Store the time when the UFO was last made visible
                    } else {
                        if (now - lastVisibleTime >= 5_000_000_000L) {
                            // Check if 5 seconds have passed since the UFO was last made visible
                            ufo.getCharacter().setVisible(false);
                            ufoSpawned = false;
                            lastSpawnTime = now;

                            // if the UFO has left the screen, move it to a random y-coordinate on the right edge of the screen
                            if (ufoEntered) {
                                ufo.getCharacter().setTranslateX(WIDTH);
                                ufo.getCharacter().setTranslateY(new Random().nextInt(HEIGHT));
                                ufoEntered = false;
                            }
                        }
                    }
                }
                if (ufoSpawned) {
                    // Adding random movement for the UFO
                    Random random = new Random();
                    // Update the UFO's movement direction every 1 second
                    if (now - lastUfoUpdateTime >= 1_000_000_000L) {
                        double randomDirection = random.nextDouble() * 2 * Math.PI;
                        double ufoSpeed = 1.5; // adjust as needed
                        Point2D randomVector = new Point2D(Math.cos(randomDirection), Math.sin(randomDirection)).normalize().multiply(ufoSpeed);
                        ufo.setMovement(randomVector);
                        lastUfoUpdateTime = now;
                    }

                    // Make the UFO shoot projectiles if it's alive and canShootUFO is true
                    if (ufo.isAlive() && canShootUFO) {
                        // Create a new UFO projectile
                        double angle = Math.toDegrees(ufo.calculateAngleBetweenUFOAndShip(ship));
                        Projectile ufoProjectile = new Projectile((int) ufo.getCharacter().getTranslateX(), (int) ufo.getCharacter().getTranslateY(), angle, Projectile.ProjectileOrigin.UFO, Color.GREEN);
                        // Set the fill color to green
                        ufoProjectile.getCharacter().setFill(Color.GREEN);

                        // Set the rotation of the UFO projectile
                        double ufoToShipAngle = Math.toDegrees(ufo.calculateAngleBetweenUFOAndShip(ship));

                        ufoProjectile.getCharacter().setRotate(angle);

                        ufoProjectiles.add(ufoProjectile);

                        // Projectile movement
                        ufoProjectile.accelerate();
                        ufoProjectile.setMovement(ufoProjectile.getMovement().normalize().multiply(2));

                        pane.getChildren().add(ufoProjectile.getCharacter());

                        // Set canShootUFO to false and start a 1.5-second delay before enabling shooting again
                        canShootUFO = false;
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> canShootUFO = true));
                        timeline.play();
                    }
                    ufo.move(); // Update the UFO's position
                }


                ufoProjectiles.forEach(projectile -> {
                    projectile.move();
                });


                // Collision detection between the UFO projectiles and the ship
                ufoProjectiles.forEach(projectile -> {
                    if (ship.getCharacter().getBoundsInParent().intersects(projectile.getCharacter().getBoundsInParent())) {
                        ship.death();
                        healthText.setText("Lives: " + ls.decrementAndGet());
                        if (ship.health > 0) {
                            ship.alive = true;
                            ship.movement = new Point2D(0, 0);
                            ship.respawning();
                        } else {

                            //writes high score

                            //hiscore.put(name, pts);
                            stop();

                            //game over scene switch
                            try {
                                hs.setpts(pts);
                                hs.start(GameOverScreen.classStage);
                                stage.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } // reduce ship health
                        projectile.setAlive(false);
                    }
                });

                // Collision detection between the player's projectiles and the UFO
                projectiles.forEach(projectile -> {
                    if (ufoSpawned && ufo.isAlive() && projectile.getOrigin() == Projectile.ProjectileOrigin.SHIP && ufo.getCharacter().getBoundsInParent().intersects(projectile.getCharacter().getBoundsInParent())) {
                        // Increase player's score by 5,000 points
                        points.setText("Points: " + pts.addAndGet(5000));

                        // Destroy the UFO
                        ufo.setAlive(false);
                        ufo.getCharacter().setVisible(false);
                        ufoSpawned = false;

                        // Remove the projectile
                        projectile.setAlive(false);
                    }
                });


                // remove projectiles that are off the screen
                projectiles.removeIf(projectile -> {
                    if (projectile.getCharacter().getTranslateX() < 0 ||
                            projectile.getCharacter().getTranslateX() > AsteroidsApplication.WIDTH ||
                            projectile.getCharacter().getTranslateY() < 0 ||
                            projectile.getCharacter().getTranslateY() > AsteroidsApplication.HEIGHT) {
                        pane.getChildren().remove(projectile.getCharacter());


                        return true;
                    }

                    return false;
                });

                // Existing UFO collision code
                if (ufoSpawned && ufo.getCharacter().getBoundsInParent().intersects(ship.getCharacter().getBoundsInParent())) {
                    ship.health--; // Reduce ship health by 1
                    healthText.setText("Lives: " + ls.decrementAndGet());
                    if (ship.health <= 0) {
                        // If the ship's health is 0 or less, end the game

                        // Game over scene switch
                        try {
                            //popup box asking for name
                            Platform.runLater(() -> {
                                stop();
                                TextInputDialog dialog = new TextInputDialog();
                                dialog.setTitle("Enter name here: ");
                                dialog.showAndWait().ifPresent(string -> setName(string));

                                //write name and points to hashmap
                                map.put(name, pts);
                                File file = new File(outputpath);
                                BufferedWriter bf = null;
                                try {
                                    bf = new BufferedWriter(new FileWriter(file));
                                    for (Map.Entry<String, AtomicInteger> entry:
                                            map.entrySet()) {
                                        bf.write(entry.getKey() + ":" + entry.getValue());
                                        bf.newLine();
                                    }
                                    bf.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                //high score screen
                                hs.setpts(pts);
                                hs.start(HighScore.classStage);
                                stage.close();
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        // If the ship's health is still greater than 0, respawn the ship
                        ship.movement = new Point2D(0, 0);
                        ship.respawning();
                    }
                }


                // ensure that there are no more than three projectiles on the screen
                if (projectiles.size() > 3) {
                    projectiles.subList(0, projectiles.size() - 3).clear();
                }
                //controlling the effect of projectiles: removing asteroids from the list
                List<Asteroid> newAsteroids = new ArrayList<>();
                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if(projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            points.setText("Points: " + pts.addAndGet(asteroid.getPoints()));
                            if (!asteroid.getSize().equals("small")) {
                                newAsteroids.add(asteroid.createSmallerAsteroid());
                                newAsteroids.add(asteroid.createSmallerAsteroid());
                            }
                            asteroid.setAlive(false);
                        }
                    });
                });

                // Add new asteroids to game
                for (Asteroid asteroid : newAsteroids) {
                    asteroids.add(asteroid);
                    pane.getChildren().add(asteroid.getCharacter());
                }

                //managing removing projectiles of the screen
                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));
                // level part
                while (asteroids.size() == 0) {
                    for (int i = 0; i < level+1; i++) {
                        Random rnd = new Random();
                        Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
                        asteroids.add(asteroid);
                    }
                    asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
                    level++;
                    levelText.setText("Level: " + level);
                }
                ship.move();
                //adding asteroid movement
                //collision = stop animation add-on
                asteroids.forEach(asteroid -> asteroid.move());
                //projectile movement
                projectiles.forEach(projectile -> projectile.move());

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        asteroid.setAlive(false);
                        ship.death();
                        healthText.setText("Lives: " + ls.decrementAndGet());
                        if (ship.health >0) {
                            ship.alive = true;
                            ship.movement = new Point2D(0, 0);
                            ship.respawning();
                        } else {
                            if (ship.health <= 0) {
                                // If the ship's health is 0 or less, end the game


                                // Game over scene switch
                                try {
                                    //popup box asking for name
                                    Platform.runLater(() -> {
                                        stop();
                                        TextInputDialog dialog = new TextInputDialog();
                                        dialog.setTitle("Enter name here: ");
                                        dialog.showAndWait().ifPresent(string -> setName(string));

                                        //write name and points to hashmap
                                        map.put(name, pts);
                                        File file = new File(outputpath);
                                        BufferedWriter bf = null;
                                        try {
                                            bf = new BufferedWriter(new FileWriter(file));
                                            for (Map.Entry<String, AtomicInteger> entry:
                                            map.entrySet()) {
                                                bf.write(entry.getKey() + ":" + entry.getValue());
                                                bf.newLine();
                                            }
                                            bf.flush();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        //high score screen
                                        hs.setpts(pts);
                                        hs.start(HighScore.classStage);
                                        stage.close();
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                });

            }
        }.start();

        stage.setTitle("Asteroids Game!");
        stage.setScene(scene);
        stage.show();

        //assigns stage variable for call in startscreen
        classStage = stage;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
