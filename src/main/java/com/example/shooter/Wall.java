package com.example.shooter;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Wall extends Group {
    public Wall(double x, double y, double angle) {
        Polygon shield = new Polygon(-45.0, -5.0, -45.0, 5.0, 45.0, 5.0, 45.0, -5.0);
        shield.setFill(Color.BROWN);
        super.getChildren().add(shield);
        this.setTranslateX(x + 375.0);
        this.setTranslateY(y + 375.0);
        this.setRotate(angle);
        this.setVisible(false);
    }

    public boolean handleCollision(Bullet bullet) {
        Bounds bounds = bullet.getBoundsInParent();
        return this.getBoundsInLocal().intersects(this.parentToLocal(bounds));
    }

    public void toggle() {
        this.setVisible(!this.isVisible());
    }

}
