/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics.tree;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author XiaHui
 */
public class TreeNode extends BorderPane {

    Label textLabel = new Label();
    Label numberLabel = new Label();


    BorderPane textPane = new BorderPane();

    public TreeNode() {
        initComponent();
        iniEvent();
    }

    private void initComponent() {
        this.setBackground(Background.EMPTY);

        HBox hBox = new HBox();

        this.getChildren().add(textPane);
        this.getStyleClass().add("list-top-title");

        this.setTop(getGapNode(8));
        this.setCenter(hBox);
        hBox.getChildren().add(textLabel);
        hBox.getChildren().add(new Label(" "));
        hBox.getChildren().add(numberLabel);

        textLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");
        numberLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");
    }

    private void iniEvent() {
    }

    private Node getGapNode(double value) {
        AnchorPane tempPane = new AnchorPane();
        tempPane.setPrefWidth(value);
        tempPane.setPrefHeight(value);
        tempPane.setBackground(Background.EMPTY);
        return tempPane;
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

    public void setNumberText(String text) {
        numberLabel.setText(text);
    }
}
