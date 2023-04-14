package com.example.asteroidsfx;

package com.example.againasteroids;

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
    public Ship(int x, int y) {

        super(new Polygon( -20, 20,
                0, -20,
                20, 20,
                10, 20,
                0, 0,
                -10, 20), x, y);


    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public void death() {
        this.health = this.health-1;
    }
}
