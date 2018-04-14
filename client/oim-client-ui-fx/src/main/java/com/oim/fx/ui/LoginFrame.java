/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import org.comtel2000.keyboard.control.KeyBoardPopup;
import org.comtel2000.keyboard.control.KeyBoardPopupBuilder;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.MediaSimplPane;
import com.oim.fx.common.component.WaitingPane;
import com.oim.ui.fx.classics.component.ListPopup;
import com.only.fx.OnlyPopupOver;
import com.only.fx.OnlyTitlePane;
import com.only.fx.common.action.ExecuteAction;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

/**
 * 登录窗口
 *
 * @author XiaHui
 */
public class LoginFrame extends BaseFrame {

	// private DropShadow dropShadow = new DropShadow();//阴影特效

	private Button settingButton = new Button();// 顶部设置按钮

	private StackPane baseStackPane = new StackPane();
	private ImageView backgroundImageView = new ImageView();
	private ImageView logoImageView = new ImageView();
	private MediaSimplPane mp = new MediaSimplPane();

	private Button loginButton = new Button();// 登录按钮

	private TextField accountField = new TextField();// 账号输入框
	private PasswordField passwordField = new PasswordField();
	private ListPopup<Object> autoCompletionPopup = new ListPopup<Object>();

	private OnlyPopupOver accountOver = new OnlyPopupOver();// 账号提示
	private OnlyPopupOver passwordOver = new OnlyPopupOver();// 密码提示

	private Label accountOverLabel = new Label();
	private Label passwordOverLabel = new Label();

	private ImageView imageHeadView = new ImageView();// 头像显示
	Rectangle headClip = new Rectangle();
	private Button headButton = new Button();
	private AnchorPane statusButton = new AnchorPane();
	private ImageView statusImageView = new ImageView();// 状态显示

	protected CheckBox rememberCheckBox = new CheckBox();// 记住密码
	protected CheckBox autoCheckBox = new CheckBox();// 自动登录

	protected Label registerLabel = new Label();
	protected Label forgetLabel = new Label();

	protected Button addAccountButton = new Button();
	protected Button towCodeButton = new Button();

	private StackPane centerPane = new StackPane();
	private WaitingPane waitingPanel = new WaitingPane();
	private AnchorPane userPane = new AnchorPane();

	KeyBoardPopup popup = KeyBoardPopupBuilder.create().initLocale(Locale.ENGLISH).build();// 弹出键盘

	Image onlineImage = ImageBox.getImageClassPath("/resources/common/images/status/online.png");
	Image callMeImage = ImageBox.getImageClassPath("/resources/common/images/status/call_me.png");
	Image awayImage = ImageBox.getImageClassPath("/resources/common/images/status/away.png");
	Image busyImage = ImageBox.getImageClassPath("/resources/common/images/status/busy.png");
	Image muteImage = ImageBox.getImageClassPath("/resources/common/images/status/mute.png");
	Image invisibleImage = ImageBox.getImageClassPath("/resources/common/images/status/invisible.png");

	ContextMenu menu = new ContextMenu();

	private String status = "1";

	ExecuteAction accountAction;
	ExecuteAction removeAction;
	EventHandler<ActionEvent> loginAction;
	public LoginFrame() {
		initComponent();
		initContextMenu();
		iniEvent();
		initSet();
	}

	/**
	 * 初始化界面各组件
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年5月25日 下午5:56:15
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午5:56:15
	 */
	private void initComponent() {
		this.setTitle("登录");
		this.setResizable(false);
		this.setWidth(445);
		this.setHeight(345);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(baseStackPane);
		this.getScene().getStylesheets().add(this.getClass().getResource("/resources/login/css/login.css").toString());
		// this.getIcons().clear();

		settingButton.setId("setting-button");
		settingButton.setPrefWidth(30);
		settingButton.setPrefHeight(27);

		OnlyTitlePane titlePane = getOnlyTitlePane();
		titlePane.getChildren().add(0, settingButton);

		AnchorPane topRootPane = new AnchorPane();
		topRootPane.setBackground(Background.EMPTY);
		topRootPane.setPrefWidth(430);
		topRootPane.setPrefHeight(180);

		AnchorPane logoPane = new AnchorPane();
		logoPane.setBackground(Background.EMPTY);
		logoPane.setPrefWidth(430);
		logoPane.setPrefHeight(180);

		logoImageView.setLayoutX(125);
		logoImageView.setLayoutY(50);

		logoPane.getChildren().add(logoImageView);

		// WebView webView = new WebView();
		// webView.setPrefSize(430, 180);

		// WebEngine webEngine = webView.getEngine();
		// webEngine.load(this.getClass().getResource("/resources/login/html/index.html").toString());
		// File file = new File("Resources/UI/Login/Html/index.html");
		// String absolutePath = file.getAbsolutePath();
		// absolutePath = absolutePath.replace("\\", "/");
		// webEngine.load("file:/" + absolutePath);

		// System.out.println(this.getClass().getResource("/resources/login/html/index.html").toString());
		// System.out.println(file.getAbsolutePath());
		// System.out.println("file:/"+file.getAbsolutePath());
		// System.out.println(file.getPath());

		// topRootPane.getChildren().add(mp);
		topRootPane.getChildren().add(logoPane);

		userPane.setPrefWidth(428);
		userPane.setPrefHeight(150);
		userPane.setStyle("-fx-background-color:#ebf2f9;");

		accountOver.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_LEFT);
		passwordOver.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_LEFT);

		accountOver.setDetachable(false);
		accountOver.setDetached(false);

		passwordOver.setDetachable(false);
		passwordOver.setDetached(false);

		accountOverLabel.setPadding(new Insets(4));
		passwordOverLabel.setPadding(new Insets(4));

		accountOver.setContentNode(accountOverLabel);
		passwordOver.setContentNode(passwordOverLabel);

		accountField.getStyleClass().remove("text-field");
		passwordField.getStyleClass().remove("text-field");

		accountField.setBackground(Background.EMPTY);
		passwordField.setBackground(Background.EMPTY);

		accountField.setBorder(Border.EMPTY);
		passwordField.setBorder(Border.EMPTY);

		accountField.setPromptText("账号");
		passwordField.setPromptText("密码");

		// popup.addDoubleClickEventFilter(passwordField);
		/// popup.addFocusListener(this.getScene());

		accountField.setPrefWidth(170);
		passwordField.setPrefWidth(170);

		accountField.setPrefHeight(30);
		passwordField.setPrefHeight(29);

		loginButton.setText("登  录");
		loginButton.setId("login-button");
		loginButton.setLayoutX(135);
		loginButton.setLayoutY(107);
		loginButton.setPrefHeight(30);
		loginButton.setPrefWidth(194);

		rememberCheckBox.setText("记住密码");
		autoCheckBox.setText("自动登录");

		autoCheckBox.setPrefHeight(15);

		rememberCheckBox.setLayoutX(136);
		rememberCheckBox.setLayoutY(80);

		autoCheckBox.setLayoutX(260);
		autoCheckBox.setLayoutY(80);

		registerLabel.setText("注册账号");
		forgetLabel.setText("忘记密码");
		//
		registerLabel.setLayoutX(340);
		registerLabel.setLayoutY(20);
		forgetLabel.setLayoutX(340);
		forgetLabel.setLayoutY(50);

		registerLabel.setCursor(Cursor.HAND);
		forgetLabel.setCursor(Cursor.HAND);

		registerLabel.setStyle("-fx-text-fill: #55A8E7;");
		forgetLabel.setStyle("-fx-text-fill: #55A8E7;");

		addAccountButton.setId("add-account-button");
		towCodeButton.setId("tow-code-button");

		addAccountButton.setLayoutX(10);
		addAccountButton.setLayoutY(120);
		addAccountButton.setPrefHeight(24);
		addAccountButton.setPrefWidth(24);

		towCodeButton.setLayoutX(398);
		towCodeButton.setLayoutY(120);
		towCodeButton.setPrefHeight(24);
		towCodeButton.setPrefWidth(24);

		Button accountButton = new Button();
		accountButton.setId("combo-box-button");
		accountButton.setPrefHeight(20);
		accountButton.setPrefWidth(20);
		accountButton.setLayoutX(170);
		accountButton.setLayoutY(4);

		accountButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (null != autoCompletionPopup) {
					autoCompletionPopup.show(accountField);
				}
				event.consume();
			}
		});

		AnchorPane accountPane = new AnchorPane();
		accountPane.setId("account-input");
		accountPane.setLayoutX(135);
		accountPane.setLayoutY(15);
		accountPane.setPrefWidth(194);
		accountPane.getChildren().add(accountField);
		accountPane.getChildren().add(accountButton);

		autoCompletionPopup.setPrefWidth(accountPane.getPrefWidth());

		AnchorPane passwordButton = new AnchorPane();
		passwordButton.setId("password-button");
		passwordButton.setPrefHeight(16);
		passwordButton.setPrefWidth(15);
		passwordButton.setPrefSize(15, 15);
		passwordButton.setLayoutX(173);
		passwordButton.setLayoutY(6);

		popup.setAutoHide(true);
		popup.setOnKeyboardCloseButton(e -> popup.hide());
		passwordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				passwordField.requestFocus();
				popup.show(LoginFrame.this, LoginFrame.this.getX() + 110, LoginFrame.this.getY() + 280);
				event.consume();
			}
		});

		AnchorPane passwordPane = new AnchorPane();
		passwordPane.setId("password-input");
		passwordPane.setLayoutX(135);
		passwordPane.setLayoutY(44);
		passwordPane.setPrefWidth(194);
		passwordPane.setPrefHeight(30);
		passwordPane.getChildren().add(passwordField);
		passwordPane.getChildren().add(passwordButton);

		accountPane.setOnMouseEntered((Event event) -> {
			accountPane.toFront();
		});

		passwordPane.setOnMouseEntered((Event event) -> {
			passwordPane.toFront();
		});

		userPane.getChildren().addAll(accountPane, passwordPane);
		userPane.getChildren().addAll(loginButton);

		userPane.getChildren().addAll(rememberCheckBox);
		userPane.getChildren().addAll(autoCheckBox);

		userPane.getChildren().addAll(addAccountButton);
		userPane.getChildren().addAll(towCodeButton);

		userPane.getChildren().addAll(registerLabel);
		userPane.getChildren().addAll(forgetLabel);

		statusImageView.setImage(onlineImage);
		statusImageView.setLayoutY(3);
		statusImageView.setLayoutX(3);

		statusButton.setId("status-button");
		statusButton.setPrefHeight(13);
		statusButton.setPrefWidth(13);
		statusButton.setLayoutX(62);
		statusButton.setLayoutY(62);

		statusButton.getChildren().add(statusImageView);

		headClip.setArcHeight(8);
		headClip.setArcWidth(8);

		headClip.setWidth(80);
		headClip.setHeight(80);

		imageHeadView.setFitWidth(80);
		imageHeadView.setFitHeight(80);

		imageHeadView.getStyleClass().add("image-head-view");
		imageHeadView.setClip(headClip);

		AnchorPane headPane = new AnchorPane();

		headPane.setLayoutY(15);
		headPane.setLayoutX(40);

		headPane.getChildren().add(imageHeadView);
		headPane.getChildren().add(statusButton);

		userPane.getChildren().add(headPane);

		headButton.setGraphic(imageHeadView);
		headButton.setPrefWidth(80);
		headButton.setPrefHeight(80);

		waitingPanel.add(WaitingPane.show_waiting, "登录中...",
				ImageBox.getImageClassPath("/resources/common/images/loading/loading_312_4.gif"));
		waitingPanel.show(WaitingPane.show_waiting);

		waitingPanel.setPrefWidth(428);
		waitingPanel.setPrefHeight(150);
		waitingPanel.setStyle("-fx-background-color:#ebf2f9;");

		centerPane.getChildren().add(waitingPanel);
		centerPane.getChildren().add(userPane);

		mp.setPrefWidth(431);
		mp.setPrefHeight(280);

		this.setOnHidden(h -> {
			mp.stop();
		});
		this.setOnShowing(s -> {
			mp.play();
		});

		// topImageView.setEffect(dropShadow);

		VBox backgroundImageBox = new VBox();
		backgroundImageBox.getChildren().add(backgroundImageView);

		StackPane background = new StackPane();
		background.getChildren().add(backgroundImageBox);
		background.getChildren().add(mp);

		VBox rootBox = new VBox();
		rootBox.setBackground(Background.EMPTY);

		rootBox.getChildren().add(topRootPane);
		rootBox.getChildren().add(centerPane);

		baseStackPane.getChildren().add(background);
		baseStackPane.getChildren().add(rootBox);

	}

	/**
	 * 初始化各事件
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年5月25日 下午5:56:45
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午5:56:45
	 */
	private void iniEvent() {
		autoCompletionPopup.setOnSuggestion(sce -> {
			autoCompletionPopup.hide();
			if (null != accountAction) {
				accountAction.execute(sce.getSuggestion());
			}
		});

		autoCompletionPopup.setOnRemove(sce -> {
			autoCompletionPopup.hide();
			if (null != removeAction) {
				removeAction.execute(sce.getRemove());
			}
		});

		statusButton.setOnMouseClicked((Event event) -> {
			menu.show(LoginFrame.this, LoginFrame.this.getX() + 110, LoginFrame.this.getY() + 280);
		});
		// accountField.setOnKeyPressed(k->{
		// if ( k.getCode() == KeyCode.TAB) {
		// passwordField.selectEnd();
		// k.consume();
		// }
		//
		// });
		// accountField.requestFocus();
		// accountField.setOnKeyReleased(k -> {
		// System.out.println(0000);
		// if (k.getCode() == KeyCode.TAB) {
		// passwordField.requestFocus();
		// System.out.println(1111);
		// // passwordField.selectPositionCaret(0);
		// // k.consume();
		// }
		// });
		// passwordField.setOnKeyReleased(k -> {
		// // System.out.println(0000);
		// if (k.getCode() == KeyCode.TAB) {
		// accountField.requestFocus();
		// // System.out.println(1111);
		// // passwordField.selectPositionCaret(0);
		// // k.consume();
		// }
		// });

		this.addEventHandler(KeyEvent.KEY_RELEASED, k -> {
			if (k.getCode() == KeyCode.TAB) {
				tab();
			} else if (k.getCode() == KeyCode.ENTER) {
				if(null!=loginAction) {
					ActionEvent event=new ActionEvent(loginButton,loginButton);
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
			accountField.requestFocus();
		}
		isAccountFocused = !isAccountFocused;
	}

	private void initContextMenu() {
		//////////////////////////////////////

		ImageView iv = new ImageView();
		iv.setImage(onlineImage);
		MenuItem menuItem = new MenuItem("我在线上", iv);
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
		menuItem = new MenuItem("Q我吧", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(callMeImage);
				status =  "2";
			}
		});

		iv = new ImageView();
		iv.setImage(awayImage);
		menuItem = new MenuItem("离开", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(awayImage);
				status =  "3";
			}
		});

		iv = new ImageView();
		iv.setImage(busyImage);
		menuItem = new MenuItem("忙碌", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(busyImage);
				status =  "4";
			}
		});

		iv = new ImageView();
		iv.setImage(muteImage);
		menuItem = new MenuItem("请勿打扰", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(muteImage);
				status =  "5";
			}
		});

		iv = new ImageView();
		iv.setImage(invisibleImage);
		menuItem = new MenuItem("隐身", iv);
		menu.getItems().add(menuItem);
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				statusImageView.setImage(invisibleImage);
				status =  "6";
			}
		});
	}

	private void initSet() {
		this.setHeadSize(80);
		this.setHeadRadius(80);
		Image image = ImageBox.getImageClassPath("/resources/login/images/default/2.png");
		setHeadImage(image);
		Image backgroundIamge = ImageBox.getImageClassPath("/resources/login/images/default/b-1.jpg");
		setBackgroundImage(backgroundIamge);
		Image logoIamge = new Image(this.getClass().getResource("/resources/login/logo_1.png").toExternalForm(), true);
		setLogoImageView(logoIamge);
	}

	public boolean verify() {
		String account = accountField.getText();
		String password = passwordField.getText();

		boolean mark = true;
		if (null == account || "".equals(account)) {
			accountOverLabel.setText("请输入您的账号登录!");
			accountOver.show(accountField);
			accountField.requestFocus();
			mark = false;
			return mark;
		} else {
			mark = true;
		}
		if (null == password || "".equals(password)) {
			passwordOverLabel.setText("请输入您的密码登录!");
			passwordOver.show(passwordField);
			if (mark) {
				passwordField.requestFocus();
			}
			mark = false;
		}
		return mark;
	}

	public void setList(List<Object> list, StringConverter<Object> converter) {
		autoCompletionPopup.getSuggestions().setAll(list);
		autoCompletionPopup.setConverter(converter);
		// StringConverter<Object> stringConverter = new
		// StringConverter<Object>() {
		// @Override
		// public String toString(Object t) {
		// return t == null ? null : t.toString();
		// }
		//
		// @Override
		// public Object fromString(String string) {
		// return (Object) string;
		// }
		// };
	}

	public void removeAccount(Object object) {
		autoCompletionPopup.getSuggestions().remove(object);
	}

	public void setAccountAction(ExecuteAction accountAction) {
		this.accountAction = accountAction;
	}

	public void setRemoveAction(ExecuteAction removeAction) {
		this.removeAction = removeAction;
	}

	public <T> void addAccountChangeListener(ChangeListener<String> listener) {
		accountField.textProperty().addListener(listener);
	}

	public void showWaiting(boolean show) {
		waitingPanel.setVisible(show);
		userPane.setVisible(!show);
	}

	public void setLoginAction(EventHandler<ActionEvent> value) {
		loginButton.setOnAction(value);
		loginAction=value;
	}

	public void setSettingAction(EventHandler<ActionEvent> value) {
		settingButton.setOnAction(value);
	}

	public void setRegisterOnMouseClicked(EventHandler<MouseEvent> value) {
		registerLabel.setOnMouseClicked(value);
	}

	public void setForgetLOnMouseClicked(EventHandler<MouseEvent> value) {
		forgetLabel.setOnMouseClicked(value);
	}

	public String getStatus() {
		return status;
	}

	public void setLogoImageView(Image image) {
		logoImageView.setImage(image);
	}

	public void setBackgroundImage(Image image) {
		backgroundImageView.setImage(image);
	}

	public void setHeadImage(Image image) {
		imageHeadView.setImage(image);
	}

	public void setHeadSize(double value) {
		headClip.setWidth(value);
		headClip.setHeight(value);

		imageHeadView.setFitWidth(value);
		imageHeadView.setFitHeight(value);
	}

	public void setHeadRadius(double value) {
		headClip.setArcHeight(value);
		headClip.setArcWidth(value);
	}

	public String getAccount() {
		return accountField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public void setAccount(String value) {
		accountField.setText(value);
	}

	public void setPassword(String value) {
		passwordField.setText(value);
	}

	public void setVideo(File file) {
		try {
			String pathString = file.toURI().toURL().toString();
			mp.setUrl(pathString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
