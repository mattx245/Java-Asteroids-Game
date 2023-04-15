module com.asteroids.javaasteroidsgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.asteroids.javaasteroidsgame to javafx.fxml;
    exports com.asteroids.javaasteroidsgame;
}