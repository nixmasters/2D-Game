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

public class Shield extends Group {
    private double radius;

    private Translate position;




    public Shield ( double radius, Translate position) {
        this.radius   = radius;
        this.position = position;


        //final double gunWidth = 0.3 * radius;
        //final double gunHeight = 2 * radius;

        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{
                -10.0,0.0,
                -5.0,-10.0,
                5.0,-10.0,
                10.0,0.0,
                5.0,10.0,
                -5.0,10.0
        });

        hexagon.setFill(Color.BLUE);
        hexagon.setStroke(Color.LIGHTBLUE);
        //hexagon.setStrokeWidth(2);
        //hexagon.getBaselineOffset();




        super.getChildren ( ).addAll ( hexagon );

        super.getTransforms ( ).add ( position );
    }


    public double getRadius() {
        return this.radius;
    }

    public boolean isPickedUp(Player player) {
        return this.getBoundsInParent().intersects(player.getBoundsInParent());
    }

    public void PickUpEffect(Player player) {
        player.addShield();
        Thread shieldTimer = new Thread(()->{
            try{

                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            player.removeShield();
        });
        shieldTimer.start();
    }
}
