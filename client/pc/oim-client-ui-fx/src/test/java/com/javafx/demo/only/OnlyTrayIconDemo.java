package com.javafx.demo.only;

import java.awt.SystemTray;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.only.fx.common.component.OnlyTrayIcon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OnlyTrayIconDemo extends Application {
	private OnlyTrayIcon trayIcon;

	/** * @param args */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		enableTray(stage);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setGridLinesVisible(true);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Button b1 = new Button("测试1");
		Button b2 = new Button("测试2");
		grid.add(b1, 0, 0);
		grid.add(b2, 1, 1);

		Scene scene = new Scene(grid, 800, 600);
		stage.setScene(scene);
		stage.show();
		Platform.setImplicitExit(false);
	}

	// 右小角,最小化.
	private void enableTray(final Stage stage) {
		try {
			ContextMenu menu = new ContextMenu();

			MenuItem updateMenuItem = new MenuItem();
			MenuItem showMenuItem = new MenuItem();
			showMenuItem.setText("查看群信息");
			updateMenuItem.setText("修改群信息");
			menu.getItems().add(showMenuItem);
			menu.getItems().add(updateMenuItem);
			menu.getItems().add(new MenuItem("好好的事实的话"));
			menu.getItems().add(new MenuItem("好好的事实的话"));
			menu.getItems().add(new MenuItem("好好的事实的话"));
			menu.getItems().add(new MenuItem("好好的事实的话"));
			menu.getItems().add(new MenuItem("好好的事实的话"));

			SystemTray tray = SystemTray.getSystemTray();
			BufferedImage image = ImageIO.read(OnlyTrayIconDemo.class.getResourceAsStream("tray.png"));
			// Image image = new
			// ImageIcon("Resources/Images/Logo/logo_16.png").getImage();
			trayIcon = new OnlyTrayIcon(image, "自动备份工具");
			trayIcon.setToolTip("自动备份工具");
			trayIcon.setContextMenu(menu);
			tray.add(trayIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}