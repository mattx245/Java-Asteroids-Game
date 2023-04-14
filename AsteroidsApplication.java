package com.asteroids.javaasteroidsgame;

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

//    public AtomicInteger getpoints(){
//        return pts;
//    }


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
        Ship ship = new Ship(WIDTH, HEIGHT);
        ship.setHealth(3);
        //adding the asteroids as a list
        List<Asteroid> asteroids = new ArrayList<>();
        // List of projectiles for UFO bullets
        List<Projectile> ufoProjectiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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

            private long lastVisibleTime = 0;
            private boolean ufoSpawned = false;

            @Override
            public void handle(long now) {
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }
                if(pressedKeys.getOrDefault(KeyCode.DOWN, false)) {
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
                    double randomDirection = random.nextDouble() * 2 * Math.PI;
                    double ufoSpeed = 2.0; // adjust as needed
                    Point2D randomVector = new Point2D(Math.cos(randomDirection), Math.sin(randomDirection)).normalize().multiply(ufoSpeed);
                    ufo.setMovement(ufo.getMovement().add(randomVector).normalize().multiply(5)); // increase speed

                    ufo.move(); // Add this line to update the UFO's position

                    // Make the UFO shoot projectiles if it's alive and canShootUFO is true
                    if (ufo.isAlive() && canShootUFO) {
                        // Create a new UFO projectile
                        Projectile ufoProjectile = new Projectile((int) ufo.getCharacter().getTranslateX(), (int) ufo.getCharacter().getTranslateY(), Projectile.ProjectileOrigin.UFO);
                        // Set the fill color to green
                        ufoProjectile.getCharacter().setFill(Color.GREEN);

                        // Set the rotation of the UFO projectile
                        double angle = ufo.calculateAngleBetweenUFOAndShip(ship);
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
                }


                ufoProjectiles.forEach(projectile -> {
                    ufo.move(); // Update the UFO's position
                });

                // Collision detection between the UFO projectiles and the ship
                ufoProjectiles.forEach(projectile -> {
                    if (ship.getCharacter().getBoundsInParent().intersects(projectile.getCharacter().getBoundsInParent())) {
                        ship.setHealth(ship.getHealth() - 1); // reduce ship health
                        projectile.setAlive(false);

                        // Check if the ship has no more health left
                        if (ship.getHealth() <= 0) {
                            ship.setAlive(false);

                            // End the game and show the game over screen
                            stop();
                            try {
                                hs.setpts(pts);
                                hs.start(GameOverScreen.classStage);
                                stage.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
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

                // Collision detection between the UFO and the ship
                if (ufoSpawned && ufo.getCharacter().getBoundsInParent().intersects(ship.getCharacter().getBoundsInParent())) {
                    ship.setAlive(false);

                    // End the game and show the game over screen
                    stop();
                    try {
                        hs.setpts(pts);
                        hs.start(GameOverScreen.classStage);
                        stage.close();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
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
                            if (!asteroid.getSize().equals("small")) {
                                newAsteroids.add(asteroid.createSmallerAsteroid());
                                newAsteroids.add(asteroid.createSmallerAsteroid());
                            }
                            asteroid.setAlive(false);
                        }
                    });
                    if(!projectile.isAlive()) {
                        points.setText("Points: " + pts.addAndGet(1000));
                    }
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
                        if (ship.health >0) {
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
