package com.oim.fx.ui.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.fx.common.DataConverter;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.only.fx.common.action.EventAction;
import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

public class SimpleChatPanel extends BorderPane {

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	private ShowSplitPane showSplitPane = new ShowSplitPane();

	private ShowPanel showPanel = new ShowPanel();
	private VBox showBox = new VBox();

	private VBox writeBox = new VBox();
	
	private HBox middleBox = new HBox();
	
	private HBox middleToolBarBox = new HBox();
	private WritePanel writePanel = new WritePanel();
	private HBox bottomButtonBox = new HBox();

	private boolean showFontBox = false;
	private HBox fontBox = new HBox();

	private Button sendButton = new Button();

	HBox rightFunctionBox = new HBox();

	EventAction<File> fileEventAction;

	public SimpleChatPanel() {
		initComponent();
		initMiddleToolBar();
		iniEvent();
		initFontBox();
	}

	private void initComponent() {
		this.setCenter(showSplitPane);

		WebView webView = showPanel.getWebView();
		webView.setPrefWidth(20);
		webView.setPrefHeight(50);

		showSplitPane.setTop(showBox);
		showSplitPane.setBottom(writeBox);
		showSplitPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");

		showBox.getChildren().add(webView);
		VBox.setVgrow(webView, Priority.ALWAYS);

		HBox line = new HBox();
		line.setMinHeight(1);
		line.setStyle("-fx-background-color:rgba(236, 239, 243, 1);");

		middleToolBarBox.setMinHeight(25);
		middleToolBarBox.setSpacing(8);
		middleToolBarBox.setAlignment(Pos.CENTER_LEFT);

		rightFunctionBox.setPadding(new Insets(0, 5, 0, 0));

		

		middleBox.setAlignment(Pos.CENTER_RIGHT);

		middleBox.getChildren().add(middleToolBarBox);
		middleBox.getChildren().add(rightFunctionBox);

		HBox.setHgrow(middleToolBarBox, Priority.ALWAYS);

		VBox writeTempBox = new VBox();

		writeTempBox.getChildren().add(line);
		writeTempBox.getChildren().add(middleBox);

		writeBox.getChildren().add(writeTempBox);
		writeBox.getChildren().add(writePanel);
		writeBox.getChildren().add(bottomButtonBox);

		fontBox.setMinHeight(25);
		fontBox.setSpacing(2);

		bottomButtonBox.setSpacing(5);
		bottomButtonBox.setPadding(new Insets(0, 15, 0, 0));
		bottomButtonBox.setAlignment(Pos.CENTER_RIGHT);
		bottomButtonBox.setMinHeight(40);

		sendButton.setText("发送");

		sendButton.setPrefSize(72, 24);

		sendButton.setFocusTraversable(false);

		bottomButtonBox.getChildren().add(sendButton);
	}

	/**
	 * 初始化中间工具按钮
	 */
	IconPane fontIconButton;

	private void initMiddleToolBar() {
		// 字体设置按钮
		Image normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_font.png");
		Image hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_font_hover.png");
		Image pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_font_hover.png");

		fontIconButton = new IconPane(normalImage, hoverImage, pressedImage);
		fontIconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				showFontBox = !showFontBox;
				showFontBox(showFontBox);
			}
		});
		this.addMiddleTool(fontIconButton);
	}

	public void setFontIconImage(Image normalImage, Image hoverImage, Image pressedImage) {
		fontIconButton.setNormalImage(normalImage);
		fontIconButton.setPressedImage(pressedImage);
		fontIconButton.setHoverImage(hoverImage);
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
					writePanel.setFontName(font);
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
					writePanel.setFontSize(fontSize);
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
				writePanel.setBold(!writePanel.isBold());
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
				writePanel.setItalic(!writePanel.isItalic());
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
				writePanel.setUnderline(!writePanel.isUnderline());
			}
		});
		fontBox.getChildren().add(iconButton);
		////////////////////
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				Color newColor = colorPicker.getValue();
				writePanel.setColor(newColor);

			}
		});
		fontBox.getChildren().add(colorPicker);
	}

	public void showFontBox(boolean showFontBox) {
		if (showFontBox) {
			showBox.getChildren().add(fontBox);
		} else {
			showBox.getChildren().remove(fontBox);
		}
	}

	private void iniEvent() {
		setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != SimpleChatPanel.this) {
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
		showPanel.setWebOnDragDropped(eh);
		writePanel.setWebOnDragDropped(eh);
	}

	public void insertImage(String id, String name, String value, String path) {
		if (null != path && !"".equals(path)) {
			StringBuilder image = new StringBuilder();
			image.append("<img ");
			if (null != id && !"".equals(id)) {
				image.append(" id=\"");
				image.append(id);
				image.append("\"");
			}
			if (null != name && !"".equals(name)) {
				image.append(" name=\"");
				image.append(name);
				image.append("\"");
			}
			if (null != value && !"".equals(value)) {
				image.append(" value=\"");
				image.append(value);
				image.append("\"");
			}
			// image.append(" src=\"file:/");
			// image.append(path.replace("\\", "/"));
			// image.append("\" />");
			String temp = path.replace("\\", "/");
			image.append(" src=\"file:");
			if (temp.startsWith("/")) {

			} else {
				image.append("/");
			}
			image.append(temp);
			image.append("\" ");

			image.append("	style=\" max-width: 60%; height: auto;\" ");
			image.append(" />");

			// String p = "file:";
			// if (temp.startsWith("/")) {
			//
			// } else {
			// p =p+("/");
			// }
			// p =p+temp;
			// writePanel.insertImage(p, id, name, value);
			writePanel.insertSelectionHtml(image.toString());
		}
	}

	public void initializeWriteHtml() {
		writePanel.clearBody();
		// writeRequestFocus();
	}

	public void replaceImage(String id, String src) {
		src = src.replace("\\", "/");
		showPanel.replaceImage(id, src);
	}

	public void addMiddleTool(Node node) {
		middleToolBarBox.getChildren().add(node);
	}

	public void addMiddleRightTool(Node node) {
		rightFunctionBox.getChildren().add(node);
	}

	public void writeRequestFocus() {
		WebView webView = writePanel.getWebView();
		webView.requestFocus();
	}

	public void setOnWriteKeyReleased(EventHandler<? super KeyEvent> value) {
		WebView webView = writePanel.getWebView();
		webView.setOnKeyReleased(value);
	}

	public void setSendAction(EventHandler<ActionEvent> value) {
		sendButton.setOnAction(value);
	}

	public void setFileEventAction(EventAction<File> fileEventAction) {
		this.fileEventAction = fileEventAction;
	}

	public void insertShowLastHtml(String html) {
		showPanel.insertLastHtml(html);
		showPanel.checkElement();
	}

	public void insertShowLastHtml(String name, String head, String time, String orientation, String html) {
		showPanel.insertLastShowHtml(name, head, time, orientation, html);
		showPanel.checkElement();
	}

	public void insertShowBeforeHtml(String name, String head, String time, String orientation, String html) {
		showPanel.insertBeforeShowHtml(name, head, time, orientation, html);
	}

	public void insertShowBeforeHtml(String html) {
		showPanel.insertBeforeHtml(html);
	}

	public String getHtml() {
		return writePanel.getHtml();
	}

	public String getFontName() {
		return writePanel.getFontName();
	}

	public int getFontSize() {
		return writePanel.getFontSize();
	}

	public String getWebColor() {
		return writePanel.getWebColor();
	}

	public boolean isBold() {
		return writePanel.isBold();
	}

	public boolean isUnderline() {
		return writePanel.isUnderline();
	}

	public boolean isItalic() {
		return writePanel.isItalic();
	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	public void setEmojiConverter(DataConverter<String, String> emojiConverter) {
		writePanel.setEmojiConverter(emojiConverter);
	}

	public ShowPanel getShowPanel() {
		return showPanel;
	}

	public WritePanel getWritePanel() {
		return writePanel;
	}

	public HBox getMiddleBox() {
		return middleBox;
	}

	public void insertWriteSelectionHtml(String html) {
		writePanel.insertSelectionHtml(html);
	}

}
