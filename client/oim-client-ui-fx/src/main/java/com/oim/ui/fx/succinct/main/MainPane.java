package com.oim.ui.fx.succinct.main;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.ui.list.ListRootPanel;
import com.only.fx.common.component.FlatTab;
import com.only.fx.common.component.FlatTabPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017-12-08 20:34:10
 */
public class MainPane extends BorderPane {

	private final PersonalPane personalPane = new PersonalPane();
	private final BorderPane leftBorderPane = new BorderPane();
	private final VBox topVBox = new VBox();

	private final FlatTabPane tabPanel = new FlatTabPane();

	private final TextField findTextField = new TextField();
	private final ImageView findImageView = new ImageView();
	private final ListRootPanel findListPane = new ListRootPanel();

	public MainPane() {

		findTextField.getStyleClass().clear();
		findTextField.setBackground(Background.EMPTY);
		findTextField.setStyle("-fx-text-fill: #ffffff;-fx-prompt-text-fill: #a9a9a9;-fx-text-font-size: 14px;");
		
		HBox findHBox = new HBox();
		findHBox.setAlignment(Pos.CENTER_LEFT);
		findHBox.getChildren().add(findImageView);
		
		BorderPane findBorderPane=new BorderPane();
		findBorderPane.setStyle("-fx-background-color:#26292e");
		findBorderPane.setPrefSize(245, 30);
		findBorderPane.setLeft(findHBox);
		findBorderPane.setCenter(findTextField);
		
		VBox findVBox = new VBox();
		findVBox.setAlignment(Pos.CENTER);
		findVBox.setPadding(new Insets(5, 10, 5, 10));
		findVBox.getChildren().add(findBorderPane);
		
		topVBox.getChildren().add(personalPane);
		topVBox.getChildren().add(findVBox);
		
		final StackPane centerStackPane = new StackPane();

		centerStackPane.getChildren().add(tabPanel);
		centerStackPane.getChildren().add(findListPane);

		leftBorderPane.setPrefWidth(280);
		leftBorderPane.setTop(topVBox);
		leftBorderPane.setCenter(centerStackPane);

		leftBorderPane.setStyle("-fx-background-color:#2e3238;");

		this.setPrefHeight(600);
		this.setLeft(leftBorderPane);
		
		tabPanel.setVisible(true);
		findListPane.setVisible(false);
		
		initEvent();
		initTest();
		
		//tabPanel.setSide(Side.RIGHT);
		tabPanel.setTabSize(45);
	}

	private void initEvent() {
		tabPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			e.consume();
		});
		findTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String text = findTextField.getText();
				if (null != text && !text.isEmpty()) {
					tabPanel.setVisible(false);
					findListPane.setVisible(true);
				} else {
					tabPanel.setVisible(true);
					findListPane.setVisible(false);
				}
			}
		});
	}

	public void initTest() {
		Image image = ImageBox.getImageClassPath("/resources/images/common/head/default/1.png");
		personalPane.setHeadImage(image);
	}

	public void addTab(Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		FlatTab tab = new FlatTab(normalImage, hoverImage, selectedImage);
		tab.setBottomLineSize(1);
		tab.setBottomChange(false);
		tabPanel.add(tab, node);
	}

	public PersonalPane getPersonalPane() {
		return personalPane;
	}

	public TextField getFindTextField() {
		return findTextField;
	}

	public ListRootPanel getFindListPane() {
		return findListPane;
	}

}
