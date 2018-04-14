package com.javafx.demo.control.tray;

/**
 * @author: XiaHui
 * @date: 2016年10月12日 上午11:18:32
 */
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TrayDemo extends Application {
	private TrayIcon trayIcon;

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
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				stage.hide();
			}
		});
	}

	// 右小角,最小化.
	private void enableTray(final Stage stage) {
		PopupMenu popupMenu = new PopupMenu();
		java.awt.MenuItem openItem = new java.awt.MenuItem("显示");
		java.awt.MenuItem hideItem = new java.awt.MenuItem("最小化");
		java.awt.MenuItem quitItem = new java.awt.MenuItem("退出");

		ActionListener acl = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				java.awt.MenuItem item = (java.awt.MenuItem) e.getSource();
				Platform.setImplicitExit(false); // 多次使用显示和隐藏设置false

				if (item.getLabel().equals("退出")) {
					SystemTray.getSystemTray().remove(trayIcon);
					Platform.exit();
					return;
				}
				if (item.getLabel().equals("显示")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.show();
						}
					});
				}
				if (item.getLabel().equals("最小化")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.hide();
						}
					});
				}
			}
		};

		// 双击事件方法
		MouseListener sj = new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				Platform.setImplicitExit(false); // 多次使用显示和隐藏设置false
				if (e.getClickCount() == 2) {
					if (stage.isShowing()) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								stage.hide();
							}
						});
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								stage.show();
							}
						});
					}
				}
			}
		};

		openItem.addActionListener(acl);
		quitItem.addActionListener(acl);
		hideItem.addActionListener(acl);

		popupMenu.add(openItem);
		popupMenu.add(hideItem);
		popupMenu.add(quitItem);

		try {
			SystemTray tray = SystemTray.getSystemTray();
			BufferedImage image = ImageIO.read(TrayDemo.class.getResourceAsStream("tray.png"));
			//Image image = new ImageIcon("Resources/Images/Logo/logo_16.png").getImage();
			trayIcon = new TrayIcon(image, "自动备份工具", popupMenu);
			trayIcon.setToolTip("自动备份工具");
			tray.add(trayIcon);
			trayIcon.addMouseListener(sj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}