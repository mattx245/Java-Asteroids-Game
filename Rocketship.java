package com.example.asteroidsfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Rocketship implements Elements{
    int[] position = new int[2];
    position =[450, 350]
    Polygon ship;

    public Rocketship(){
        // Rocket
        ship = new Polygon(-5, -10, 25, 0, -5, 10);
        ship.setTranslateX(position[0]);
        ship.setTranslateY(position[1]);
        ship.setStroke(Color.WHITE);
    }

    public Polygon getElement(){
        return ship;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

}
