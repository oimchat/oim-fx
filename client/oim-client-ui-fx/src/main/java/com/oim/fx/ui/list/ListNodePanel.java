/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.list;

import com.oim.fx.common.box.ImageBox;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ListNodePanel extends VBox {

    private final HBox topBox = new HBox();
    private final VBox box = new VBox();
    boolean isShow = false;
    Label textLabel = new Label();
    Label numberLabel = new Label();

    Image closed = ImageBox.getImageClassPath("/resources/main/images/list/closed.png");
    Image open = ImageBox.getImageClassPath("/resources/main/images/list/open.png");

    ImageView imageView = new ImageView();
    BorderPane imagePane = new BorderPane();
    BorderPane textPane = new BorderPane();

    public ListNodePanel() {
        initComponent();
        iniEvent();
        //initTest();
    }

    private void initComponent() {
        this.getChildren().add(topBox);
        //this.getChildren().add(box);
        this.setBackground(Background.EMPTY);

        imagePane.setPrefWidth(30);
        imagePane.setPrefHeight(30);

        //textPane.setPrefHeight(30);
        HBox hBox = new HBox();
//        StackPane stackPane = new StackPane();

        topBox.getChildren().add(imagePane);
        topBox.getChildren().add(textPane);
        topBox.getStyleClass().add("list-top-title");

        //stackPane.getChildren().add(hBox);
        textPane.setTop(getGapNode(8));
        textPane.setCenter(hBox);
        hBox.getChildren().add(textLabel);
        hBox.getChildren().add(new Label(" "));
        hBox.getChildren().add(numberLabel);

        imagePane.setCenter(imageView);

        imageView.setImage(closed);

        textLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");
        textLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");

    }

    private void iniEvent() {
        topBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getButton()==MouseButton.PRIMARY) {
            		if (isShow) {
                        isShow = false;
                        imageView.setImage(closed);
                        ListNodePanel.this.getChildren().remove(box);
                    } else {
                        isShow = true;
                        imageView.setImage(open);
                        ListNodePanel.this.getChildren().add(box);
                    }
            	}
            }
        });
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

    public void addItem(Node node) {
    	if(!box.getChildren().contains(node)){
    		box.getChildren().add(node);
    	}
    }
    
    public int itemSize() {
    	return box.getChildren().size();
    }

    public void removeItem(Node node) {
        box.getChildren().remove(node);
    }

    public void clearItem() {
        box.getChildren().clear();;
    }

//    private void initTest() {
//        setText("我的好友");
//        setNumberText("0/9");
//
//        HeadItem headItem = new HeadItem();
//        addItem(headItem);
//
//        headItem = new HeadItem();
//        addItem(headItem);
//    }

}
