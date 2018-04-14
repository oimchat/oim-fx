package com.oim.ui.fx.bine;

import java.util.List;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.BaseStage;
import com.oim.fx.common.component.WaitingPane;
import com.oim.fx.ui.list.HeadImagePanel;
import com.oim.ui.fx.classics.component.FreeComboBox;
import com.only.fx.OnlyPopupOver;
import com.only.fx.OnlyTitlePane;
import com.only.fx.common.action.ValueAction;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class LoginStage extends BaseStage {

	private Button settingButton = new Button();// 顶部设置按钮

	private OnlyPopupOver popupOver = new OnlyPopupOver();// 账号提示
	private Label overLabel = new Label();

	private BorderPane borderPane = new BorderPane();

	private HeadImagePanel head = new HeadImagePanel();
	private VBox userBox = new VBox();

	private FreeComboBox<String> accountComboBox = new FreeComboBox<String>();
	private PasswordField passwordField = new PasswordField();

	private Button loginButton = new Button();// 登录按钮
	private WaitingPane waitingPanel = new WaitingPane();
	EventHandler<ActionEvent> loginAction;

	public LoginStage() {
		initComponent();
		initSet();
	}

	private void initComponent() {
		this.setTitle("登录");
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setWidth(270);
		this.setHeight(300);
		this.setMinWidth(300);
		this.setMinHeight(270);
		// this.setMaxWidth(610);
		this.setRadius(5);
		this.setCenter(borderPane);
		this.getScene().getStylesheets().add(this.getClass().getResource("/bine/css/login.css").toString());

		settingButton.setId("setting-button");
		settingButton.setPrefWidth(30);
		settingButton.setPrefHeight(27);

		OnlyTitlePane titlePane = getOnlyTitlePane();
		titlePane.getChildren().add(0, settingButton);

		popupOver.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_LEFT);

		popupOver.setDetachable(false);
		popupOver.setDetached(false);

		overLabel.setPadding(new Insets(4));
		popupOver.setContentNode(overLabel);

		// Separator separator = new Separator();

		VBox topBox = new VBox();
		// topBox.setPrefHeight(50);
		topBox.setMinHeight(30);
		// topBox.getChildren().add(separator);

		waitingPanel.add(WaitingPane.show_waiting, "登录中...",
				ImageBox.getImageClassPath("/common/images/loading/loading_140_9.gif"));
		waitingPanel.show(WaitingPane.show_waiting);

		// waitingPanel.setPrefWidth(428);
		// waitingPanel.setPrefHeight(150);
		// waitingPanel.setStyle("-fx-background-color:#ebf2f9;");

		head.setHeadSize(60);
		head.setHeadRadius(60);
		head.setPrefSize(60, 60);

		HBox headBox = new HBox();
		headBox.setPadding(new Insets(5, 0, 15, 0));
		headBox.setAlignment(Pos.BOTTOM_CENTER);
		headBox.getChildren().add(head);

		accountComboBox.setPromptText("账号");
		passwordField.setPromptText("密码");

		accountComboBox.setMinSize(80, 35);
		accountComboBox.setPrefSize(180, 35);
		passwordField.setPrefSize(180, 35);

		accountComboBox.setBorder(Border.EMPTY);
		accountComboBox.setBackground(Background.EMPTY);

		passwordField.getStyleClass().remove("text-field");
		passwordField.getStyleClass().add("empty-text-input-field");

		loginButton.setText("登  录");
		loginButton.setPrefSize(180, 35);

		Rectangle clip = new Rectangle();
		clip.setArcHeight(8);
		clip.setArcWidth(8);

		userBox.setClip(clip);
		userBox.widthProperty().addListener((Observable observable) -> {
			clip.setWidth(userBox.getWidth());
		});
		userBox.heightProperty().addListener((Observable observable) -> {
			clip.setHeight(userBox.getHeight());
		});

		userBox.setAlignment(Pos.BOTTOM_CENTER);
		userBox.setSpacing(1);
		userBox.setStyle("-fx-background-color: #e6e6e6;");

		VBox accountBox = new VBox();
		accountBox.setStyle("-fx-background-color: #ffffff;");
		accountBox.getChildren().add(accountComboBox);

		VBox passwordBox = new VBox();
		passwordBox.setStyle("-fx-background-color: #ffffff;");
		passwordBox.getChildren().add(passwordField);

		userBox.getChildren().add(accountBox);
		userBox.getChildren().add(passwordBox);

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.BOTTOM_CENTER);
		hBox.getChildren().add(userBox);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(hBox);
		stackPane.getChildren().add(waitingPanel);

		VBox centerBox = new VBox();
		centerBox.setAlignment(Pos.CENTER);
		centerBox.getChildren().add(headBox);
		centerBox.getChildren().add(stackPane);

		VBox buttonBox = new VBox();
		buttonBox.setAlignment(Pos.TOP_CENTER);
		buttonBox.setPadding(new Insets(15, 0, 15, 0));
		buttonBox.getChildren().add(loginButton);

		HBox bottomBox = new HBox();
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.getChildren().add(buttonBox);

		VBox vBox = new VBox();

		vBox.getChildren().add(centerBox);
		vBox.getChildren().add(bottomBox);

		borderPane.setTop(topBox);
		borderPane.setCenter(vBox);

		showWaiting(false);
	}

	public void showWaiting(boolean show) {
		waitingPanel.setVisible(show);
		userBox.setVisible(!show);
		loginButton.setVisible(!show);
	}

	public void setHeadImage(Image image) {
		head.setImage(image);
	}

	public void setHeadSize(double value) {
		head.setHeadSize(value);
	}

	public void setHeadRadius(double value) {
		head.setHeadRadius(value);
	}

	public String getAccount() {
		return accountComboBox.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public void setAccount(String value) {
		accountComboBox.setText(value);
	}

	public void setPassword(String value) {
		passwordField.setText(value);
	}

	public void setLoginAction(EventHandler<ActionEvent> value) {
		loginButton.setOnAction(value);
		loginAction = value;
	}

	public void setSettingAction(EventHandler<ActionEvent> value) {
		settingButton.setOnAction(value);
	}

	public void setAccountListener(ChangeListener<? super String> listener) {
		accountComboBox.addTextChangeListener(listener);
	}

	public void setList(List<String> list) {
		accountComboBox.setList(list);
	}

	public void setOnAccountRemove(ValueAction<String> valueAction) {
		accountComboBox.setOnRemove(valueAction);
	}

	public void removeAccount(String value) {
		accountComboBox.remove(value);
	}

	private void initSet() {
		Image image = ImageBox.getImageClassPath("/resources/images/common/head/default/1.png");
		setHeadImage(image);
	}

	public boolean verify() {
		String account = this.getAccount();
		String password = this.getPassword();

		if (null == account || "".equals(account)) {
			overLabel.setText("请输入您的账号登录!");
			popupOver.show(accountComboBox);
			accountComboBox.requestFocus();
			return false;
		}

		if (null == password || "".equals(password)) {
			overLabel.setText("请输入您的密码登录!");
			popupOver.show(passwordField);
			passwordField.requestFocus();
			return false;
		}
		return true;
	}

	public void showSettingButton(boolean value) {
		settingButton.setVisible(value);
	}
}
