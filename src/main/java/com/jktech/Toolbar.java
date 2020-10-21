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

        this.getItems().addAll(draw,erase,step);

    }

    private void handleErase(ActionEvent actionEvent) {
        _mainView.setDrawMode(Simulation.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        _mainView.setDrawMode(Simulation.ALIVE);
    }


    private void handleStep(ActionEvent actionEvent) {
        _mainView.getSimulation().step();
        _mainView.draw();
    }


}
