package com.example.asteroidsfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Rocketship implements Elements{
    int[] position = new int[]{450, 350, 0};
    Polygon ship;

    public Rocketship(){
        // Rocket
        ship = new Polygon(-5, -10, 25, 0, -5, 10);
        ship.setTranslateX(position[0]);
        ship.setTranslateY(position[1]);
        ship.setRotate(position[2]);
        ship.setStroke(Color.WHITE);
    }

    public Polygon getElement(){
        return ship;
    }

    public void turnLeft(){
        position[2] -= 4;
        ship.setRotate(position[2]);
    }

    public void turnRight(){
        position[2] += 4;
        ship.setRotate(position[2]);
    }

    @Override
    public int[] getPosition() {
        return position;
    }

}
