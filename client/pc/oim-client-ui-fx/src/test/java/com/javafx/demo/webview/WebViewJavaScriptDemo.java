/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.webview;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewJavaScriptDemo extends Application {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(WebViewJavaScriptDemo.class, args);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("Hello World");

		WebView webView = new WebView();
//		WebPage webPage = null;
		WebEngine webEngine = webView.getEngine();

		
//		JSObject window = (JSObject) webEngine.executeScript("window");
//		window.setMember("app", new JavaApplication());
//		webPage = Accessor.getPageFor(webEngine);
//		webPage.setJavaScriptEnabled(true);
//		webPage.setEditable(false);
//		webPage.setContextMenuEnabled(false);
//		webView.setFocusTraversable(true);

		StringBuilder html = new StringBuilder();
		html.append("<html>");
		html.append("	<head>");
		html.append("	<title></title>");
		html.append("		<meta charset=\"UTF-8\">");
		html.append("		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		html.append("		<script type=\"text/javascript\">");
		html.append(createLastJavaScript());
		html.append(createCheckElementJavaScript());
		html.append(createBodyJavaScript());
		html.append("		</script>");

		html.append("	</head>");
		html.append("	<body style=\"word-wrap:break-word;\" >");

		html.append("		<a href=\"app.exit();\" onclick=\"app.exit()\">Click here to exit application</a>");
		html.append("		<a href=\"javascript:void()\" onclick=\"app.show('hhh')\">hhh</a>");

		html.append("	</body>");
		html.append("</html>");
		
		
		webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {
			System.out.println("loadWorker");
			if (newState == State.SUCCEEDED) {
				System.out.println("SUCCEEDED");
			}
		});
		webEngine.loadContent(html.toString(), "text/html");
	
		Group root = new Group();
		Scene scene = new Scene(root, 1000, 550, Color.LIGHTBLUE);

		root.getChildren().add(webView);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	private StringBuilder createLastJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("			function insertLastHtml(html) {");
		js.append("				var element = document.createElement('div');");
		js.append("				element.innerHTML = html;");
		js.append("				document.body.appendChild(element);");
		js.append("			}");
		return js;
	}

	private StringBuilder createBodyJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("			function setBodyHtml(html) {");
		js.append("				document.body.innerHTML = html;");
		js.append("			}");
		return js;
	}

	private StringBuilder createCheckElementJavaScript() {
		StringBuilder js = new StringBuilder();

		js.append("function checkElement() {");
		js.append("    var max = 500;");
		js.append("    var nodes = document.body.children;");
		js.append("    var length = nodes.length;");
		js.append("    if (length > max) {");
		js.append("        var size = length - max;");
		js.append("        for (var i = 0; i < size; i++) {");
		js.append("            document.body.removeChild(document.body.firstElementChild);");
		js.append("        }");
		js.append("   }");
		js.append("}");
		return js;
	}
}
