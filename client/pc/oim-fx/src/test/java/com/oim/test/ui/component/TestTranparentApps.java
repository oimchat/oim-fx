package com.oim.test.ui.component;

/**
 * @author: XiaHui
 * @date: 2016年9月2日 上午11:08:31
 */
import java.lang.reflect.Field;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;

public class TestTranparentApps extends Application {

	@Override
	public void start(Stage primaryStage) {
		new WebPage(primaryStage);
		primaryStage.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	class WebPage {

		WebView webview;
		WebEngine webengine;

		public WebPage(Stage mainstage) {
			webview = new WebView();
			webengine = webview.getEngine();

			Scene scene = new Scene(webview);
			scene.setFill(null);

			mainstage.setScene(scene);
			mainstage.initStyle(StageStyle.TRANSPARENT);
			mainstage.setWidth(700);
			mainstage.setHeight(100);

			webengine.documentProperty().addListener(new DocListener());
			webengine.loadContent("<body style='background : rgba(0,0,0,0);font-size: 70px;text-align:center;'>Test Transparent</body>");
		}

		class DocListener implements ChangeListener<Document> {
			@Override
			public void changed(ObservableValue<? extends Document> observable, Document oldValue, Document newValue) {
				try {

					// Use reflection to retrieve the WebEngine's private 'page'
					// field.
					Field f = webengine.getClass().getDeclaredField("page");
					f.setAccessible(true);
					com.sun.webkit.WebPage page = (com.sun.webkit.WebPage) f.get(webengine);
					page.setBackgroundColor((new java.awt.Color(0, 0, 0, 0)).getRGB());

				} catch (Exception e) {
				}

			}
		}
	}
}