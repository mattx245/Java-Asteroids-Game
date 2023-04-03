package com.example.asteroidsfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
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
    public static int WIDTH = 600;
    public static int HEIGHT = 400;
    //declaring the projectile list and leaving it empty at the beginning
    List<Projectile> projectiles = new ArrayList<>();

    //movement variable
    Point2D movement = new Point2D(1, 0);
    //variable for controlling the shooting of projectiles
    private boolean canShoot = true;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        //old ship
        //Polygon ship = new Polygon(0,0,-10,20,10,20);
        //ship.setTranslateX(300);
        //ship.setTranslateY(200);
        //ship.setRotate(rotationAngle);
        //ship.setRotate(-90); // rotating the ship 90 degrees counterclockwise

        //new ship from its own class
        //setting the position to center
        Ship ship = new Ship(300, 200);
        //adding the asteroids as a list
        List<Asteroid> asteroids = new ArrayList<>();
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
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
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
                            projectile.getCharacter().getTranslateX() > AsteroidsApplication.WIDTH ||
                            projectile.getCharacter().getTranslateY() < 0 ||
                            projectile.getCharacter().getTranslateY() > AsteroidsApplication.HEIGHT) {
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
                        stop();
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
