package com.example.shooter;

import javafx.application.Platform;
import javafx.scene.Node;
import com.example.shooter.timer.MyTimer;
import com.example.shooter.timer.Updatable;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import static com.example.shooter.Constants.*;

public class JavaFXMenu extends Application {
    private boolean gameOver;
    private MyTimer timer;
    private MyTimer eventTimer;
    private Group root;


    @Override
    public void start ( Stage stage ) throws IOException {
        Label label = new Label("Izaberite vrstu topa");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Mali", "Srednji", "Veliki");
        comboBox.setValue("Mali");




        // Create a Button
        Button button = new Button("Potvrdi");


        // Add event handler to the button
        button.setOnAction(event -> {
            String selectedItem = comboBox.getSelectionModel().getSelectedItem();
            JavaFXApplication.main(null);
        });

        // Create a VBox to hold the ComboBox and Button
        VBox vbox = new VBox(10, label, comboBox, button);






        this.root = new Group(new Node[]{vbox});






        Scene scene = new Scene ( root, WINDOW_WIDTH, WINDOW_HEIGHT );

        scene.setCursor(Cursor.NONE);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();





    }

    public static void main ( String[] args ) {
        launch ( );
    }
}