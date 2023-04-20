package com.asteroids.javaasteroidsgame;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
//imports for the animation of turning
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import java.util.Map;
//movement imports
import javafx.geometry.Point2D;
//array of asteroids imports
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
//projectiles removing asteroids
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
//shooting projectiles
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


//base created following the mooc.fi tutorial loosely: https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
public class AsteroidsApplication extends Application {

    //size of screen
    public final static int WIDTH = 950;
    public final static int HEIGHT = 700;
    //declaring the projectile list and leaving it empty at the beginning
    List<Projectile> projectiles = new ArrayList<>();

    //variable for controlling the shooting of projectiles
    private boolean canShoot = true;
    private boolean canShootUFO = true;

    // create a boolean variable to keep track of whether the UFO has entered the screen or not
    private boolean ufoEntered = false;


    //creates variable for startscreen
    static Stage classStage = new Stage();
    //game over screen switcher

    //point int
    private AtomicInteger pts = new AtomicInteger();

    GameOverScreen go = new GameOverScreen(pts.get());

    //high score hashmap nd filepath
    final static String outputpath = "score.txt";
    Path path = Files.createTempFile("score", ".txt");
    boolean exists = Files.exists(path);
    HashMap<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();

    //name for high score and setter
    String name;

    public AsteroidsApplication() throws IOException {
    }

    public void setName (String nm)  {
        this.name = nm;
    }
    private Integer respawnSafe = 0;
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
        pane.getChildren().add(ufo.getCharacter()); // Add the UFO character to the Pane
        ufo.getCharacter().setVisible(false); // Set the UFO visibility to false initially

        // Sounds
        Sounds sounds = new Sounds();

        //new ship from its own class
        //setting the position to center
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2, sounds);
        ship.setHealth(3);

        //Face ship upward
        ship.character.setRotate(-90);

        //adding the asteroids as a list
        List<Asteroid> asteroids = new ArrayList<>();

        // List of projectiles for UFO bullets
        List<Projectile> ufoProjectiles = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), sounds);
            asteroids.add(asteroid);
        }

        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        Scene scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        points.setFill(Color.WHITE);

        //---------------------------------------------------------------------------------------
        //getting the pressed keys from the hashmap for faster/smoother animation
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        //---------------------------------------------------------------------------------------
        // FallingLines:
        List<FallingLines> fallingLines = new ArrayList<>();

        //---------------------------------------------------------------------------------------

        new AnimationTimer() {

            private long lastSpawnTime = System.nanoTime();

            private long lastUfoUpdateTime = 0;

            private long lastVisibleTime = 0;
            private boolean ufoSpawned = false;

            @Override
            public void handle(long now) {
                //key controls
                //---------------------------------------------------------------------------------------
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    sounds.playSound("thrust");
                    ship.accelerate();
                }
                if (pressedKeys.getOrDefault(KeyCode.J, false)) {
                    respawnSafe = ship.hyperspaceJump();
                }
                /* not in the specification
                if (pressedKeys.getOrDefault(KeyCode.DOWN, false)) {
                    sounds.playSound("thrust");
                    ship.reverse();
                }*/

                //---------------------------------------------------------------------------------------

                //adding projectiles
                // shoot only if there are less than six projectiles on the screen
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 6 && canShoot) {
                    // Inside the AnimationTimer where the ship shoots
                    sounds.playSound("fire");
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY(), ship.getCharacter().getRotate(), Projectile.ProjectileOrigin.SHIP);
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    //projectile movement
                    projectile.setMovement(ship.getMovement());
                    for(int i = 0; i < 100; i++) {
                        projectile.accelerate();
                    }

                    pane.getChildren().add(projectile.getCharacter());

                    // Set canShoot to false and start a 0.3 -second delay before enabling shooting again
                    canShoot = false;
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> canShoot = true));
                    timeline.play();
                }


                //---------------------------------------------------------------------------------------

                // UFO behavior
                if (now - lastSpawnTime >= 5_000_000_000L) {
                    if (!ufoSpawned) {
                        sounds.playSound("saucer");
                        ufo.getCharacter().setTranslateX(WIDTH);
                        ufo.getCharacter().setTranslateY(new Random().nextInt(HEIGHT));

                        // Set UFO's alive status to true
                        ufo.setAlive(true);

                        ufo.getCharacter().setVisible(true);
                        ufoSpawned = true;
                        lastVisibleTime = now; // Store the time when the UFO was last made visible
                    } else {
                        // Check if 5 seconds have passed since the UFO was last made visible
                        ufoSpawned = true;
                        lastSpawnTime = now;

                        // if the UFO has left the screen, move it to a random y-coordinate on the right edge of the screen
                        if (ufoEntered) {
                            pane.getChildren().remove(ufo.getCharacter()); // Remove the UFO character from the Pane
                            ufo.getCharacter().setTranslateX(WIDTH);
                            ufo.getCharacter().setTranslateY(new Random().nextInt(HEIGHT));
                            ufoEntered = false;
                        }

                        if (!pane.getChildren().contains(ufo.getCharacter())) {
                            pane.getChildren().add(ufo.getCharacter()); // Add the UFO character to the Pane
                        }

                        ufo.setAlive(true);
                        ufo.setVisibility(true); // Make the UFO visible
                        sounds.playSound("saucer");
                    }
                }

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

                // Make the UFO shoot projectiles if it's alive, spawned and canShootUFO is true
                if (ufo.isAlive() && canShootUFO && ufoSpawned) {
                    // Create a new UFO projectile
                    double angle = ufo.calculateAngleBetweenUFOAndShip(ship);
                    Projectile ufoProjectile = new Projectile((int) ufo.getCharacter().getTranslateX(), (int) ufo.getCharacter().getTranslateY(), angle, Projectile.ProjectileOrigin.UFO, Color.GREEN);
                    // Set the fill color to green
                    ufoProjectile.getCharacter().setFill(Color.GREEN);

                    // Play the UFO projectile shooting sound
                    sounds.playSound("beat");

                    // Set the rotation of the UFO projectile
                    ufoProjectile.getCharacter().setRotate(Math.toDegrees(angle));

                    ufoProjectiles.add(ufoProjectile);

                    // Projectile movement
                    ufoProjectile.accelerate();
                    ufoProjectile.setMovement(ufoProjectile.getMovement().normalize().multiply(4));

                    pane.getChildren().add(ufoProjectile.getCharacter());

                    // Set canShootUFO to false and start a 1.5-second delay before enabling shooting again
                    canShootUFO = false;
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> canShootUFO = true));
                    timeline.play();
                }

                ufo.move(); // Update the UFO's position


                ufoProjectiles.forEach(projectile -> {
                    projectile.move();
                });

                // Collision detection between the UFO's projectiles and the player's ship
                ufoProjectiles.forEach(ufoProjectile -> {
                    if (ufoProjectile.getOrigin() == Projectile.ProjectileOrigin.UFO && ship.getCharacter().getBoundsInParent().intersects(ufoProjectile.getCharacter().getBoundsInParent())) {
                        // Handle the collision: decrease player's lives, end the game, etc.
                        sounds.playSound("large"); // Explosion sound when projectile and ship collide

                        shipCollides();

                        // Remove the UFO projectile from the scene and the ufoProjectiles list
                        pane.getChildren().remove(ufoProjectile.getCharacter());
                        ufoProjectile.setAlive(false);
                    }
                });

                // Remove dead UFO projectiles
                ufoProjectiles.removeIf(ufoProjectile -> !ufoProjectile.isAlive());


                // Remove projectiles that collided with the UFO
                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                // Collision detection between the player's projectiles and the UFO
                projectiles.forEach(projectile -> {
                    if (ufoSpawned && ufo.isAlive() && projectile.getOrigin() == Projectile.ProjectileOrigin.SHIP && ufo.getCharacter().getBoundsInParent().intersects(projectile.getCharacter().getBoundsInParent())) {
                        // Increase player's score by 300 points
                        points.setText("Points: " + pts.addAndGet(300));

                        ufoDied(now);

                        // Remove the projectile
                        projectile.setAlive(false);
                    }
                });


                // UFO collision code
                if (ufoSpawned && ufo.getCharacter().getBoundsInParent().intersects(ship.getCharacter().getBoundsInParent()) && respawnSafe < 1) {
                    sounds.playSound("large"); // Explosion sound when ship and UFO collide
                    shipCollides();
                    ufoDied(now);
                }


                // ensure that there are no more than six projectiles on the screen
                if (projectiles.size() > 6) {
                    projectiles.subList(0, projectiles.size() - 6).clear();
                }

                //---------------------------------------------------------------------------------------

                List<Asteroid> newAsteroids = new ArrayList<>();
                // Add new asteroids to game
                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

                //---------------------------------------------------------------------------------------
                //controlling the effect of projectiles: removing asteroids from the list
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

                // level part
                //---------------------------------------------------------------------------------------
                while (asteroids.size() == 0) {
                    for (int i = 0; i < level+1; i++) {
                        Random rnd = new Random();
                        Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), sounds);
                        asteroids.add(asteroid);
                    }
                    asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
                    level++;
                    levelText.setText("Level: " + level);
                }

                //declaring movement
                //---------------------------------------------------------------------------------------
                ship.move();
                //adding asteroid movement
                //collision = stop animation add-on
                asteroids.forEach(asteroid -> asteroid.move());
                //projectile movement
                projectiles.forEach(projectile -> projectile.move());

                // falling apart of the asteroids and ship
                //---------------------------------------------------------------------------------------

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid) && respawnSafe < 1) {
                        asteroid.setAlive(false);
                        shipCollides();
                    }
                });


                fallingLines.forEach(fallingLine -> {
                    if (!pane.getChildren().contains(fallingLine.getCharacter())) {
                        pane.getChildren().add(fallingLine.getCharacter()); // Add the Line character to the Pane
                    }
                    fallingLine.move();
                    if(!fallingLine.isAlive()) {
                        pane.getChildren().remove(fallingLine.getCharacter());
                    }
                });

                // Remove "not alive" fallingLines
                fallingLines.removeAll(fallingLines.stream()
                        .filter(fallingLine -> !fallingLine.isAlive())
                        .collect(Collectors.toList()));

                // Reduce Safe respawn time
                respawnSafe--;
            }

            public void ufoDied(long now){
                // Destroy the UFO
                ufo.setAlive(false);
                ufo.getCharacter().setVisible(false);
                ufoSpawned = false;

                // Play the UFO explosion sound
                sounds.playSound("medium");
                // Remove the UFO from the screen
                pane.getChildren().remove(ufo.getCharacter());

                // Reset the lastSpawnTime
                lastSpawnTime = now;
            }
            public void shipCollides() {
                respawnSafe = ship.death(); // Reduce ship health by 1 // Returns -1 if ship died
                ship.fallingApart().forEach(fallingLine -> fallingLines.add(fallingLine));
                healthText.setText("Lives: " + ls.decrementAndGet());
                if (respawnSafe.equals(-1)) {
                    gameOver();
                }
            }
            private void gameOver() {
                try {
                    //popup box asking for name
                    Platform.runLater(() -> {
                        stop();
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Enter name here: ");
                        dialog.showAndWait().ifPresent(string -> setName(string));

                        //write name and points to hashmap
                        map.put(name, pts);
                        if (!exists) {
                            File file = new File(outputpath);
                        }
                        BufferedWriter bf = null;
                        try {
                            bf = new BufferedWriter(new FileWriter("score.txt", true));
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
                        try {
                            go.start(GameOverScreen.classStage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        //hs.start(HighScore.classStage);
                        stage.close();
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
