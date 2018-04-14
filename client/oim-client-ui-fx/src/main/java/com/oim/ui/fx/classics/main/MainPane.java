package com.oim.ui.fx.classics.main;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.list.ListRootPane;
import com.only.fx.common.component.FlatTabPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017-12-08 20:34:10
 */
public class MainPane extends BorderPane {

	private final ImageView logoImageView = new ImageView();
	private final BorderPane borderPane = new BorderPane();

	private final VBox topVBox = new VBox();
	private final HBox titleHBox = new HBox();
	private final UserDataPane userDataPane = new UserDataPane();
	private final BorderPane centerBorderPane = new BorderPane();

	private final TextField findTextField = new TextField();
	private final HBox searchBox = new HBox();
	private final StackPane listBaseStackPane = new StackPane();

	private final FlatTabPane tabPane = new FlatTabPane();
	private final ListRootPane findListPane = new ListRootPane();

	private final ToolBar functionBox = new ToolBar();
	private final HBox rightFunctionBox = new HBox();

	private final VBox bottomBox = new VBox();

	public MainPane() {

		initComponent();
		initEvent();
		initDefault();
	}

	private void initComponent() {
		Label titleLabel = new Label("", logoImageView);
		titleLabel.setStyle("-fx-text-fill: white;");

		titleHBox.setPadding(new Insets(5, 0, 0, 10));
		titleHBox.setPrefHeight(30);
		titleHBox.getChildren().add(titleLabel);

		topVBox.getChildren().add(titleHBox);
		topVBox.getChildren().add(userDataPane);

		listBaseStackPane.getChildren().add(tabPane);
		listBaseStackPane.getChildren().add(findListPane);

		centerBorderPane.setTop(searchBox);
		centerBorderPane.setCenter(listBaseStackPane);

		findTextField.setPromptText("搜索：联系人、多人聊天、群");
		findTextField.getStyleClass().remove("text-field");
		findTextField.setBackground(Background.EMPTY);
		findTextField.setMinHeight(30);
		findTextField.setStyle("-fx-prompt-text-fill:#ffffff;");

		Image image = ImageBox.getImageClassPath("/classics/images/main/search/search_icon.png");
		ImageView searchImageView = new ImageView();
		searchImageView.setImage(image);

		searchBox.getChildren().add(findTextField);
		searchBox.getChildren().add(searchImageView);
		HBox.setHgrow(findTextField, Priority.ALWAYS);

		searchBox.setAlignment(Pos.CENTER_RIGHT);
		searchBox.setStyle("-fx-background-color:rgba(0, 0, 0, 0.3)");

		tabPane.setStyle("-fx-background-color:rgba(230, 230, 230, 0.8)");

		functionBox.setBackground(Background.EMPTY);
		functionBox.setPadding(new Insets(0, 5, 5, 0));

		VBox separator = new VBox();
		separator.setPrefHeight(1);
		separator.setMinHeight(1);
		separator.setBackground(Background.EMPTY);
		// separator.setStyle("-fx-background-color:rgba(230, 230, 230, 1)");

		BorderPane bottomBorderPane = new BorderPane();
		bottomBorderPane.setCenter(functionBox);
		bottomBorderPane.setRight(rightFunctionBox);

		bottomBox.setPadding(new Insets(5, 2, 1, 2));
		bottomBox.setStyle("-fx-background-color:rgba(230, 230, 230, 0.7)");

		bottomBox.getChildren().add(separator);
		bottomBox.getChildren().add(bottomBorderPane);

		borderPane.setTop(topVBox);
		borderPane.setCenter(centerBorderPane);
		borderPane.setBottom(bottomBox);

		this.setCenter(borderPane);
		tabPane.setVisible(true);
		findListPane.setVisible(false);

		functionBox.setOnMouseEntered(m -> {
			functionBox.setCursor(Cursor.DEFAULT);
		});
	}

	private void initEvent() {
		tabPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			e.consume();
		});
		findTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String text = findTextField.getText();
				if (null != text && !text.isEmpty()) {
					tabPane.setVisible(false);
					findListPane.setVisible(true);
				} else {
					tabPane.setVisible(true);
					findListPane.setVisible(false);
				}
			}
		});
	}

	private void initDefault() {
		Image logoIamge = new Image(this.getClass().getResource("/classics/images/main/title_logo.png").toExternalForm(), true);
		setLogoImage(logoIamge);
	}

	public void setLogoImage(Image value) {
		this.logoImageView.setImage(value);
	}

	public void setHeadImage(Image headImage) {
		userDataPane.setHeadImage(headImage);
	}

	public void setStatusImage(Image statusImage) {
		userDataPane.setStatusImage(statusImage);
	}

	public void setNickname(String nickname) {
		this.userDataPane.setNickname(nickname);
	}

	public void setText(String text) {
		this.userDataPane.setText(text);
	}

	public void addBusinessIcon(Node node) {
		userDataPane.addBusinessIcon(node);
	}

	public void removeBusinessIcon(Node node) {
		userDataPane.removeBusinessIcon(node);
	}

	public void addFunctionIcon(Node node) {
		functionBox.getItems().add(node);
	}

	public void addRightFunctionIcon(Node node) {
		rightFunctionBox.getChildren().add(node);
	}

	public void addTab(Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		tabPane.add(normalImage, hoverImage, selectedImage, node);
	}

	public void setOnStatusMouseClicked(EventHandler<? super MouseEvent> value) {
		userDataPane.setStatusOnMouseClicked(value);
	}

	public void setOnHeadMouseClicked(EventHandler<? super MouseEvent> value) {
		userDataPane.setHeadOnMouseClicked(value);
	}

	public TextField getFindTextField() {
		return findTextField;
	}

	public ListRootPane getFindListPane() {
		return findListPane;
	}

}
