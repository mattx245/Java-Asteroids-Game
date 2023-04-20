package com.asteroids.javaasteroidsgame;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
//collision import
import javafx.scene.shape.Shape;
import java.util.List;
import java.util.ArrayList;

public abstract class Character {

    //declaring the characters are polygons
    public Polygon character;
    //movement variable
    public Point2D movement;
    //alive tracker variable
    public boolean alive;
    public List<Character> characters = new ArrayList<>();

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.alive = true;
        //this.character.setFill(Color.WHITE);

        this.movement = new Point2D(0, 0);
        characters.add(this);
    }

    public void setWhiteStroke() {
        getCharacter().setFill(Color.TRANSPARENT);
        getCharacter().setStroke(Color.WHITE);
        getCharacter().setStrokeWidth(2.0);
    }

    public Polygon getCharacter() {
        return character;
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);
    }

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());


        //making so the character stays on screen by reentering on the other side
        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + AsteroidsApplication.WIDTH);
        }

        if (this.character.getTranslateX() > AsteroidsApplication.WIDTH) {
            this.character.setTranslateX(this.character.getTranslateX() % AsteroidsApplication.WIDTH);
        }

        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + AsteroidsApplication.HEIGHT);
        }

        if (this.character.getTranslateY() > AsteroidsApplication.HEIGHT) {
            this.character.setTranslateY(this.character.getTranslateY() % AsteroidsApplication.HEIGHT);
        }
        //The size of the character is not taken into account, so its x- or y -coordinates can be outside of the screen,
        // even if part of the character stays visible.
        // We can —probably— solve this problem with the getBoundsInParent-method of the Node -class. (mooc.fi)
    }

    public void accelerate() {
        double angle = Math.toRadians(this.character.getRotate());
        double changeX = Math.cos(angle);
        double changeY = Math.sin(angle);

        changeX *= (8.0 - Math.abs(getMovement().getX()))/140;
        changeY *= (8.0 - Math.abs(getMovement().getY()))/140;


        this.movement = this.movement.add(changeX, changeY);
    }

    public void reverse() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= -(8.0 - Math.abs(getMovement().getX()))/140;
        changeY *= -(8.0 - Math.abs(getMovement().getY()))/140;

        this.movement = this.movement.add(changeX, changeY);
    }

    //collision method
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
    //adding the get and set movement methods, so we can control projectiles
    public Point2D getMovement() {
        return this.movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return this.alive;
    }

    
}
