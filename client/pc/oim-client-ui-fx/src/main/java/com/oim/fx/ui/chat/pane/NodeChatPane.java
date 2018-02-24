package com.oim.fx.ui.chat.pane;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.ui.chat.TopPanel;
import com.only.fx.common.action.EventAction;
import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NodeChatPane extends StackPane {

	BorderPane rootPane = new BorderPane();

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	private TopPanel topPanel = new TopPanel();

	private SplitPane splitPane = new SplitPane();
	private StackPane topPane = new StackPane();
	private StackPane rightPane = new StackPane();

	private VBox showBox = new VBox();

	BorderPane writeTopPane = new BorderPane();
	BorderPane writePane = new BorderPane();

	private MessageList messageList = new MessageList();

	private HBox middleToolBarBox = new HBox();
	private HBox bottomButtonBox = new HBox();

	private boolean showFontBox = false;
	private HBox fontBox = new HBox();

	private TextArea textArea = new TextArea();

	private Button sendButton = new Button();
	private Button closeButton = new Button();

	private HBox rightFunctionBox = new HBox();

	private EventAction<File> fileEventAction;

	private String fontName = "Microsoft YaHei";
	private int fontSize = 12;
	private Color color = Color.BLACK;
	private boolean bold = false;
	private boolean underline = false;
	private boolean italic = false;

	public NodeChatPane() {
		initComponent();
		initMiddleToolBar();
		iniEvent();
		initFontBox();
	}

	private void initComponent() {
		this.getChildren().add(rootPane);

		topPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.7)");
		topPane.setPrefHeight(80);
		topPane.getChildren().add(topPanel);

		//rightPane.setPrefWidth(140);
		//rightPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.5)");

		showBox.getChildren().add(messageList);
		splitPane.setId("hiddenSplitter");
		HBox line = new HBox();
		line.setMinHeight(1);
		line.setStyle("-fx-background-color:rgba(180, 180, 180, 1);");

		middleToolBarBox.setMinHeight(25);
		middleToolBarBox.setSpacing(8);
		middleToolBarBox.setAlignment(Pos.CENTER_LEFT);

		HBox functionRootBox = new HBox();
		functionRootBox.setAlignment(Pos.CENTER_RIGHT);

		functionRootBox.getChildren().add(middleToolBarBox);
		functionRootBox.getChildren().add(rightFunctionBox);

		HBox.setHgrow(middleToolBarBox, Priority.ALWAYS);

		fontBox.setMinHeight(25);
		fontBox.setSpacing(2);

		closeButton.setText("关闭");
		sendButton.setText("发送");

		closeButton.setPrefSize(72, 24);
		sendButton.setPrefSize(72, 24);

		closeButton.setFocusTraversable(false);
		sendButton.setFocusTraversable(false);

		bottomButtonBox.setPadding(new Insets(0, 15, 0, 0));
		bottomButtonBox.setSpacing(5);
		bottomButtonBox.setAlignment(Pos.CENTER_RIGHT);
		bottomButtonBox.setMinHeight(40);

		bottomButtonBox.getChildren().add(closeButton);
		bottomButtonBox.getChildren().add(sendButton);

		textArea.setBorder(Border.EMPTY);
		textArea.getStyleClass().clear();
		textArea.setWrapText(true);

		VBox writeTempBox = new VBox();

		writeTempBox.getChildren().add(line);
		writeTempBox.getChildren().add(functionRootBox);

		writeTopPane.setCenter(writeTempBox);

		// writePane.setPrefHeight(150);

		writePane.setTop(writeTopPane);
		writePane.setCenter(textArea);
		writePane.setBottom(bottomButtonBox);

		// splitPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

		splitPane.getItems().add(messageList);
		splitPane.getItems().add(writePane);
		splitPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");

		// splitPane.setDividerPosition(0,0.8f);
		splitPane.setDividerPositions(0.7f, 0.3f);
		splitPane.setOrientation(Orientation.VERTICAL);

		rootPane.setTop(topPane);
		rootPane.setCenter(splitPane);
		rootPane.setRight(rightPane);
	}

	public MessageList getMessageList() {
		return messageList;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public Button getSendButton() {
		return sendButton;
	}

	/**
	 * 初始化中间工具按钮
	 */
	private void initMiddleToolBar() {
		// 字体设置按钮
		Image normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_font.png");
		Image hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_font_hover.png");
		Image pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_font_hover.png");

		IconPane iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				showFontBox = !showFontBox;
				showFontBox(showFontBox);
			}
		});
		this.addMiddleTool(iconButton);
	}

	private void initFontBox() {

		ComboBox<String> fontFamilyComboBox = new ComboBox<String>();
		fontFamilyComboBox.setFocusTraversable(false);
		ObservableList<String> fonts = new ObservableListWrapper<String>(new ArrayList<String>()); // FXCollections.observableArrayList(Font.getFamilies());
		fontFamilyComboBox.setItems(fonts);
		fonts.add("微软雅黑");
		fonts.add("宋体");
		fonts.add("小篆");
		fonts.add("Helvetica");
		fonts.add("TimesRoman");
		fonts.add("Courier");
		fonts.add("Helvetica");
		fonts.add("TimesRoman");

		fontFamilyComboBox.setValue("微软雅黑");
		fontFamilyComboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String font = fontFamilyComboBox.getValue().toString();
				if (null != font) {
					setFontName(font);
				}
			}
		});

		fontBox.getChildren().add(fontFamilyComboBox);
		//////////////
		ComboBox<Integer> fontSizeComboBox = new ComboBox<Integer>();
		fontSizeComboBox.setFocusTraversable(false);
		ObservableList<Integer> fontSizes = new ObservableListWrapper<Integer>(new ArrayList<Integer>()); // FXCollections.observableArrayList(Font.getFamilies());
		fontSizeComboBox.setItems(fontSizes);
		for (int i = 8; i < 23; i++) {
			fontSizes.add(i);
		}
		fontSizeComboBox.setValue(12);
		fontSizeComboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Integer fontSize = fontSizeComboBox.getValue();
				if (null != fontSize) {
					setFontSize(fontSize);
				}
			}
		});

		fontBox.getChildren().add(fontSizeComboBox);
		///////////////////
		Image normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_bold_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_bold_highlight.png");
		Image pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_bold_push.png");

		IconPane iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setBold(!isBold());
			}
		});

		fontBox.getChildren().add(iconButton);
		////////////////////
		normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_italic_normal.png");
		hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_italic_highlight.png");
		pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_italic_push.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setItalic(!isItalic());
			}
		});
		fontBox.getChildren().add(iconButton);
		/////////////////////////
		normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_underline_normal.png");
		hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_underline_highlight.png");
		pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/font/aio_quickbar_sysfont_underline_push.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setUnderline(!isUnderline());
			}
		});
		fontBox.getChildren().add(iconButton);
		////////////////////
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				Color newColor = colorPicker.getValue();
				setColor(newColor);

			}
		});
		fontBox.getChildren().add(colorPicker);
	}

	public void showFontBox(boolean showFontBox) {
		if (showFontBox) {
			writeTopPane.setTop(fontBox);
		} else {
			writeTopPane.setTop(null);
		}
	}

	private void iniEvent() {
		setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != NodeChatPane.this) {
					event.acceptTransferModes(TransferMode.ANY);
				}
			}
		});
		EventHandler<DragEvent> eh = new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				List<File> files = dragboard.getFiles();
				if (null != files && files.size() > 0) {
					// System.out.println((files.get(0)).getName());
					if (null != fileEventAction) {
						fileEventAction.execute(files.get(0));
					}
				}
				event.consume();
			}
		};
		setOnDragDropped(eh);

		textArea.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			if (e.isShiftDown() && e.getCode() == KeyCode.ENTER) {
				textArea.appendText("\n");
				e.consume();
			}
		});
	}

	public void initializeWriteHtml() {
		textArea.setText("");
	}

	public void setName(String name) {
		topPanel.setName(name);
	}

	public void setText(String text) {
		topPanel.setText(text);
	}

	public void addMiddleTool(Node node) {
		middleToolBarBox.getChildren().add(node);
	}

	public void addMiddleRightTool(Node node) {
		rightFunctionBox.getChildren().add(node);
	}

	public void setRightPane(Node node) {
		rightPane.getChildren().clear();
		rightPane.getChildren().add(node);
	}

	public void setRightPanePrefWidth(double value) {
		rightPane.setPrefWidth(value);
	}

	public void addTopTool(Node value) {
		topPanel.addTool(value);
	}

	public void removeTopTool(Node value) {
		topPanel.removeTool(value);
	}

	public void setSendAction(EventHandler<ActionEvent> value) {
		sendButton.setOnAction(value);
	}

	public void setCloseAction(EventHandler<ActionEvent> value) {
		closeButton.setOnAction(value);
	}

	public void setFileEventAction(EventAction<File> fileEventAction) {
		this.fileEventAction = fileEventAction;
	}

	public String getWriteText() {
		return this.textArea.getText();
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public Color getColor() {
		return color;
	}

	public String getWebColor() {
		return colorValueToHex(color);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	private String colorValueToHex(Color c) {
		return String.format((Locale) null, "%02x%02x%02x", Math.round(c.getRed() * 255),
				Math.round(c.getGreen() * 255), Math.round(c.getBlue() * 255));
	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	public void setShowCloseButton(boolean value) {
		closeButton.setVisible(value);
	}

	public void setOnWriteKeyReleased(EventHandler<? super KeyEvent> value) {
		this.textArea.setOnKeyReleased(value);
	}
}
