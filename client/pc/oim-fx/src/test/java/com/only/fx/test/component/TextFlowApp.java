package com.only.fx.test.component;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * 
 * Demonstrates some simple uses of rich text and TextFlow.The first example
 * 
 * shows use of text with different fonts in a TextFlow. Use the Playground to
 * 
 * experiment with different Text properties. The second example shows
 * 
 * TextFlow and embedded objects.
 * 
 */

public class TextFlowApp extends Application {

	private static Image image = new Image(TextFlowApp.class.getResource("/ensemble/samples/shared-resources/oracle.png").toExternalForm());

	private TextFlow textFlow;
	private Text textHello;
	private Text textBold;
	private Text textWorld;

	public Parent createContent() {

		String family = "Helvetica";

		double size = 20;

		// Simple example

		textFlow = new TextFlow();
		
		textHello = new Text("Hello ");

		textHello.setFont(Font.font(family, size));
		textBold = new Text("Bold");
		textBold.setFont(Font.font(family, FontWeight.BOLD, size));
		textWorld = new Text(" World");
		textWorld.setFont(Font.font(family, FontPosture.ITALIC, size));
		textFlow.getChildren().addAll(textHello, textBold, textWorld);

		// Example with embedded objects

		TextFlow textFlowEmbedObj = new TextFlow();
		Text textEO1 = new Text("Lets have ");
		textEO1.setFont(Font.font(family, size));
		Text textEO2 = new Text("embedded objects: a Rectangle ");

		textEO2.setFont(Font.font(family, FontWeight.BOLD, size));

		Rectangle rect = new Rectangle(80, 60);

		rect.setFill(null);
		rect.setStroke(Color.GREEN);
		rect.setStrokeWidth(5);
		Text textEO3 = new Text(", then a button ");

		Button button = new Button("click me");
		Text textEO4 = new Text(", and finally an image ");

		ImageView imageView = new ImageView(image);
		Text textEO5 = new Text(".");

		textEO5.setFont(Font.font(family, size));

		textFlowEmbedObj.getChildren().addAll(textEO1, textEO2, rect, textEO3, button, textEO4, imageView, textEO5);

		VBox vbox = new VBox(18);

		VBox.setMargin(textFlow, new Insets(5, 5, 0, 5));

		VBox.setMargin(textFlowEmbedObj, new Insets(0, 5, 5, 5));

		vbox.getChildren().addAll(textFlow, textFlowEmbedObj);
		return vbox;

	}

	@Override

	public void start(Stage primaryStage) throws Exception {

		primaryStage.setScene(new Scene(createContent()));

		primaryStage.show();

	}

	/**
	 * 
	 * Java main for when running without JavaFX launcher
	 * 
	 */

	public static void main(String[] args) {

		launch(args);

	}

}