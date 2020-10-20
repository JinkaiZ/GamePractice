package com.jktech;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Button stepButton;
    private Canvas canvas;
    private Affine affine;
    private Simulation simulation;

    private int drawMode = 1;

    public MainView(){

        stepButton = new Button("step");
        stepButton.setOnAction(actionEvent -> {
            simulation.step();
            draw();
        });
        canvas = new Canvas(400,400);
        canvas.setOnMousePressed(this::handleDraw);
        canvas.setOnMouseDragged(this::handleDraw);

        this.setOnKeyPressed(this::OnKeyPressed);

        this.getChildren().addAll(stepButton,canvas);
        affine = new Affine();
        affine.appendScale(400/10f, 400/10f);
        simulation = new Simulation(10,10);

    }

    private void OnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.D){
            drawMode = 1;
            System.out.println("Draw mode");
        }
        else if(keyEvent.getCode() == KeyCode.E){
            drawMode = 0;
            System.out.println("erase mode");
        }
    }

    private void handleDraw(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();


        try {
            Point2D simCoord = affine.inverseTransform(mouseX, mouseY);
            int simX = (int) simCoord.getX();
            int simY = (int) simCoord.getY();
            System.out.println(simX + "," + simY);

            simulation.setState(simX,simY,drawMode);


            draw();

        } catch (NonInvertibleTransformException e) {
            System.out.println("Can not invert transform");
        }
    }

    public void draw(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,450,450);
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulation.width; x++) {
            for (int y = 0; y < simulation.height; y++) {
                if(simulation.getState(x,y) == 1) {
                    g.fillRect(x, y, 1, 1);
                }

            }

        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x < simulation.width + 1; x++) {
            g.strokeLine(x,0,x,10);
        }

        for (int y = 0; y < simulation.height + 1; y++) {
            g.strokeLine(0,y,10,y);

        }

    }
}
