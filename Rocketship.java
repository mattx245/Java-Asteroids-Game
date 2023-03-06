package com.example.asteroidsfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Rocketship implements Elements{
    int[] position = new int[2];
    Polygon ship;

    public Rocketship(){
        // Rocket
        ship = new Polygon(-5, -10, 25, 0, -5, 10);
        ship.setTranslateX(450);
        ship.setTranslateY(350);
        ship.setStroke(Color.WHITE);
    }

    public Polygon getElement(){
        return ship;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public void setPosition(int X, int Y){
        position[0] = X;
        position[1] = Y;
    }
}
