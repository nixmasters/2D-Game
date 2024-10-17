package com.example.shooter;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

public class Coin extends Circle {
    private Translate position;


    public Coin (double radius, Translate position, Color color  ) {
        super ( radius, color );

        this.position = position;
        super.getTransforms ( ).add ( this.position );

    }


    public boolean isPickedUp(Player player) {
        return this.getBoundsInParent().intersects(player.getBoundsInParent());
    }

    public void PickUpEffect(Player player) {

    }
}
