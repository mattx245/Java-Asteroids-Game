import javafx.scene.shape.Polygon;

public class Ship extends Character {

    public Ship(int x, int y) {
        super(new Polygon(0,0,-10,20,10,20), x, y);
    }
}
