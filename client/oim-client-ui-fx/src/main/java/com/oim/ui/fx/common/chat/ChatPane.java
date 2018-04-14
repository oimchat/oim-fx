package com.oim.ui.fx.common.chat;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

/**
 * @author: XiaHui
 * @date: 2017-12-12 20:27:04
 */
public class ChatPane extends StackPane {

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	private final BorderPane baseBorderPane = new BorderPane();

	private final Label textLabel = new Label();
	private final HBox topRightButtonBox = new HBox();
	private final ChatSplitPane splitPane = new ChatSplitPane();
	private final ChatShowPane chatShowPane = new ChatShowPane();
	private final ChatWritePane chatWritePane = new ChatWritePane();

	private final BorderPane writeBorderPane = new BorderPane();

	private final HBox middleToolBarBox = new HBox();
	private final HBox bottomButtonBox = new HBox();

	private Button sendButton = new Button();

	public ChatPane() {
		initComponent();
		iniEvent();

	}

	private void initComponent() {

		textLabel.setStyle("-fx-font-size: 16px;");

		HBox line = new HBox();
		line.setMinHeight(1);
		line.setStyle("-fx-background-color:#d6d6d6;");

		middleToolBarBox.setPadding(new Insets(10, 0, 0, 20));
		middleToolBarBox.setMinHeight(25);
		middleToolBarBox.setSpacing(10);
		middleToolBarBox.setAlignment(Pos.CENTER_LEFT);

		VBox writeTempBox = new VBox();

		writeTempBox.getChildren().add(line);
		writeTempBox.getChildren().add(middleToolBarBox);

		chatShowPane.setStyle("-fx-background-color:#f5f5f5;");
		writeBorderPane.setStyle("-fx-background-color:#ffffff;");

		writeBorderPane.setTop(writeTempBox);
		writeBorderPane.setCenter(chatWritePane);

		splitPane.setTop(chatShowPane);
		splitPane.setBottom(writeBorderPane);
		splitPane.setPrefWidth(780);

		HBox textHBox = new HBox();
		textHBox.setPadding(new Insets(28, 0, 15, 28));
		textHBox.setAlignment(Pos.CENTER_LEFT);
		textHBox.getChildren().add(textLabel);

		topRightButtonBox.setAlignment(Pos.CENTER_RIGHT);
		topRightButtonBox.setPadding(new Insets(25, 20, 0, 0));

		VBox topRightButtonVBox = new VBox();
		topRightButtonVBox.setAlignment(Pos.CENTER);
		topRightButtonVBox.getChildren().add(topRightButtonBox);

		BorderPane topBorderPane = new BorderPane();
		topBorderPane.setCenter(textHBox);
		topBorderPane.setRight(topRightButtonVBox);

		HBox topLine = new HBox();
		topLine.setMinHeight(1);
		topLine.setStyle("-fx-background-color:#d6d6d6;");

		VBox topVBox = new VBox();
		topVBox.setPadding(new Insets(0, 0, 0, 0));
		topVBox.getChildren().add(topBorderPane);
		topVBox.getChildren().add(topLine);

		sendButton.setText("发送");

		sendButton.setPrefSize(72, 24);
		sendButton.setFocusTraversable(false);

		bottomButtonBox.setStyle("-fx-background-color:#ffffff;");
		bottomButtonBox.setPadding(new Insets(0, 15, 0, 0));
		bottomButtonBox.setSpacing(5);
		bottomButtonBox.setAlignment(Pos.CENTER_RIGHT);
		bottomButtonBox.setMinHeight(40);
		bottomButtonBox.getChildren().add(sendButton);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(splitPane);
		borderPane.setBottom(bottomButtonBox);

		baseBorderPane.setTop(topVBox);
		baseBorderPane.setCenter(borderPane);

		this.getChildren().add(baseBorderPane);
	}

	private void iniEvent() {
		splitPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			e.consume();
		});
	}

	public ChatShowPane getChatShowPane() {
		return chatShowPane;
	}

	public ChatWritePane getChatWritePane() {
		return chatWritePane;
	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	public void setText(String value) {
		textLabel.setText(value);
	}

	public void addMiddleTool(Node node) {
		middleToolBarBox.getChildren().add(node);
	}

	public void addTopRightTool(Node node) {
		topRightButtonBox.getChildren().add(node);
	}

	public void setRight(Node value) {
		baseBorderPane.setRight(value);
	}

	public void setOnSendAction(EventHandler<ActionEvent> value) {
		sendButton.setOnAction(value);
	}

	public void setOnWriteKeyReleased(EventHandler<? super KeyEvent> value) {
		WebView webView = chatWritePane.getWebView();
		webView.setOnKeyReleased(value);
	}
}
