package com.javafx.demo.webview;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
//import javafx.scene.effect.BlendMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewStyle extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new WebViewPane();
		primaryStage.setScene(new Scene(root, 1024, 768));
		primaryStage.show();
		
		primaryStage.getScene().getStylesheets().add(this.getClass().getResource("WebView.css").toString());

	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Create a resizable WebView pane
	 */
	public class WebViewPane extends BorderPane {
		WebView webView = new WebView();
		TextField locationField = new TextField("http://www.baidu.com");
		Button goButton = new Button("Go");

		public WebViewPane() {
			setStyle("-fx-background-color:rgba(55, 155, 255, 1)");
			webView.setMinSize(500, 400);
			webView.setPrefSize(500, 400);
			//WebPage webPage = Accessor.getPageFor(webEngine);
			//webPage.setEditable(true);
			//webView.setBlendMode(BlendMode.DARKEN);// 透明
			HBox hBox = new HBox();
			goButton.setDefaultButton(true);
			locationField.setMaxHeight(Double.MAX_VALUE);

			hBox.getChildren().add(locationField);
			hBox.getChildren().add(goButton);
			this.setTop(hBox);
			this.setCenter(webView);
			initEvent();
		}

		private void initEvent() {

			final WebEngine webEngine = webView.getEngine();

			webEngine.locationProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					locationField.setText(newValue);
				}
			});
			webEngine.locationProperty().addListener(e -> {
				System.out.println(webEngine.getLocation());
			});

			EventHandler<ActionEvent> goAction = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					webEngine.load(locationField.getText().startsWith("http://") ? locationField.getText() : "http://" + locationField.getText());
				}
			};

			locationField.setOnAction(goAction);
			goButton.setOnAction(goAction);

			//webEngine.load("http://www.baidu.com");
			webView.getEngine().load(WebViewStyle.class.getResource("ScriptToJava.html").toExternalForm());

		}
	}
}