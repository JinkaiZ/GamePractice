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

    private InfoBar _infoBar;
    private Canvas _canvas;
    private Affine _affine;
    private Simulation _simulation;

    private int _drawMode = 1;

    public MainView(){


        _canvas = new Canvas(400,400);
        _canvas.setOnMousePressed(this::handleDraw);
        _canvas.setOnMouseDragged(this::handleDraw);

        this.setOnKeyPressed(this::OnKeyPressed);

        Toolbar toolbar = new Toolbar(this);
        _infoBar = new InfoBar();

        this.getChildren().addAll(toolbar,_canvas, _infoBar);

        _affine = new Affine();
        _affine.appendScale(400/10f, 400/10f);
        _simulation = new Simulation(10,10);

    }

    private void OnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.D){
            _drawMode = Simulation.ALIVE;
            System.out.println("Draw mode");
        }
        else if(keyEvent.getCode() == KeyCode.E){
            _drawMode = Simulation.DEAD;
            System.out.println("erase mode");
        }
    }

    private void handleDraw(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();


        try {
            Point2D simCoord = _affine.inverseTransform(mouseX, mouseY);
            int simX = (int) simCoord.getX();
            int simY = (int) simCoord.getY();
            System.out.println(simX + "," + simY);

            _simulation.setState(simX,simY,_drawMode);


            draw();

        } catch (NonInvertibleTransformException e) {
            System.out.println("Can not invert transform");
        }
    }

    public void draw(){
        GraphicsContext g = _canvas.getGraphicsContext2D();
        g.setTransform(_affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,450,450);
        g.setFill(Color.BLACK);
        for (int x = 0; x < _simulation._width; x++) {
            for (int y = 0; y < _simulation._height; y++) {
                if(_simulation.getState(x,y) == Simulation.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }

            }

        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x < _simulation._width + 1; x++) {
            g.strokeLine(x,0,x,10);
        }

        for (int y = 0; y < _simulation._height + 1; y++) {
            g.strokeLine(0,y,10,y);

        }

    }

    public Simulation getSimulation() {
        return this._simulation;
    }

    public void setDrawMode(int newDrawMode) {
        _drawMode = newDrawMode;
    }
}
