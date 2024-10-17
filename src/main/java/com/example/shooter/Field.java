package com.example.shooter;

import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

public class Field extends Circle implements FieldInterface {
	private Translate position;
	public Field ( double radius, Translate position ) {
		super ( radius );

		this.position = position;

		super.getTransforms ( ).add ( position );
	}

	public double getLength(){
		return super.getRadius();
	}
	
	public Translate getPosition ( ) {
		return this.position;
	}
}
