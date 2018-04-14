/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.webview;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.LoadListenerClient;
import com.sun.webkit.WebPage;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WebViewUrlDemo extends Application {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(WebViewUrlDemo.class, args);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("Hello World");

		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();

		//
		WebPage webPage = Accessor.getPageFor(webEngine);
		webPage.setJavaScriptEnabled(true);
		// webPage.setEditable(false);
		// webPage.setContextMenuEnabled(false);
		// webView.setFocusTraversable(true);

		webPage.addLoadListenerClient(new LoadListenerClient() {

			@Override
			public void dispatchLoadEvent(long frame, int state, String url, String contentType, double progress, int errorCode) {
				System.out.println("load:" + url);
			}

			@Override
			public void dispatchResourceLoadEvent(long frame, int state, String url, String contentType, double progress, int errorCode) {
				System.out.println("resource:" + url);
			}
		});
		

		webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {
			System.out.println("app");
			if (newState == State.SUCCEEDED) {
				System.out.println("window");
				JSObject window = (JSObject) webEngine.executeScript("window");
				window.setMember("app", new JavaApplication());
			}
		});
		webEngine.load("http://www.baidu.com");
		;
		Group root = new Group();
		Scene scene = new Scene(root, 1000, 550, Color.LIGHTBLUE);
		root.getChildren().add(webView);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public class JavaApplication {
		public void show(String text) {
			System.out.println(text);
		}

		public void exit() {
			System.out.println(1);
		}
	}
}
