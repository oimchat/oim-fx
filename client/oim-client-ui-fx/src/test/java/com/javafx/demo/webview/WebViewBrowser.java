package com.javafx.demo.webview;

/**
 * @author XiaHui
 * @date 2017年6月10日 下午2:35:35
 */
import java.util.List;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewBrowser extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new WebViewPane();
		primaryStage.setScene(new Scene(root, 1024, 768));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Create a resizable WebView pane
	 */
	public class WebViewPane extends Pane {

		public WebViewPane() {
			VBox.setVgrow(this, Priority.ALWAYS);
			setMaxWidth(Double.MAX_VALUE);
			setMaxHeight(Double.MAX_VALUE);

			WebView webView = new WebView();
			webView.setMinSize(500, 400);
			webView.setPrefSize(500, 400);
			final WebEngine webEngine = webView.getEngine();
			webEngine.load("http://www.baidu.com");

			final TextField locationField = new TextField("http://www.baidu.com");
			Button goButton = new Button("Go");

			EventHandler<ActionEvent> goAction = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					webEngine.load(locationField.getText().startsWith("http://") ? locationField.getText() : "http://" + locationField.getText());
				}
			};

			goButton.setDefaultButton(true);
			goButton.setOnAction(goAction);

			locationField.setMaxHeight(Double.MAX_VALUE);
			locationField.setOnAction(goAction);
			
			webEngine.locationProperty().addListener(e->{
				System.out.println(webEngine.getLocation());
			});
			
			webEngine.locationProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					locationField.setText(newValue);
				}
			});
			
			WebPage webPage  = Accessor.getPageFor(webEngine);
			
			webPage.setEditable(true);
			
			GridPane grid = new GridPane();
			grid.setVgap(5);
			grid.setHgap(5);
			GridPane.setConstraints(locationField, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
			GridPane.setConstraints(goButton, 1, 0);
			GridPane.setConstraints(webView, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
			grid.getColumnConstraints().addAll(
					new ColumnConstraints(100, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true),
					new ColumnConstraints(40, 40, 40, Priority.NEVER, HPos.CENTER, true));
			grid.getChildren().addAll(locationField, goButton, webView);
			getChildren().add(grid);
		}

		@Override
		protected void layoutChildren() {
			List<Node> managed = getManagedChildren();
			double width = getWidth();
			double height = getHeight();
			double top = getInsets().getTop();
			double right = getInsets().getRight();
			double left = getInsets().getLeft();
			double bottom = getInsets().getBottom();
			for (int i = 0; i < managed.size(); i++) {
				Node child = managed.get(i);
				layoutInArea(child, left, top,width - left - right, height - top - bottom,0, Insets.EMPTY, true, true, HPos.CENTER, VPos.CENTER);
			}
		}
	}
}