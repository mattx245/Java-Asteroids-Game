package com.asteroids.javaasteroidsgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import java.lang.Math;

//Classes like Polygon and Circle inherit the Node class of JavaFx.
// The Node class has a variable rotate, which describes the rotation of the node in degrees.
// Turning any object inheriting the Node class is therefore quite straightforward —
// you just use the existing method setRotate.
// The method is given the amount to turn in degrees as its parameter.


import javafx.scene.shape.Polygon;

public class Ship extends Character {
    public int health;
    private Sounds sounds;
    public Ship(int x, int y, Sounds sounds) {

        super(new Polygon( 0, 20,
                8, -2,
                1, 0,
                -1, 0,
                -8, -2), x, y);
        setWhiteStroke();
        this.sounds = sounds;

    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public void death() {
        sounds.playSound("beat");
        this.health = this.health-1;
    }
    public boolean ship_collides() {
        for (Character character : characters) {
            if (character != this && character.collide(this)) {
                return true;
            }
        }
        return false;
    }

    public void hyperspaceJump() {
        boolean valid = false;
        int newX, newY;
        while (!valid){
            newX = (int)(Math.random() * 950);
            newY = (int)(Math.random() * 700);
            if ((!new Ship(newX,newY, sounds).ship_collides())&&(!new Ship(newX+40,newY, sounds).ship_collides())&&(!new Ship(newX-40,newY, sounds).ship_collides())&&(!new Ship(newX,newY-40, sounds).ship_collides())&&(!new Ship(newX,newY+40, sounds).ship_collides()) ){
                valid = true;
                this.movement = new Point2D(0, 0);
                // Set the position of the player's ship to the new location
                this.character.setTranslateX(newX);
                this.character.setTranslateY(newY);

            }
        }
    }
    public void respawning(){
        this.hyperspaceJump();
    }
}
