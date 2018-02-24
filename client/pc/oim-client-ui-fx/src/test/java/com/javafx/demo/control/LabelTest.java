package com.javafx.demo.control;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * @author XiaHui
 * @date 2017年9月28日 上午10:33:03
 */

public class LabelTest extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root, 400, 400);
			Pane p = new Pane();
			p.setPrefSize(400, 400);
			p.setBackground(new Background(new BackgroundFill(Color.GOLD,
					null, null)));
			root.getChildren().add(p);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Conversation about Bubbles with Elltz");
			primaryStage.show();
			Label bl1 = new Label();
			bl1.relocate(10, 50);
			bl1.setText("Hi Elltz -:)");
			bl1.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN,
					null, null)));

			Label bl2 = new Label();
			bl2.relocate(310, 100);
			bl2.setText("Heloooo Me");
			bl2.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW,
					null, null)));

			Label bl3 = new Label();
			bl3.relocate(10, 150);
			bl3.setText("you know this would be a nice library");
			bl3.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN,
					null, null)));

			Label bl4 = new Label();
			bl4.relocate(165, 200);
			bl4.setText("uhmm yea, kinda, but yknow,im tryna \nact like im not impressed");
			bl4.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW,
					null, null)));

			Label bl5 = new Label();
			bl5.relocate(10, 250);
			bl5.setText("yea! yea! i see that, lowkey.. you not gonna\n get upvotes though..lmao");
			bl5.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN,
					null, null)));

			Label bl6 = new Label();
			bl6.relocate(165, 300);
			bl6.setText("Man! shut up!!.. what you know about\n upvotes.");
			bl6.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW,
					null, null)));

			p.getChildren().addAll(bl1, bl2, bl3, bl4, bl5, bl6);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
