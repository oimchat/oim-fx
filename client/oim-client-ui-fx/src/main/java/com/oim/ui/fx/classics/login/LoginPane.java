package com.oim.ui.fx.classics.login;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import org.comtel2000.keyboard.control.KeyBoardPopup;
import org.comtel2000.keyboard.control.KeyBoardPopupBuilder;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.MediaSimplPane;
import com.oim.fx.common.component.WaitingPane;
import com.oim.fx.ui.list.HeadImagePanel;
import com.oim.ui.fx.classics.component.FreeComboBox;
import com.only.fx.OnlyPopupOver;
import com.only.fx.common.action.ValueAction;
import com.only.fx.common.component.OnlyMenuItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.PopupWindow.AnchorLocation;

/**
 * @author XiaHui
 * @date 2017-12-05 12:09:20
 */
public class LoginPane extends StackPane {

	private OnlyPopupOver popupOver = new OnlyPopupOver();// 账号提示
	private Label overLabel = new Label();

	private DropShadow dropShadow = new DropShadow();// 阴影特效
	/**
	 * 最底层面板
	 */
	private final StackPane rootStackPane = new StackPane();
	// 背景图片显示组件
	private final ImageView backgroundImageView = new ImageView();
	private final ImageView topImageView = new ImageView();

	// 上部显示背景图片，底部白色分割面板
	private final BorderPane separatePane = new BorderPane();

	private final StackPane centerStackPane = new StackPane();
	private final StackPane userStackPane = new StackPane();
	private final WaitingPane waitingPanel = new WaitingPane();

	private final BorderPane userBorderPane = new BorderPane();

	private Button statusButton = new Button();
	private ImageView statusImageView = new ImageView();// 状态显示
	private final HeadImagePanel headImagePanel = new HeadImagePanel();

	private FreeComboBox<String> accountComboBox = new FreeComboBox<String>();// 账号输入框
	private PasswordField passwordField = new PasswordField();
	private JFXButton loginButton = new JFXButton();// 登录按钮

	protected BorderPane checkBoxBorderPane = new BorderPane();
	protected JFXCheckBox rememberCheckBox = new JFXCheckBox();// 记住密码
	protected JFXCheckBox autoCheckBox = new JFXCheckBox();// 自动登录

	protected Label registerLabel = new Label();
	protected Label forgetLabel = new Label();

	Image onlineImage = ImageBox.getImageClassPath("/common/images/status/flag/big/imonline.png");
	Image callMeImage = ImageBox.getImageClassPath("/common/images/status/flag/big/call_me.png");
	Image awayImage = ImageBox.getImageClassPath("/common/images/status/flag/big/away.png");
	Image busyImage = ImageBox.getImageClassPath("/common/images/status/flag/big/busy.png");
	Image muteImage = ImageBox.getImageClassPath("/common/images/status/flag/big/mute.png");
	Image invisibleImage = ImageBox.getImageClassPath("/common/images/status/flag/big/invisible.png");

	Button accountButton = new Button();
	Button passwordButton = new Button();

	KeyBoardPopup popup = KeyBoardPopupBuilder.create().initLocale(Locale.ENGLISH).build();// 弹出键盘
	ContextMenu menu = new ContextMenu();

	private String status = "1";

	public LoginPane() {
		initComponent();
		initContextMenu();
		initEvent();
		initSet();
	}

	private void initComponent() {

		VBox b = new VBox();
		b.setAlignment(Pos.BOTTOM_RIGHT);
		b.getChildren().add(statusButton);
		b.setPickOnBounds(false);// 面板不参与计算边界，透明区域无鼠标事件

		statusButton.getStyleClass().remove("button");
		statusButton.getStyleClass().add("status-button");
		statusButton.setPrefHeight(13);
		statusButton.setPrefWidth(13);

		statusButton.setGraphic(statusImageView);

		headImagePanel.setHeadRadius(65);
		headImagePanel.setHeadSize(65);

		StackPane headStackPane = new StackPane();
		headStackPane.getChildren().add(headImagePanel);
		headStackPane.getChildren().add(b);

		Button headButton = new Button();
		headButton.getStyleClass().remove("button");
		headButton.setGraphic(headStackPane);

		Image headShadowImage = ImageBox.getImageClassPath("/classics/images/login/head_bkg_shadow.png");

		ImageView headShadowImageView = new ImageView();
		headShadowImageView.setImage(headShadowImage);

		HBox headShadowHBox = new HBox();
		headShadowHBox.setAlignment(Pos.CENTER);
		headShadowHBox.getChildren().add(headShadowImageView);

		StackPane headPane = new StackPane();
		headPane.getChildren().add(headShadowHBox);
		headPane.getChildren().add(headButton);

		HBox headHBox = new HBox();
		headHBox.setAlignment(Pos.CENTER);
		headHBox.getChildren().add(headPane);

		VBox headVBox = new VBox();
		headVBox.setPadding(new Insets(0, 0, 0, 0));
		headVBox.setAlignment(Pos.BOTTOM_CENTER);
		headVBox.setPrefHeight(160);
		headVBox.getChildren().add(headHBox);

		accountComboBox.setPromptText("账号");
		accountComboBox.getTextField().setStyle("-fx-prompt-text-fill: #b3b3b3;-fx-text-font-size: 14px;");
		// accountField.setStyle("-fx-prompt-text-fill: #b3b3b3;");

		passwordField.setPromptText("密码");
		passwordField.getStyleClass().add("empty-text-input-field");
		passwordField.getStyleClass().remove("text-field");
		passwordField.setStyle("-fx-prompt-text-fill: #b3b3b3;-fx-text-font-size: 14px;");

		rememberCheckBox.setText("记住密码");
		autoCheckBox.setText("自动登录");

		rememberCheckBox.setStyle("-fx-text-fill: #b3b3b3;");
		autoCheckBox.setStyle("-fx-text-fill: #b3b3b3;");

		VBox rVBox = new VBox();
		VBox aVBox = new VBox();
		aVBox.setAlignment(Pos.CENTER_LEFT);

		checkBoxBorderPane.setLeft(rVBox);
		checkBoxBorderPane.setRight(aVBox);

		rVBox.getChildren().add(rememberCheckBox);
		aVBox.getChildren().add(autoCheckBox);

		loginButton.setText("登  录");
		loginButton.setPrefHeight(36);
		loginButton.getStyleClass().add("login-button");

		StackPane loginButtonStackPane = new StackPane();
		loginButtonStackPane.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				loginButton.setPrefWidth(loginButtonStackPane.getWidth());
			}
		});
		loginButtonStackPane.getChildren().add(loginButton);
		loginButtonStackPane.setEffect(dropShadow);
		dropShadow.setSpread(0.05);
		dropShadow.setColor(Color.web("#05bafb"));

		accountButton.getStyleClass().remove("button");
		passwordButton.getStyleClass().remove("button");

		accountButton.getStyleClass().add("account-button");
		passwordButton.getStyleClass().add("password-button");

		accountButton.setGraphic(new ImageView());
		passwordButton.setGraphic(new ImageView());

		accountComboBox.getArrowButton().getStyleClass().add("account-button");
		// accountButton.setPadding(new Insets(0,2,0,0));

		HBox accountBottomHBox = new HBox();
		accountBottomHBox.setMinHeight(1);
		accountBottomHBox.getStyleClass().add("input-bottom");
		// accountBottomHBox.setStyle("-fx-background-color:#05bafb;");

		HBox passwordBottomHBox = new HBox();
		passwordBottomHBox.setMinHeight(1);
		passwordBottomHBox.getStyleClass().add("input-bottom");

		BorderPane accountBorderPane = new BorderPane();
		accountBorderPane.setCenter(accountComboBox);
		// accountBorderPane.setRight(accountButton);
		accountBorderPane.setBottom(accountBottomHBox);

		BorderPane passwordBorderPane = new BorderPane();
		passwordBorderPane.setCenter(passwordField);
		passwordBorderPane.setRight(passwordButton);
		passwordBorderPane.setBottom(passwordBottomHBox);

		VBox inputBox = new VBox();
		inputBox.setSpacing(15);

		inputBox.getChildren().add(accountBorderPane);
		inputBox.getChildren().add(passwordBorderPane);

		VBox inputInofBox = new VBox();
		inputInofBox.setSpacing(10);
		inputInofBox.getChildren().add(inputBox);
		inputInofBox.getChildren().add(checkBoxBorderPane);
		inputInofBox.getChildren().add(loginButtonStackPane);

		registerLabel.setText("注册账号");
		forgetLabel.setText("忘记密码");

		registerLabel.setCursor(Cursor.HAND);
		forgetLabel.setCursor(Cursor.HAND);

		registerLabel.setStyle("-fx-text-fill: #b3b3b3;");
		forgetLabel.setStyle("-fx-text-fill: #b3b3b3;");

		VBox registerVBox = new VBox();
		registerVBox.setAlignment(Pos.BOTTOM_CENTER);
		registerVBox.setPadding(new Insets(0, 35, 10, 12));

		VBox forgetVBox = new VBox();
		forgetVBox.setAlignment(Pos.BOTTOM_CENTER);

		registerVBox.getChildren().add(registerLabel);
		forgetVBox.getChildren().add(forgetLabel);
		forgetVBox.setPadding(new Insets(0, 12, 10, 35));

		userBorderPane.setTop(headVBox);
		userBorderPane.setCenter(inputInofBox);

		userBorderPane.setLeft(registerVBox);
		userBorderPane.setRight(forgetVBox);

		userStackPane.getChildren().add(userBorderPane);

		Image waitingImage = ImageBox.getImageClassPath("/common/images/loading/loading_312_4.gif");
		waitingPanel.add(WaitingPane.show_waiting, "登录中...", waitingImage);
		waitingPanel.show(WaitingPane.show_waiting);

		// waitingPanel.setPrefWidth(428);
		// waitingPanel.setPrefHeight(150);
		// waitingPanel.setStyle("-fx-background-color:#ebf2f9;");

		centerStackPane.getChildren().add(waitingPanel);
		centerStackPane.getChildren().add(userStackPane);

		VBox topBox = new VBox();
		topBox.getChildren().add(topImageView);

		VBox bottomBox = new VBox();
		bottomBox.setStyle("-fx-background-color:#ffffff;");
		bottomBox.setPrefHeight(200);

		separatePane.setTop(topBox);
		separatePane.setBottom(bottomBox);

		rootStackPane.setPrefSize(428, 328);
		
		rootStackPane.getChildren().add(backgroundImageView);
		rootStackPane.getChildren().add(mp);
		rootStackPane.getChildren().add(separatePane);
		rootStackPane.getChildren().add(centerStackPane);

		this.getChildren().add(rootStackPane);

		popupOver.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_LEFT);

		popupOver.setDetachable(false);
		popupOver.setDetached(false);

		overLabel.setPadding(new Insets(4));
		popupOver.setContentNode(overLabel);

		popup.setAutoHide(true);
		popup.setOnKeyboardCloseButton(e -> popup.hide());
		popup.setAnchorLocation(AnchorLocation.CONTENT_BOTTOM_LEFT);
		
		
		mp.setPrefWidth(430);
		mp.setPrefHeight(203);

		
	}

	private void initEvent() {

		passwordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				passwordField.requestFocus();
				// popup.setVisible(true);
				popup.show(passwordField, event.getScreenX() - 280, event.getScreenY() + 150);
				// popup.show(LoginFrame.this, LoginFrame.this.getX() + 110,
				// LoginFrame.this.getY() + 280);
				event.consume();
			}
		});
		statusButton.setOnMouseClicked((MouseEvent event) -> {
			menu.show(statusButton, event.getScreenX(), event.getScreenY());
		});

		autoCheckBox.setOnAction(a -> {
			boolean selected = autoCheckBox.isSelected();
			if (selected) {
				rememberCheckBox.setSelected(true);
			}
		});
		
		rememberCheckBox.setOnAction(a -> {
			boolean selected = rememberCheckBox.isSelected();
			if (!selected) {
				autoCheckBox.setSelected(false);
			}
		});
		this.addEventHandler(KeyEvent.KEY_RELEASED, k -> {
			if (k.getCode() == KeyCode.TAB) {
				tab();
			} else if (k.getCode() == KeyCode.ENTER) {
				EventHandler<ActionEvent> loginAction = loginButton.getOnAction();
				if (null != loginAction) {
					ActionEvent event = new ActionEvent(loginButton, loginButton);
					loginAction.handle(event);
				}
			}
		});
	}

	boolean isAccountFocused = true;

	private void tab() {
		if (isAccountFocused) {
			passwordField.requestFocus();
		} else {
			accountComboBox.getTextField().requestFocus();
		}
		isAccountFocused = !isAccountFocused;
	}

	private void initContextMenu() {
		//////////////////////////////////////

		ImageView iv = new ImageView();
		iv.setImage(onlineImage);
		OnlyMenuItem menuItem = new OnlyMenuItem("我在线上", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(onlineImage);
				status = "1";
			}
		});

		iv = new ImageView();
		iv.setImage(callMeImage);
		menuItem = new OnlyMenuItem("Q我吧", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(callMeImage);
				status = "2";
			}
		});

		iv = new ImageView();
		iv.setImage(awayImage);
		menuItem = new OnlyMenuItem("离开", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(awayImage);
				status = "3";
			}
		});

		iv = new ImageView();
		iv.setImage(busyImage);
		menuItem = new OnlyMenuItem("忙碌", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(busyImage);
				status = "4";
			}
		});

		iv = new ImageView();
		iv.setImage(muteImage);
		menuItem = new OnlyMenuItem("请勿打扰", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(muteImage);
				status = "5";
			}
		});

		iv = new ImageView();
		iv.setImage(invisibleImage);
		menuItem = new OnlyMenuItem("隐身", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(invisibleImage);
				status = "6";
			}
		});
	}

	private void initSet() {
		Image image = ImageBox.getImageClassPath("/classics/images/login/default/1.png");
		//Image backgroundIamge = ImageBox.getImageClassPath("/classics/images/login/b1.jpg");
		// Image topIamge =
		// ImageBox.getImageClassPath("/classics/images/login/login_bkg_normal.png");
		//setBackgroundImage(backgroundIamge);
		// setTopImage(topIamge);
		setHeadImage(image);
		this.showWaiting(false);

		statusImageView.setImage(onlineImage);
	}

	public void setBackgroundImage(Image image) {
		backgroundImageView.setImage(image);
	}

	public void setTopImage(Image image) {
		topImageView.setImage(image);
	}

	public void showWaiting(boolean show) {
		waitingPanel.setVisible(show);
		userStackPane.setVisible(!show);
	}

	public void setHeadImage(Image headImage) {
		headImagePanel.setImage(headImage);
	}

	public void setHeadSize(double value) {
		headImagePanel.setHeadSize(value);
	}

	public void setHeadRadius(double value) {
		headImagePanel.setHeadRadius(value);
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public void setPassword(String value) {
		passwordField.setText(value);
	}

	public String getAccount() {
		return accountComboBox.getText();
	}

	public void setAccount(String value) {
		accountComboBox.setText(value);
	}

	public void setStatus(String status) {
		status = (null == status) ? "1" : status;
		this.status = status;
		switch (status) {
		case "1":
			statusImageView.setImage(onlineImage);
			break;
		case "2":
			statusImageView.setImage(callMeImage);
			break;
		case "3":
			statusImageView.setImage(awayImage);
			break;
		case "4":
			statusImageView.setImage(busyImage);
			break;
		case "5":
			statusImageView.setImage(muteImage);
			break;
		case "6":
			statusImageView.setImage(invisibleImage);
			break;
		default:
			statusImageView.setImage(onlineImage);
			this.status = "1";
			break;
		}
	}

	public String getStatus() {
		return status;
	}

	public boolean isAutoLogin() {
		return autoCheckBox.isSelected();
	}

	public void setAutoLogin(boolean autoLogin) {
		autoCheckBox.setSelected(autoLogin);
	}

	public boolean isRememberPassword() {
		return rememberCheckBox.isSelected();
	}

	public void setRememberPassword(boolean rememberPassword) {
		rememberCheckBox.setSelected(rememberPassword);
	}

	// public void setStatus(String status) {
	// this.status = status;
	// }

	public void setAccountList(List<String> list) {
		accountComboBox.setList(list);
	}

	public void removeAccount(String value) {
		accountComboBox.remove(value);
	}

	public void setOnAccountRemove(ValueAction<String> valueAction) {
		accountComboBox.setOnRemove(valueAction);
	}

	public void setOnLoginAction(EventHandler<ActionEvent> value) {
		loginButton.setOnAction(value);
	}

	public void addAccountListener(ChangeListener<? super String> listener) {
		accountComboBox.addTextChangeListener(listener);
	}

	public void setRegisterOnMouseClicked(EventHandler<MouseEvent> value) {
		registerLabel.setOnMouseClicked(value);
	}

	public void setForgetOnMouseClicked(EventHandler<MouseEvent> value) {
		forgetLabel.setOnMouseClicked(value);
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
	
	private MediaSimplPane mp = new MediaSimplPane();
	
	public void setVideo(File file) {
		try {
			String pathString = file.toURI().toURL().toString();
			mp.setUrl(pathString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		mp.stop();
	}

	public void play() {
		mp.play();
	}
}
