package com.example.shooter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class HealthBar extends Rectangle {
    private double width;
    private double filled = 100.0D;

    public HealthBar(double width, double height, Color color) {
        super(width, height, color);
        this.width = width;
    }

    public void reduceBy(double amount) {
        this.filled -= amount;
        double oldWidth = super.getWidth();
        double newWidth = this.width * this.filled / 100.0D;
        Timeline animation = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.widthProperty(), oldWidth)}), new KeyFrame(Duration.seconds(1.0D), new KeyValue[]{new KeyValue(this.widthProperty(), newWidth)})});
        animation.play();
    }

    public void increaseBy(double amount) {
        this.filled += amount;

        // Ensure that 'filled' does not exceed 100%
        if (this.filled > 100.0D) {
            this.filled = 100.0D;
        }

        double oldWidth = super.getWidth();
        double newWidth = this.width * this.filled / 100.0D;

        Timeline animation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(this.widthProperty(), oldWidth)),
                new KeyFrame(Duration.seconds(1.0D), new KeyValue(this.widthProperty(), newWidth))
        );
        animation.play();
    }
}
