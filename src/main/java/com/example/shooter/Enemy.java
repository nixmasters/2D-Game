package com.example.shooter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.VLineTo;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Enemy extends Group {
	private int health;
	private Color bodyColor;
	private Color gunColor;
	private Circle body;
	private Path gun;
	private Rotate rotate;
	private int id;
	private double radius;
	private Translate animationTranslate;
	private double gunHeight;
	private long elapsed;
	private int totHP ;

	public Enemy(double radius, int health, int id) {

		this.body = new Circle(radius);
		this.body.setFill(Color.RED);
		totHP = health;
		this.id = id;
		this.rotate = new Rotate();
		//this.getTransforms().add(this.rotate);
		Rotate rotate = new Rotate((double)(-id) * 360.0D / 4.0D);
		double gunWidth = 0.3D * radius;
		double gunHeight = 2.0D * radius;
		this.gun = new Path(new PathElement[]{new MoveTo(gunWidth * 0.25D, 0.0D), new VLineTo(0.8D * gunHeight), new LineTo(gunWidth, gunHeight), new HLineTo(-gunWidth), new LineTo(-0.25D * gunWidth, gunHeight * 0.8D), new VLineTo(0.0D), new ClosePath()});
		this.gunHeight = gunHeight;
		this.animationTranslate = new Translate();
		this.gun.setFill(Color.PURPLE);
		this.gun.getTransforms().addAll(this.rotate, rotate, this.animationTranslate);
		super.getChildren().addAll(this.body, this.gun);
		this.health = health;
		this.bodyColor = Color.RED;
		this.gunColor = Color.PURPLE;
		this.radius = radius;
	}



	public boolean handleCollision(Bullet bullet) {
		double bx = bullet.getBoundsInParent().getCenterX();
		double by = bullet.getBoundsInParent().getCenterY();
		double x = this.getBoundsInParent().getCenterX();
		double y = this.getBoundsInParent().getCenterY();
		double d = (bx - x) * (bx - x) + (by - y) * (by - y);
		double r = (bullet.getRadius() + this.body.getRadius()) * (bullet.getRadius() + this.body.getRadius());
		boolean collided = d <= r;
		if (collided) {
			this.health -= bullet.getStrength();
			double bodyColorRed = this.bodyColor.getRed();
			double bodyColorGreen = this.bodyColor.getGreen();
			double bodyColorBlue = this.bodyColor.getBlue();
			double bodyColorOpacity = this.bodyColor.getOpacity() - (double)bullet.getStrength() * (1./(totHP));
			double gunColorRed = this.gunColor.getRed();
			double gunColorGreen = this.gunColor.getGreen();
			double gunColorBlue = this.gunColor.getBlue();
			double gunColorOpacity = this.gunColor.getOpacity() - (double)bullet.getStrength() * (1./(totHP));
			this.bodyColor = new Color(bodyColorRed, bodyColorGreen, bodyColorBlue, bodyColorOpacity);
			this.gunColor = new Color(gunColorRed, gunColorGreen, gunColorBlue, gunColorOpacity);
			this.body.setFill(this.bodyColor);
			this.gun.setFill(this.gunColor);
		}

		return collided;
	}

	public boolean isDead() {
		return this.health <= 0;
	}

	public void updateGun(Player player) {
		double x = this.getBoundsInParent().getCenterX();
		double y = this.getBoundsInParent().getCenterY();
		double px = player.getBoundsInParent().getCenterX();
		double py = player.getBoundsInParent().getCenterY();
		Point2D vector = (new Point2D(px - x, py - y)).normalize();
		double angle = -Math.signum(vector.getX()) * vector.angle(0.0D, 1.0D);
		this.rotate.setAngle(angle);

	}
	public Rotate getRotatee(){
		return rotate;
	}
	public Bullet fire(Player player) {

		double x = this.getBoundsInParent().getCenterX();
		double y = this.getBoundsInParent().getCenterY();
		double px = player.getBoundsInParent().getCenterX();
		double py = player.getBoundsInParent().getCenterY();
		Point2D speed = (new Point2D(px - x, py - y)).normalize();
		Point2D offset = speed.multiply(2.1D * this.radius);
		double bx = x + offset.getX();
		double by = y + offset.getY();
		Translate position = new Translate(bx, by);
		Bullet bullet = new Bullet(0.3D * this.radius, position, speed, Color.DARKSALMON, 1.0D);
		Timeline animation = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.animationTranslate.yProperty(), 0)}), new KeyFrame(Duration.seconds(1.0D), new KeyValue[]{new KeyValue(this.animationTranslate.yProperty(), -this.gunHeight * 0.1D)}), new KeyFrame(Duration.seconds(2.0D), new KeyValue[]{new KeyValue(this.animationTranslate.yProperty(), 0)})});
		animation.play();
		return bullet;
	}

}