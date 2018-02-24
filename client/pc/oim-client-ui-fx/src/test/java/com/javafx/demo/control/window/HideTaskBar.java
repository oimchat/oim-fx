package com.javafx.demo.control.window;

/**
 * @author: XiaHui
 * @date: 2016年10月12日 下午12:53:14
 */
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class HideTaskBar extends Application {

	@Override
	public void start(final Stage stage) throws Exception {
		stage.initStyle(StageStyle.UTILITY);
		stage.setScene(new Scene(new Group(), 100, 100));
		stage.setX(0);
		stage.setY(Screen.getPrimary().getBounds().getHeight() + 100);
		stage.show();

		Stage app = new Stage();
		app.setScene(new Scene(new Group(), 300, 200));
		app.setTitle("JavaFX隐藏任务栏");
		app.initOwner(stage);
		app.initModality(Modality.APPLICATION_MODAL);
		app.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				stage.close();
			}
		});

		app.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}