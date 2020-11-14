package com.jktech;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InfoBar extends HBox {

    private static String drawModelFormat = "Draw Mode: %s";
    private static String cursorPosFormat = "Cursor: (%d, %d)";

    private Label _cursor;
    private Label _editingTool;


    public InfoBar(){

        _cursor = new Label();
        _editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(_editingTool,spacer,_cursor);

    }

    public void setDrawModel(int drawMode){
        String drawModeString;
        if(drawMode == Simulation.ALIVE){
            drawModeString = "Drawing";
        }else{
            drawModeString = "Erasing";
        }
        _editingTool.setText(String.format(drawModelFormat, drawModeString));
    }
    public void setCursorPosition(int x, int y){
        _cursor.setText(String.format(cursorPosFormat,x,y));

    }

}
