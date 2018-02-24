/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.test.control;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WebViewStyleTest extends Application {

	JavaApplication app = new JavaApplication();// 提前实例化，不然有时不起效果

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(WebViewStyleTest.class, args);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("What World");
		
		WebView webView = new WebView();
		WebPage webPage = null;
		WebEngine webEngine = webView.getEngine();

		webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {
			System.out.println("loadWorker");
			if (newState == State.SUCCEEDED) {
				JSObject window = (JSObject) webEngine.executeScript("window");
				window.setMember("app", app);
				System.out.println("app");
			}
		});

		webPage = Accessor.getPageFor(webEngine);
		webPage.setJavaScriptEnabled(true);
		webPage.setEditable(false);
		webPage.setContextMenuEnabled(false);
		webView.setFocusTraversable(true);
		webView.getEngine().load(WebViewStyleTest.class.getResource("WebViewStyleTest.html").toExternalForm());

		BorderPane bp=new BorderPane();
		Scene scene = new Scene(bp);
		scene.getStylesheets().add(this.getClass().getResource("WebView.css").toString());
		bp.setCenter(webView);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public class JavaApplication {//有些jdk需要public才能调用

		public void show(String text) {
			System.out.println(text);
		}

		public void exit() {
			System.out.println("exit");
		}
	}
}
