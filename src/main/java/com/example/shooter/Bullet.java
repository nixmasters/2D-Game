package com.example.shooter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import static com.example.shooter.Constants.*;

public class Bullet extends Circle {
	private Translate position;
	private Point2D speed;
	private double factor;
	private int strength;


	public Bullet ( double radius, Translate position, Point2D speed, Color color , double factor ) {
		super ( radius, color );
		this.strength = 1;
		this.position = position;
		this.speed    = speed;
		super.getTransforms ( ).add ( this.position );
		this.factor = factor;
	}
	public Bullet(double radius, Translate position, Point2D speed){
		this(radius, position, speed, Color.ORANGE, 10.0D);
	}

	
	public boolean update ( long dns, double left, double right, double up, double down ) {
		double newX = this.position.getX ( ) + this.speed.getX ( ) * this.factor;
		double newY = this.position.getY ( ) + this.speed.getY ( ) * this.factor;
		
		this.position.setX ( newX );
		this.position.setY ( newY );
		
		double radius = super.getRadius ( );
		
		boolean isXOutOfBounds = newX <= ( left - radius ) || newX >= ( right + radius );
		boolean isYOutOfBounds = newY <= ( up - radius ) || newY >= ( down + radius );


		return isXOutOfBounds && isYOutOfBounds;

	}

	public int getStrength() {
		return this.strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void bounceOff(Player player) {
		if (player.handleCollision(this)) {
			if (player.isShielded()) {

				Point2D playerCenter = new Point2D(player.getBoundsInParent().getCenterX(), player.getBoundsInParent().getCenterY());
				Point2D bulletCenter = new Point2D(this.position.getX(), this.position.getY());
				Point2D direction = bulletCenter.subtract(playerCenter).normalize();


				this.speed = direction.multiply(0.5);


			} else {

				return;
			}
			// System.out.println("Old speed: " + this.speed);
			//        Point2D playerCenter = new Point2D(player.getBoundsInParent().getCenterX(), player.getBoundsInParent().getCenterY());
			//        System.out.println("Player center: " + playerCenter);
			//        Point2D bulletCenter = new Point2D(this.getBoundsInParent().getCenterX(), this.getBoundsInParent().getCenterY());
			//        System.out.println("Bullet center: " + bulletCenter);
			//        Point2D direction = bulletCenter.subtract(playerCenter).normalize();
			//        System.out.println("Direction: " + direction);
			//        this.speed = direction.multiply(0.125);
			//        System.out.println("New speed: " + this.speed);
		}
	}
}
