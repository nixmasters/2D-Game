package com.example.shooter;

import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class FieldRect extends Rectangle implements FieldInterface {
    private Translate position;
    public FieldRect ( double w, double h, Translate position ) {
        super ( w,h );

        this.position = position;

        super.getTransforms ( ).add ( position );
    }

    public double getLength(){
        return super.getWidth();
    }

    public Translate getPosition ( ) {
        return this.position;
    }
}
