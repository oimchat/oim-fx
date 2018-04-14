package com.oim.ui.fx.classics.chat;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.ui.fx.common.chat.ChatShowPane;
import com.oim.ui.fx.common.chat.ChatSplitPane;
import com.oim.ui.fx.common.chat.ChatWritePane;
import com.only.fx.common.action.EventAction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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

	private final ChatTopPane chatTopPane = new ChatTopPane();
	private final ChatSplitPane splitPane = new ChatSplitPane();
	private final ChatShowPane chatShowPane = new ChatShowPane();
	private final ChatWritePane chatWritePane = new ChatWritePane();

	private final BorderPane writeBorderPane = new BorderPane();

	private final HBox middleToolBarBox = new HBox();
	private final HBox bottomButtonBox = new HBox();

	private Button sendButton = new Button();
	private EventAction<List<File>> fileEventAction;

	public ChatPane() {
		initComponent();
		iniEvent();

	}

	private void initComponent() {

		HBox line = new HBox();
		line.setMinHeight(1);
		line.setStyle("-fx-background-color:#d6d6d6;");

		middleToolBarBox.setPadding(new Insets(5, 0, 0, 15));
		middleToolBarBox.setMinHeight(25);
		middleToolBarBox.setSpacing(10);
		middleToolBarBox.setAlignment(Pos.CENTER_LEFT);

		VBox writeTempBox = new VBox();

		writeTempBox.getChildren().add(line);
		writeTempBox.getChildren().add(middleToolBarBox);

		// chatShowPane.setStyle("-fx-background-color:#f5f5f5;");
		// writeBorderPane.setStyle("-fx-background-color:#ffffff;");
		chatShowPane.setStyle("-fx-background-color:rgba(250, 250, 250, 0.9)");
		writeBorderPane.setStyle("-fx-background-color:rgba(250, 250, 250, 0.9)");

		writeBorderPane.setTop(writeTempBox);
		writeBorderPane.setCenter(chatWritePane);

		splitPane.setTop(chatShowPane);
		splitPane.setBottom(writeBorderPane);
		splitPane.setPrefWidth(780);

		sendButton.setText("发送");

		sendButton.setPrefSize(72, 24);
		sendButton.setFocusTraversable(false);

		bottomButtonBox.setStyle("-fx-background-color:rgba(250, 250, 250, 0.9)");
		bottomButtonBox.setPadding(new Insets(0, 15, 0, 0));
		bottomButtonBox.setSpacing(5);
		bottomButtonBox.setAlignment(Pos.CENTER_RIGHT);
		bottomButtonBox.setMinHeight(40);
		bottomButtonBox.getChildren().add(sendButton);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(splitPane);
		borderPane.setBottom(bottomButtonBox);

		baseBorderPane.setTop(chatTopPane);
		baseBorderPane.setCenter(borderPane);

		this.getChildren().add(baseBorderPane);
	}

	private void iniEvent() {
		splitPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			e.consume();
		});
		setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != ChatPane.this) {
					event.acceptTransferModes(TransferMode.ANY);
				}
			}
		});
		EventHandler<DragEvent> eh = new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				List<File> files = dragboard.getFiles();
				if (null != files && null != fileEventAction) {
					fileEventAction.execute(files);
				}
				event.consume();
			}
		};
		setOnDragDropped(eh);
		chatShowPane.setWebOnDragDropped(eh);
		chatWritePane.setWebOnDragDropped(eh);
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
		chatTopPane.setText(value);
	}

	public void setName(String value) {
		chatTopPane.setName(value);
	}

	public void addMiddleTool(Node node) {
		middleToolBarBox.getChildren().add(node);
	}

	public void addTopTool(Node node) {
		chatTopPane.addTool(node);
	}

	public void addTopRightTool(Node node) {
		chatTopPane.addRightTool(node);
	}

	public void setRight(Node value) {
		baseBorderPane.setRight(value);
	}

	public void setOnSendAction(EventHandler<ActionEvent> value) {
		sendButton.setOnAction(value);
	}

	public void setOnFileEventAction(EventAction<List<File>> fileEventAction) {
		this.fileEventAction = fileEventAction;
	}

	public void setOnWriteKeyReleased(EventHandler<? super KeyEvent> value) {
		WebView webView = chatWritePane.getWebView();
		webView.setOnKeyReleased(value);
	}
}
