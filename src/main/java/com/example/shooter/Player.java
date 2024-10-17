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

public class Player extends Group {
	private double radius;
	private double mouseX;
	private double mouseY;
	private Translate position;
	private Rotate    rotate;
	private int lives;

	private int MaxLives ;
	private double factorMove;
	private Circle shield;
	private int NumOfShields = 0 ;

	private double brzinaKretanja;

	public Player ( double radius, Translate position, double gunWidth, double gunHeight, double brzinaKretanja, int lives,
					double factorMove) {
		this.radius   = radius;
		this.position = position;
		this.brzinaKretanja = brzinaKretanja;
		this.factorMove = factorMove;
		this.lives = lives;
		this.MaxLives = lives;
		this.shield = new Circle(radius + 5.0);
		this.shield.setFill(Color.TRANSPARENT);
		this.shield.setStroke(Color.CYAN);
		this.shield.setStrokeWidth(3.0);
		this.shield.setVisible(false);

		this.rotate = new Rotate ( );
		//final double gunWidth = 0.3 * radius;
		//final double gunHeight = 2 * radius;
		Rectangle gun = new Rectangle ( gunWidth, gunHeight );
		Polygon hexagon = new Polygon();
		hexagon.getPoints().addAll(new Double[]{
				-20.0,0.0,
				-10.0,-20.0,
				10.0,-20.0,
				20.0,0.0,
				10.0,20.0,
				-10.0,20.0

		});

		hexagon.setFill(Color.YELLOW);
		hexagon.setStroke(Color.BLACK);
		//hexagon.setStrokeWidth(2);
		//hexagon.getBaselineOffset();


		gun.setFill ( Color.PURPLE );
		gun.getTransforms ( ).addAll (
				this.rotate,
				new Translate ( -gunWidth / 2, 0 )
		);
		
		super.getChildren ( ).addAll ( hexagon, gun , shield );
		
		super.getTransforms ( ).add ( position );
	}
	
	private void updateGun ( ) {
		Point2D vector = new Point2D (
				this.mouseX - this.position.getX ( ),
				this.mouseY - this.position.getY ( )
		).normalize ( );
		
		double angle = -Math.signum ( vector.getX ( ) ) * vector.angle ( 0, 1 );
		this.rotate.setAngle ( angle );
	}
	
	public void move ( double stepX, double stepY, FieldInterface field ) {
		double newX = this.position.getX ( ) + stepX * factorMove;
		double newY = this.position.getY ( ) + stepY * factorMove;
		
		double fieldX = field.getPosition ( ).getX ( );
		double fieldY = field.getPosition ( ).getY ( );
		boolean sudar=false;

		if(field instanceof Field){
			double dx = newX - fieldX;
			double dy = newY - fieldY;
			double dr = field.getLength() - this.radius * 0.5;

			double distanceSquared = dx * dx + dy * dy;
			sudar = distanceSquared > dr*dr;
		}else if(field instanceof FieldRect){
			if(newX < fieldX || newX > fieldX+((FieldRect) field).getWidth() ||
					newY<fieldY || newY > fieldY + ((FieldRect) field).getHeight())
				sudar = true;
		}
		
		if ( !sudar ) {
			this.position.setX ( newX );
			this.position.setY ( newY );
			
			this.updateGun ( );
		}
	}
	
	public Bullet handleMouseEvent ( MouseEvent mouseEvent ) {
		this.mouseX = mouseEvent.getX ( );
		this.mouseY = mouseEvent.getY ( );
		
		this.updateGun ( );
		
		Bullet bullet = null;
		
		if ( mouseEvent.getEventType ( ).equals ( MouseEvent.MOUSE_PRESSED )) {
			Point2D speed = new Point2D (
					this.mouseX - this.position.getX ( ),
					this.mouseY - this.position.getY ( )
			).normalize ( );
			
			Point2D offset = speed.multiply ( brzinaKretanja  * radius );
			
			double x = this.position.getX ( ) + offset.getX ( );
			double y = this.position.getY ( ) + offset.getY ( );
			
			Translate position = new Translate ( x, y );

			if (mouseEvent.isSecondaryButtonDown()) {
				bullet = new Bullet(0.5D * this.radius, position, speed, Color.STEELBLUE, 2.5D);
				bullet.setStrength(2);
			} else {
				bullet = new Bullet(0.3D * this.radius, position, speed);
			}
		}
		
		return bullet;
	}

	public boolean handleCollision(Bullet bullet) {
		double bx = bullet.getBoundsInParent().getCenterX();
		double by = bullet.getBoundsInParent().getCenterY();
		double px = this.position.getX();
		double py = this.position.getY();
		double d = (bx - px) * (bx - px) + (by - py) * (by - py);
		double r = (bullet.getRadius() + this.radius) * (bullet.getRadius() + this.radius);
		boolean collided = d <= r;


		return collided;
	}


	public int getLives() {
		return this.lives;
	}
	public int getMaxLives(){
		return this.MaxLives;
	}
	public void updateLives(int lives){
			if(this.lives + lives >= this.MaxLives){
				this.lives = this.MaxLives;
			}
			this.lives+=lives;
	}

	public boolean isShielded() {
		return this.shield.isVisible();
	}

	public void addShield(){
		this.shield.setVisible(true);
		if(this.NumOfShields == 0)
			++this.NumOfShields;

	}
	public void removeShield(){
		--this.NumOfShields;
		if(this.NumOfShields ==0){
			this.shield.setVisible(false);
		}
	}


}
