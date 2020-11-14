package com.jktech;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView _mainView;

    public Toolbar(MainView mainView){
        _mainView = mainView;
        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);
        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        this.getItems().addAll(draw,erase,reset,step,start,stop);

    }

    private void handleStop(ActionEvent actionEvent) {
        _mainView.getSimulator().stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        _mainView.setApplicationState(MainView.SIMULATING);
        _mainView.getSimulator().start();
    }

    private void handleReset(ActionEvent actionEvent) {
        _mainView.setApplicationState(MainView.EDITING);
        _mainView.draw();
    }

    private void handleErase(ActionEvent actionEvent) {
        _mainView.setDrawMode(Simulation.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        _mainView.setDrawMode(Simulation.ALIVE);
    }


    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Step pressed");
        _mainView.setApplicationState(MainView.SIMULATING);

        _mainView.getSimulation().step();
        _mainView.draw();
    }


}
