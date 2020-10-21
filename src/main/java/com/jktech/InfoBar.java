package com.jktech;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InfoBar extends HBox {


    private Label _cursor;
    private Label _editingTool;


    public InfoBar(){

        _cursor = new Label("Cursor: (0.0)");
        _editingTool = new Label("Draw Mode: Drawing");

        this.getChildren().addAll(_cursor,_editingTool);

    }

}
