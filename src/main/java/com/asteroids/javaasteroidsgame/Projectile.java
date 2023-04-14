package com.asteroids.javaasteroidsgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Projectile extends Character {
    public enum ProjectileOrigin {
        SHIP,
        UFO
    }

    public Projectile(int x, int y, double rotation, ProjectileOrigin origin) {
        super(new Polygon(-1, 1, 1, 1, 0, -2), x, y); // Small triangle shape
        this.origin = origin;
        this.setAlive(true);

        this.getCharacter().setFill(Color.YELLOW);
        this.getCharacter().setRotate(rotation);

        double radians = Math.toRadians(rotation);
        double offsetX = 20 * Math.cos(radians);
        double offsetY = 20 * Math.sin(radians);

        this.getCharacter().setTranslateX(x + offsetX);
        this.getCharacter().setTranslateY(y + offsetY);
    }
    private ProjectileOrigin origin;

    public Projectile(int x, int y, ProjectileOrigin origin) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        this.origin = origin;
    }

    public ProjectileOrigin getOrigin() {
        return origin;
    }
}
