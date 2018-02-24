package com.only.fx.common.component;

import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OnlyTrayIcon extends TrayIcon {

	private MouseListener mouseListener = new TrayMouseListener();;
	private ContextMenu contextMenu;
	private Stage stage;

	ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (!newValue && stage.isShowing()) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						stage.hide();
					}
				});
			}
		}
	};

	public OnlyTrayIcon(Image image, String tooltip) {
		super(image, tooltip, null);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				StackPane rootPane = new StackPane();
				rootPane.setBackground(Background.EMPTY);
				Scene scene = new Scene(rootPane);
				scene.setFill(Color.TRANSPARENT);
				scene.getStylesheets().add(this.getClass().getResource("/only/css/menu.css").toString());

				stage = new Stage();
				stage.initStyle(StageStyle.UTILITY);
				stage.setOpacity(0);
				stage.setMaxWidth(1);
				stage.setMaxHeight(1);
				stage.setAlwaysOnTop(true);
				stage.setScene(scene);
				// stage.setX(0);
			}
		});
		addMouseListener(mouseListener);
	}

	public ContextMenu getContextMenu() {
		return contextMenu;
	}

	public void setContextMenu(ContextMenu contextMenu) {
		this.contextMenu = contextMenu;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (null == contextMenu) {
					if (OnlyTrayIcon.this.contextMenu != null) {
						OnlyTrayIcon.this.contextMenu.showingProperty().removeListener(listener);
					}
				} else {
					contextMenu.showingProperty().addListener(listener);
				}
			}
		});
	}

	private class TrayMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (isPopupTrigger(e)) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						if (null != contextMenu && !contextMenu.isShowing()) {
							double h = contextMenu.getHeight();//.getHeight();
							double x = (double) e.getX();
							double y = (double) e.getY();
							stage.setAlwaysOnTop(true);
							stage.setX(x);
							stage.setY(y);
							stage.show();
							contextMenu.show(stage, x, y - h);
						}
					}
				});
			}
		}
	}

	public static boolean isPopupTrigger(MouseEvent evt) {
		return isRightButton(evt.getModifiers());
	}

	public static boolean isRightButton(int modifiers) {
		return (modifiers & InputEvent.BUTTON3_MASK) != 0;
	}
}
