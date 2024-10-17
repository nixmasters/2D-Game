package com.example.shooter;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class Ammunition extends Group {
    private int count;
    private int fired;
    private Rectangle[] rectangles;

    public Ammunition(double width, double height, int count) {
        double dWidth = width / (double)(8);
        this.count = count;
        this.rectangles = new Rectangle[4];
        this.fired = 0;

            for(int i = 0; i < 4; ++i) {
                this.rectangles[i] = new Rectangle(dWidth, height, Color.BROWN);
                this.rectangles[i].getTransforms().add(new Translate((double)(2 * i) * dWidth, 0.0D));
                if(i<4-count && count !=4) {
                    this.rectangles[i].setVisible(false);
                }
                else{
                    this.rectangles[i].setVisible(true);
                }
                super.getChildren().add(this.rectangles[i]);
            }




    }
    public int getCount(){
        return count;
    }
    public void updateCount(int c) {

        int newCount = count + c;



        if (newCount >= 4) {
            newCount = 4;

        }
        else if(fired > 0) {
            --this.fired;
        }


        count = newCount;
        //System.out.println(count);
    }
    public boolean hasOne() {
        return this.count>0;
    }

    public void useOne() {

        super.getChildren().remove(this.rectangles[this.fired]);
        if(this.fired+1 <4)
            ++this.fired;
        if(this.count-1 >= 0)
            --this.count;



    }


}