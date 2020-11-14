package com.jktech;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private InfoBar _infoBar;
    private Canvas _canvas;
    private Affine _affine;
    private Simulation _simulation;
    private Simulation initialSimulation;

    private Simulator simulator;

    private int _drawMode = Simulation.ALIVE;
    private int applicationState = EDITING;

    public MainView() {


        _canvas = new Canvas(400, 400);
        _canvas.setOnMousePressed(this::handleDraw);
        _canvas.setOnMouseDragged(this::handleDraw);
        _canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::OnKeyPressed);

        Toolbar toolbar = new Toolbar(this);
        _infoBar = new InfoBar();
        _infoBar.setDrawModel(_drawMode);
        _infoBar.setCursorPosition(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);


        this.getChildren().addAll(toolbar, _canvas, spacer, _infoBar);

        _affine = new Affine();
        _affine.appendScale(400 / 10f, 400 / 10f);
        this.initialSimulation = new Simulation(10, 10);
        _simulation = Simulation.copy(this.initialSimulation);
    }

    private void handleMoved(MouseEvent event) {
        Point2D simCoord = this.getSimulationCoordinates(event);
        _infoBar.setCursorPosition((int)simCoord.getX(), (int)simCoord.getY());
    }

    private void OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            _drawMode = Simulation.ALIVE;
            System.out.println("Draw mode");
        } else if (keyEvent.getCode() == KeyCode.E) {
            _drawMode = Simulation.DEAD;
            System.out.println("erase mode");
        }
    }

    private void handleDraw(MouseEvent event) {

        if(this.applicationState == SIMULATING){
            return;
        }

        Point2D simCoord = this.getSimulationCoordinates(event);

        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();
        System.out.println(simX + "," + simY);

        initialSimulation.setState(simX, simY, _drawMode);

        draw();

    }

    private Point2D getSimulationCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        try {
            Point2D simCoord = _affine.inverseTransform(mouseX, mouseY);
            return simCoord;
        }
        catch(NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertable transform");
        }
    }


    public void draw(){
        GraphicsContext g = _canvas.getGraphicsContext2D();
        g.setTransform(_affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,450,450);

        if(this.applicationState == EDITING){
            drawSimulation(this.initialSimulation);
        }else{
            drawSimulation(_simulation);
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

    private void drawSimulation(Simulation simulationToDraw){
        GraphicsContext g = _canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw._width; x++) {
            for (int y = 0; y < simulationToDraw._height; y++) {
                if(simulationToDraw.getState(x,y) == Simulation.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }

            }

        }
    }

    public Simulation getSimulation() {
        return this._simulation;
    }

    public void setDrawMode(int newDrawMode) {
        _drawMode = newDrawMode;
        _infoBar.setDrawModel(newDrawMode);
    }

    public void setApplicationState(int applicationState) {
        if(applicationState == this.applicationState){
            return;
        }

        if(applicationState == SIMULATING) {
            _simulation = Simulation.copy(this.initialSimulation);
            this.simulator = new Simulator(this, _simulation);
        }
        this.applicationState = applicationState;
    }

    public Simulator getSimulator() {
        return simulator;
    }
}
