package com.asteroids.javaasteroidsgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
//shooting projectiles
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Game extends Application {
    //setting the width and height of the screen

    //setting the rotation angle for the on click event
    private double rotationAngle = 0.0;
    //size of screen
    public static int WIDTH = 950;
    public static int HEIGHT = 700;
    //declaring the projectile list and leaving it empty at the beginning
    List<Projectile> projectiles = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();

    //movement variable
    Point2D movement = new Point2D(1, 0);
    //variable for controlling the shooting of projectiles
    private boolean canShoot = true;

    StartScreen startScreen = new StartScreen();

    //never mind on the hashmap
    //--------------------------
    //Let's change the handling of keyboard event, such that we keep a record of pressed keys.
    // This can be done, for example, using a hash table.
    // The hash table contains the KeyCode object,
    // i.e. the object representing the key, as the key and a Boolean variable as the value.
    //Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        //new ship from its own class
        //setting the position to center
        Ship ship = new Ship(WIDTH, HEIGHT);
        ship.setHealth(3);
        //ship.setFill(Color.WHITE);
        //adding the asteroids as a list
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }

        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        // asteroid movement
        for (Asteroid asteroid : asteroids) {
            asteroid.turnRight();
            asteroid.turnRight();
            asteroid.accelerate();
            asteroid.accelerate();
        }


        Scene scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        //rotating the ship
        //creating an animation timer that needs a pressedKeys hashmap to smooth the animation
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        //key pressing set up
        //---------------------------------------------------------------
        //getting the pressed keys from the hashmap for faster/smoother animation
        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });


        // add the animation timer here
        new AnimationTimer() {

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
                    //setting the number of projectiles to 3
                    // we shoot
                    Projectile projectile = new Projectile((int) (ship.getCharacter().getTranslateX() + Math.cos(Math.toRadians(ship.getCharacter().getRotate())) * 30),
                            (int) (ship.getCharacter().getTranslateY() + Math.sin(Math.toRadians(ship.getCharacter().getRotate())) * 30));

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

// remove projectiles that are off the screen
                projectiles.removeIf(projectile -> {
                    if (projectile.getCharacter().getTranslateX() < 0 ||
                            projectile.getCharacter().getTranslateX() > WIDTH ||
                            projectile.getCharacter().getTranslateY() < 0 ||
                            projectile.getCharacter().getTranslateY() > HEIGHT) {
                        pane.getChildren().remove(projectile.getCharacter());


                        return true;
                    }
                    return false;
                });

// ensure that there are no more than three projectiles on the screen
                if (projectiles.size() > 3) {
                    projectiles.subList(0, projectiles.size() - 3).clear();
                }
                //controlling the effect of projectiles: removing asteroids from the list
                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if(projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                });

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
                        ship.death();
                        if (ship.health >0){
                            ship.alive = true;
                            ship.movement = new Point2D(0, 0);
                            ship.respawning();}
                        else{
                            stop();
                        }
                    }
                });

            }
        }.start();

        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
