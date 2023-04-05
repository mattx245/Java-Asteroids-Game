module com.asteroids.javaasteroidsgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asteroids.javaasteroidsgame to javafx.fxml;
    exports com.asteroids.javaasteroidsgame;
}