package com.asteroids.javaasteroidsgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    private int shooting_distance;

    public enum ProjectileOrigin {
        SHIP,
        UFO
    }

    public Projectile(int x, int y, double rotation, ProjectileOrigin origin) {
        super(origin == ProjectileOrigin.SHIP ? new Polygon(2, -2, 2, 2, -2, 2, -2, -2) : new Polygon(-2, 2, 2, 2, 0, -4), x, y); // Larger triangle shape for UFO projectiles
        //-1, 1, 1, 1, 0, -2
        this.origin = origin;
        this.setAlive(true);

        this.getCharacter().setFill(Color.YELLOW);
        this.getCharacter().setRotate(rotation);

        double radians = Math.toRadians(rotation);
        double offsetX = 1 * Math.cos(radians);
        double offsetY = 1 * Math.sin(radians);

        this.getCharacter().setTranslateX(x + offsetX);
        this.getCharacter().setTranslateY(y + offsetY);

        this.shooting_distance = 100;
    }

    public Projectile(int x, int y, double rotation, ProjectileOrigin origin, Color color) {
        this(x, y, rotation, origin);
        this.getCharacter().setFill(color);
    }

    private ProjectileOrigin origin;


    public ProjectileOrigin getOrigin() {
        return origin;
    }

    @Override
    public void move() {
        if (shooting_distance < 1) {
            this.setAlive(false);
            this.getCharacter().setVisible(false); // Hide the projectile when it's no longer alive
        } else {
            super.move();
            shooting_distance -= 1;
        }
    }
}
