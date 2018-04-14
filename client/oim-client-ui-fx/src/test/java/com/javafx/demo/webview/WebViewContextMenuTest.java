package com.javafx.demo.webview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewContextMenuTest extends Application {

	private final String START_URL = "www.baidu.com";
	WebView webView = new WebView();

	@Override
	public void start(Stage primaryStage) {
		TextField locationField = new TextField(START_URL);

		webView.getEngine().load(START_URL);

		webView.setContextMenuEnabled(false);
		createContextMenu(webView);

		locationField.setOnAction(e -> {
			webView.getEngine().load(getUrl(locationField.getText()));
		});
		BorderPane root = new BorderPane(webView, locationField, null, null, null);
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();

	}

	private void createContextMenu(WebView webView) {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem reload = new MenuItem("Reload");
		reload.setOnAction(e -> webView.getEngine().reload());
		MenuItem savePage = new MenuItem("Save Page");
		savePage.setOnAction(e -> System.out.println("Save page..."));
		MenuItem hideImages = new MenuItem("Hide Images");
		hideImages.setOnAction(e -> System.out.println("Hide Images..."));
		contextMenu.getItems().addAll(reload, savePage, hideImages);

		webView.setOnMousePressed(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				contextMenu.show(webView, e.getScreenX(), e.getScreenY());
			} else {
				contextMenu.hide();
			}
		});
	}

	private String getUrl(String text) {
		if (text.indexOf("://") == -1) {
			return "http://" + text;
		} else {
			return text;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public class App {
		public void cut() {
			String selectedText = (String) webView.getEngine().executeScript("editor.getSelection();");
			webView.getEngine().executeScript("editor.replaceSelection(\"\");");
			final Clipboard clipboard = Clipboard.getSystemClipboard();
			final ClipboardContent content = new ClipboardContent();
			content.putString(selectedText);
			clipboard.setContent(content);

		}

		public void copy() {
			String selectedText = (String) webView.getEngine().executeScript("editor.getSelection();");
			final Clipboard clipboard = Clipboard.getSystemClipboard();
			final ClipboardContent content = new ClipboardContent();
			content.putString(selectedText);
			clipboard.setContent(content);
		}

		public void paste() {
			final Clipboard clipboard = Clipboard.getSystemClipboard();
			String content = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
			webView.getEngine().executeScript(String.format("editor.replaceSelection(\"%s\");", content));
		}
	}
}