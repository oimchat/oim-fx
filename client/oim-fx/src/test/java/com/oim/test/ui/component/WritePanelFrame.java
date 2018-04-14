/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import java.io.File;
import java.util.ArrayList;

import com.oim.fx.common.component.BaseStage;
import com.oim.fx.ui.chat.WritePanel;
import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Only
 */
public class WritePanelFrame extends BaseStage {

	private int fontSize = 12;
	private ColorPicker colorPicker = new ColorPicker(Color.BLACK);

	VBox box = new VBox();

	WritePanel writePanel = new WritePanel();

	Button button = new Button("插入");
	
	Button insertImageButton = new Button("插入图片");
	ComboBox<String> fontFamilyComboBox = new ComboBox<String>();
	Button fontSizeA = new Button("fontSize+");
	Button fontSizeD = new Button("fontSize-");
	Button boldButton = new Button("bold");
	Button underlineButton = new Button("underline");
	Button italicButton = new Button("italic");
	Button htmlButton = new Button("html");

	TextArea textArea = new TextArea();

	// WebPage webPage;
	// WebEngine webEngine;
	public WritePanelFrame() {
		init();
		initEvent();
	}

	private void init() {
		this.setBackground("Resources/Images/Wallpaper/18.jpg");
		this.setTitle("登录");
		this.setWidth(440);
		this.setHeight(360);
		this.setCenter(box);

		box.setStyle("-fx-background-color:rgba(255, 255, 255, 0.2)");


		insertImageButton.setFocusTraversable(false);
		fontFamilyComboBox.setFocusTraversable(false);
		button.setFocusTraversable(false);
		fontSizeA.setFocusTraversable(false);
		fontSizeD.setFocusTraversable(false);
		boldButton.setFocusTraversable(false);
		underlineButton.setFocusTraversable(false);
		italicButton.setFocusTraversable(false);
		htmlButton.setFocusTraversable(false);

		TilePane tilePane = new TilePane();
		tilePane.setPrefColumns(3); // preferred columns
		tilePane.setAlignment(Pos.CENTER);

		
		
		
		tilePane.getChildren().add(insertImageButton);
		tilePane.getChildren().add(button);
		tilePane.getChildren().add(colorPicker);
		tilePane.getChildren().add(fontSizeA);
		tilePane.getChildren().add(fontFamilyComboBox);
		tilePane.getChildren().add(fontSizeD);
		tilePane.getChildren().add(boldButton);
		tilePane.getChildren().add(underlineButton);
		tilePane.getChildren().add(italicButton);
		tilePane.getChildren().add(htmlButton);

		Button gap = new Button("gap");

		box.getChildren().add(gap);
		box.getChildren().add(tilePane);
		box.getChildren().add(writePanel);
		box.getChildren().add(textArea);

		// comboBox.setItems("");
		ObservableList<String> fonts = new ObservableListWrapper<String>(new ArrayList<String>()); // FXCollections.observableArrayList(Font.getFamilies());
		fontFamilyComboBox.setItems(fonts);
		// for (String fontFamily : fonts) {
		// if (DEFAULT_OS_FONT.equals(fontFamily)) {
		// fontFamilyComboBox.setValue(fontFamily);
		// }
		//
		// }

		fonts.add("宋体");
		fonts.add("小篆");
		fonts.add("Microsoft YaHei");
		fonts.add("Helvetica");
		fonts.add("TimesRoman");
		fonts.add("Courier");
		fonts.add("Helvetica");
		fonts.add("TimesRoman");
																														// "\"
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initEvent() {
		colorPicker.setOnAction(new EventHandler() {

			@Override
			public void handle(Event t) {
				Color newColor = colorPicker.getValue();
				writePanel.setColor(newColor);

			}
		});

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				writePanel.insert("hhhh");
			}
		});

		fontFamilyComboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String font = fontFamilyComboBox.getValue().toString();
				System.out.println(font);
				if (null != font) {
					writePanel.setFontName(font);
				}
			}

		});

		fontFamilyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			writePanel.setFontName(newValue);
		});
		fontSizeA.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fontSize++;
				String size = fontSize + "px";
				System.out.println(size);
				writePanel.setFontSize(fontSize);
			}
		});

		fontSizeD.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fontSize--;
				String size = fontSize + "px";
				System.out.println(size);
				writePanel.setFontSize(fontSize);
			}
		});

		boldButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				writePanel.setBold(!writePanel.isBold());
			}
		});

		underlineButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				writePanel.setUnderline(!writePanel.isUnderline());
			}
		});

		italicButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				writePanel.setItalic(!writePanel.isItalic());
			}
		});

		htmlButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(writePanel.getHtml());
			}
		});
		insertImageButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				insertImage();
			}
		});
	}

	File file = new File("Resources/Images/Head/User/90_100.gif");
	String fullPath = file.getAbsolutePath();
	public void insertImage() {

		//String html = "<img src=\"file:/" + fullPath.replace("\\", "/") + "\" />";
		 String html =
		 "<img alt=\"Embedded Image\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAEeSURBVDhPbZFba8JAEIXz//9BoeCD4GNQAqVQBUVKm97UeMFaUmvF1mqUEJW0NXV6mtlL3PRwILuz52NnJxax5iGdt8l2TaP4shCZVBI4ezCjypVbkUklASN07NftlzMNh2GMYA6Iv0UFksWiH5w8Lk5HS9QkgHv5GAtuGq+SAKIAYJQl4E3UMTyYBLFzp7at1Q7p5scGQQlAszVdeJwYV7sqTa2xCKTKAET+dK1zysfSAEZReFqaadv1GsNi/53fAFv8KT2vkMYiKd8YADty7ptXvgaU3cuREc26Xx+YQPUtQnud3kznapkB2K4JJIcDgOBzr3uDkh+6Fjdb9XmUBf6OU3Fv6EHsIcy91hVTygP5CksA6uejGa78DxD9ApzMoGHun6uuAAAAAElFTkSuQmCC\" />";
		writePanel.insertSelectionHtml(html);;
	}
}
