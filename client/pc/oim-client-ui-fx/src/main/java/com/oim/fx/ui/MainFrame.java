/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.ui.list.TabPanel;
import com.oim.ui.fx.classics.main.UserDataPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
//import javafx.scene.Group;
import javafx.scene.Node;
//import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
//import javafx.stage.Modality;
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;

/**
 *
 * @author Only
 */
public class MainFrame extends BaseFrame {

	ImageView logoImageView = new ImageView();
	BorderPane borderPane = new BorderPane();

	VBox baseBox = new VBox();
	UserDataPane userDataPanel = new UserDataPane();
	HBox titleBox = new HBox();

	VBox centerBox = new VBox();
	HBox searchBox = new HBox();
	TabPanel tabPanel = new TabPanel();

	ToolBar appBox = new ToolBar();

	// HBox appBox = new HBox();
	HBox rightAppBox = new HBox();
	HBox appRootBox = new HBox();

	ToolBar functionBox = new ToolBar();
	HBox rightFunctionBox = new HBox();
	// HBox functionBox = new HBox();
	HBox functionRootBox = new HBox();

	VBox bottomBox = new VBox();
//	Stage stage = new Stage();

	public MainFrame() {
		initComponent();
		initDefault();
	}

	/**
	 * 初始化布局排版和各个组件
	 */
	private void initComponent() {
//		stage.initStyle(StageStyle.UTILITY);// 用于隐藏任务栏图标
//		stage.setOpacity(0);
//		stage.show();
//		stage.setScene(new Scene(new Group(), 1, 1));
//		stage.setMaxWidth(1);
//		stage.setMaxHeight(1);
//		stage.setX(0);
//		stage.setY(Screen.getPrimary().getBounds().getHeight() + 100);
//		this.initOwner(stage);
		//this.initModality(Modality.APPLICATION_MODAL);
		// this.setBackground("Resources/Images/Wallpaper/14.jpg");
		this.setTitle("OIM");
		this.setTitlePaneStyle(2);
		this.setWidth(290);
		this.setHeight(630);
		this.setMinWidth(290);
		this.setMinHeight(530);
		this.setMaxWidth(610);
		this.setRadius(5);
		this.setCenter(borderPane);

		getOnlyTitlePane().addIconifiedAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainFrame.this.hide();
			}
		});

		Label titleLabel = new Label("", logoImageView);
		titleLabel.setStyle("-fx-text-fill: white;");
		titleBox.setPrefHeight(30);

		titleBox.getChildren().add(this.getGapNode(10));
		titleBox.getChildren().add(titleLabel);

		baseBox.getChildren().add(this.getGapNode(5));
		baseBox.getChildren().add(titleBox);
		baseBox.getChildren().add(userDataPanel);

		borderPane.setTop(baseBox);
		borderPane.setCenter(centerBox);
		borderPane.setBottom(bottomBox);

		centerBox.getChildren().add(searchBox);
		centerBox.getChildren().add(tabPanel);
		VBox.setVgrow(tabPanel, Priority.ALWAYS);

		TextField searchField = new TextField();
		searchField.setPromptText("搜索：联系人、多人聊天、群、企业");
		searchField.getStyleClass().remove("text-field");
		searchField.setBackground(Background.EMPTY);
		searchField.setMinHeight(30);
		searchField.setStyle("-fx-prompt-text-fill:#ffffff;");

		Image image = ImageBox.getImageClassPath("/resources/main/images/search/search_icon.png");
		ImageView headImageView = new ImageView();
		headImageView.setImage(image);

		searchBox.getChildren().add(searchField);
		searchBox.getChildren().add(headImageView);
		HBox.setHgrow(searchField, Priority.ALWAYS);
		searchBox.setAlignment(Pos.CENTER_RIGHT);
		searchBox.setStyle("-fx-background-color:rgba(0, 0, 0, 0.3)");

		tabPanel.setStyle("-fx-background-color:rgba(230, 230, 230, 0.8)");

		appBox.setBackground(Background.EMPTY);
		appBox.setPadding(new Insets(0, 5, 5, 0));

		functionBox.setBackground(Background.EMPTY);
		functionBox.setPadding(new Insets(0, 5, 5, 0));

		// appBox.setSpacing(3);
		// functionBox.setSpacing(3);

		appRootBox.setSpacing(3);

		appRootBox.setAlignment(Pos.CENTER_RIGHT);
		functionRootBox.setAlignment(Pos.CENTER_RIGHT);

		// Separator separator = new Separator();
		VBox separator = new VBox();
		// separator.setOrientation(Orientation.HORIZONTAL);
		separator.setPrefHeight(1);
		separator.setMinHeight(1);
		separator.setBackground(Background.EMPTY);
		// separator.setStyle("-fx-background-color:rgba(230, 230, 230, 1)");

		centerBox.getChildren().add(separator);

		VBox appTempBox = new VBox();
		appTempBox.getChildren().add(appBox);

		appRootBox.getChildren().add(this.getGapNode(5));
		appRootBox.getChildren().add(appTempBox);
		appRootBox.getChildren().add(rightAppBox);
		appRootBox.getChildren().add(this.getGapNode(2));

		functionRootBox.getChildren().add(this.getGapNode(5));
		functionRootBox.getChildren().add(functionBox);
		functionRootBox.getChildren().add(rightFunctionBox);
		functionRootBox.getChildren().add(this.getGapNode(3));

		HBox.setHgrow(appTempBox, Priority.ALWAYS);
		HBox.setHgrow(functionBox, Priority.ALWAYS);

		AnchorPane gapPane = new AnchorPane();
		gapPane.setPrefHeight(5);

		// bottomBox.getChildren().add(separator);
		bottomBox.getChildren().add(gapPane);
		bottomBox.getChildren().add(appRootBox);
		bottomBox.getChildren().add(functionRootBox);
		bottomBox.getChildren().add(this.getGapNode(1));

		bottomBox.setStyle("-fx-background-color:rgba(230, 230, 230, 0.7)");

		appBox.setOnMouseEntered(m -> {
			appBox.setCursor(Cursor.DEFAULT);
		});
		
		functionBox.setOnMouseEntered(m -> {
			functionBox.setCursor(Cursor.DEFAULT);
		});
	}

	 public Node getGapNode(double value) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(value);
        pane.setPrefHeight(value);
        pane.setBackground(Background.EMPTY);
        return pane;
    }

	private void initDefault() {
		Image logoIamge = new Image(this.getClass().getResource("/resources/main/images/title_logo.png").toExternalForm(), true);
		setLogoImage(logoIamge);
	}

	public void setLogoImage(Image value) {
		this.logoImageView.setImage(value);
	}

	public void setHeadImage(Image headImage) {
		userDataPanel.setHeadImage(headImage);
	}

	public void setStatusImage(Image statusImage) {
		userDataPanel.setStatusImage(statusImage);
	}

	public void setNickname(String nickname) {
		this.userDataPanel.setNickname(nickname);
	}

	public void setText(String text) {
		this.userDataPanel.setText(text);
	}

	public void addBusinessIcon(Node node) {
		userDataPanel.addBusinessIcon(node);
	}

	public void removeBusinessIcon(Node node) {
		userDataPanel.removeBusinessIcon(node);
	}

	public void addAppIcon(Node node) {
		// appBox.getChildren().add(node);
		appBox.getItems().add(node);
	}

	public void addFunctionIcon(Node node) {
		// functionBox.getChildren().add(node);
		functionBox.getItems().add(node);
	}

	public void addRightAppIcon(Node node) {
		rightAppBox.getChildren().add(node);
	}

	public void addRightFunctionIcon(Node node) {
		rightFunctionBox.getChildren().add(node);
	}

	public void addTab(Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		tabPanel.add(normalImage, hoverImage, selectedImage, node);
	}

	public void setStatusOnMouseClicked(EventHandler<? super MouseEvent> value) {
		userDataPanel.setStatusOnMouseClicked(value);
	}

	public void setHeadOnMouseClicked(EventHandler<? super MouseEvent> value) {
		userDataPanel.setHeadOnMouseClicked(value);
	}
}
