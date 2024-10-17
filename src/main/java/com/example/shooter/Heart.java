package com.example.shooter;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

public class Heart extends Group {
    private double radius;

    private Translate position;



    public Heart ( double radius, Translate position) {
        this.radius   = radius;
        this.position = position;

        //final double gunWidth = 0.3 * radius;
        //final double gunHeight = 2 * radius;

        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{
                0.0, -10.0,    // Top center
                15.0, -15.0,   // Upper right curve
                20.0, -5.0,    // Middle right curve
                10.0, 10.0,    // Bottom right curve
                0.0, 20.0,     // Bottom tip
                -10.0, 10.0,   // Bottom left curve
                -20.0, -5.0,   // Middle left curve
                -15.0, -15.0   // Upper left curve
        });

        hexagon.setFill(Color.RED);
        hexagon.setStroke(Color.PINK);
        //hexagon.setStrokeWidth(2);
        //hexagon.getBaselineOffset();




        super.getChildren ( ).addAll ( hexagon );

        super.getTransforms ( ).add ( position );
    }
    public double getRadius(){
        return this.radius;
    }

    public boolean isPickedUp(Player player) {
        return this.getBoundsInParent().intersects(player.getBoundsInParent());
    }

    public void updateHP(Player player) {
        if(player.getLives() == player.getMaxLives()){
            return;
        }
        player.updateLives(1);

    }
}
