package com.asteroids.javaasteroidsgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

//Classes like Polygon and Circle inherit the Node class of JavaFx.
// The Node class has a variable rotate, which describes the rotation of the node in degrees.
// Turning any object inheriting the Node class is therefore quite straightforward —
// you just use the existing method setRotate.
// The method is given the amount to turn in degrees as its parameter.

public class Ship extends Character {
    private int health;
    private Sounds sounds;
    private int respawnSafe;
    public Ship(int x, int y, Sounds sounds) {
        super(new Polygon( 0, 1,
                -2, 8,
                20, 0,
                -2, -8,
                0, -1), x, y);

        setWhiteStroke();
        this.getCharacter().setFill(Color.BLACK);
        this.sounds = sounds;
        this.respawnSafe = 0;


    }
    // getter for health
    public int getHealth() {
        return this.health;
    }
    // setter for health
    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public int death() {
        sounds.playSound("beat");
        this.health = this.health - 1;

        if(this.health >= 1) {
            return this.respawning();
        } else {
            return -1;
        }
    }
    // judge if there is a collision with other objects except the ship itself
    public boolean ship_collides() {
        for (Character character : characters) {
            if (character != this && character.collide(this)) {
                return true;
            }
        }
        return false;
    }

    public int hyperspaceJump() {
        boolean valid = false;
        int newX, newY;
        this.respawnSafe = 160;
        while (!valid){
            newX = (int)(Math.random() * 950);
            newY = (int)(Math.random() * 700);
            // judge if the ship in the new location will be within 40 range of other objects
            if ((!new Ship(newX,newY, sounds).ship_collides())&&(!new Ship(newX+40,newY, sounds).ship_collides())&&(!new Ship(newX-40,newY, sounds).ship_collides())&&(!new Ship(newX,newY-40, sounds).ship_collides())&&(!new Ship(newX,newY+40, sounds).ship_collides()) ){
                valid = true;
                this.setMovement(new Point2D(0, 0));
                // Set the position of the player's ship to the new location
                this.character.setTranslateX(newX);
                this.character.setTranslateY(newY);

            }
        }
        return this.respawnSafe;
    }
    public int respawning(){
        this.hyperspaceJump();
        this.setMovement(new Point2D(0, 0));
        return this.respawnSafe;
    }

    @Override
    public void move() {
        this.respawnSafe--;
        if(this.respawnSafe > 1) {
            if(this.respawnSafe % 20 == 0) {
                this.getCharacter().setStroke(Color.BLACK);
            }
            else if(this.respawnSafe % 10 == 0) {
                this.getCharacter().setStroke(Color.WHITE);
            }
        }
        super.move();
    }

    public List<FallingLines> fallingApart(){
        List<FallingLines> fallingLines = new ArrayList<>();
        if(this.respawnSafe < 1) {
            int x_ship = (int) this.getCharacter().getTranslateX();
            int y_ship = (int) this.getCharacter().getTranslateY();
            Point2D movement_ship = this.getMovement();


            // Line 1
            fallingLines.add(new FallingLines(new Polygon(-2, 8, 20, 0), x_ship + 2, y_ship - 8, movement_ship));
            // Line 2
            fallingLines.add(new FallingLines(new Polygon(-2, -8, 20, 0), x_ship + 2, y_ship + 8, movement_ship));
            // Line 3
            fallingLines.add(new FallingLines(new Polygon(-2, -8, -2, 8), x_ship + 20, y_ship, movement_ship));
        }
        return fallingLines;
    }

}
