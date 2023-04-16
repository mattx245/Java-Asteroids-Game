package com.asteroids.javaasteroidsgame;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.Random;

public class FallingLines extends Character {
    private int life;
    private static final Random RANDOM = new Random();
    public FallingLines(Polygon polygon, int x, int y, Point2D movement) {
        super(polygon, x, y);
        life = 40;
        this.getCharacter().setStroke(Color.WHITE);
        this.getCharacter().setStrokeWidth(2);
        Point2D movement_line = new Point2D(movement.getX() * 0.5 + 0.8 * RANDOM.nextDouble(), movement.getY() * 0.5 + 0.8 * RANDOM.nextDouble());
        this.setMovement(movement_line);
    }

    public void move() {
        if (life > 0) {
            life--;
            this.getCharacter().setStroke(Color.rgb(255, 255, 255, life / 40.0));
        } else {
            setAlive(false);
        }
        super.move();
    }
}
