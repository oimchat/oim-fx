/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.only;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.ClassicsStage;
import com.only.fx.common.action.ExecuteAction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author XiaHui
 */
public class MenuFrame extends ClassicsStage {

	
	BorderPane rootPane = new BorderPane();
	HBox hBox = new HBox();
	StatusPopupMenu menu=new StatusPopupMenu();
	Button button =new Button("menu");
	
	public MenuFrame() {
		init();
	}

	private void init() {
		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);
		
		
		rootPane.setBottom(hBox);
		
		hBox.getChildren().add(button);
		
		button.setGraphicTextGap(3);
		button.setOnAction(a->{
			//menu.show(button, Side.TOP, button.getLayoutX(), button.getLayoutY());
			menu.getHeight();
			menu.centerOnScreen();
			menu.show(button, Side.TOP, button.getLayoutX(), button.getLayoutY());
		});
	}
	
	class StatusPopupMenu extends ContextMenu {

		private MenuItem awayMenuItem = new MenuItem();
		private MenuItem busyMenuItem = new MenuItem();
		private MenuItem invisibleMenuItem = new MenuItem();

		private SeparatorMenuItem separator1 = new SeparatorMenuItem();
		private SeparatorMenuItem separator2 = new SeparatorMenuItem();
		private SeparatorMenuItem separator3 = new SeparatorMenuItem();
		private MenuItem muteMenuItem = new MenuItem();
		private MenuItem omeMenuItem = new MenuItem();
		private MenuItem onlineMenuItem = new MenuItem();
		private MenuItem offlineMenuItem = new MenuItem();

		Image onlineImage = ImageBox.getImageClassPath("/resources/common/images/status/online.png");
		Image callMeImage = ImageBox.getImageClassPath("/resources/common/images/status/call_me.png");
		Image awayImage = ImageBox.getImageClassPath("/resources/common/images/status/away.png");
		Image busyImage = ImageBox.getImageClassPath("/resources/common/images/status/busy.png");
		Image muteImage = ImageBox.getImageClassPath("/resources/common/images/status/mute.png");
		Image invisibleImage = ImageBox.getImageClassPath("/resources/common/images/status/invisible.png");
		Image offlineImage = ImageBox.getImageClassPath("/resources/common/images/status/offline.png");

		ImageView onlineImageView = new ImageView();
		ImageView callMeImageView = new ImageView();
		ImageView awayImageView = new ImageView();
		ImageView busyImageView = new ImageView();
		ImageView muteImageView = new ImageView();
		ImageView invisibleImageView = new ImageView();
		ImageView offlineImageView = new ImageView();

		private SeparatorMenuItem separator4 = new SeparatorMenuItem();

		protected MenuItem updatePasswordMenuItem = new MenuItem();
		private MenuItem updateMenuItem = new MenuItem();
		private MenuItem quitMenuItem = new MenuItem();
		
		private ExecuteAction statusAction;

		public StatusPopupMenu() {
			initMenu();
		}

		private void initMenu() {

			onlineImageView.setImage(onlineImage);
			callMeImageView.setImage(callMeImage);
			awayImageView.setImage(awayImage);
			busyImageView.setImage(busyImage);
			muteImageView.setImage(muteImage);
			invisibleImageView.setImage(invisibleImage);
			offlineImageView.setImage(offlineImage);

			onlineMenuItem.setText("我在线上");
			omeMenuItem.setText("Call我吧");
			awayMenuItem.setText("离开");
			busyMenuItem.setText("忙碌");
			muteMenuItem.setText("请勿打扰");
			invisibleMenuItem.setText("隐身");
			offlineMenuItem.setText("离线");

			onlineMenuItem.setGraphic(onlineImageView);
			omeMenuItem.setGraphic(callMeImageView);
			awayMenuItem.setGraphic(awayImageView);
			busyMenuItem.setGraphic(busyImageView);
			muteMenuItem.setGraphic(muteImageView);
			invisibleMenuItem.setGraphic(invisibleImageView);
			offlineMenuItem.setGraphic(offlineImageView);

			this.getItems().add(onlineMenuItem);
			this.getItems().add(omeMenuItem);
			this.getItems().add(separator1);
			this.getItems().add(awayMenuItem);
			this.getItems().add(busyMenuItem);
			this.getItems().add(muteMenuItem);
			this.getItems().add(separator2);
			this.getItems().add(invisibleMenuItem);
			this.getItems().add(separator3);
			this.getItems().add(offlineMenuItem);
			
			updateMenuItem.setText("修改资料");
			updatePasswordMenuItem.setText("修改密码");
			quitMenuItem.setText("退出");

			this.getItems().add(separator4);
			this.getItems().add(updateMenuItem);
			this.getItems().add(updatePasswordMenuItem);
			this.getItems().add(quitMenuItem);

			onlineMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					onlineMenuItemActionPerformed();
				}
			});
			omeMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					omeMenuItemActionPerformed();
				}
			});
			awayMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					awayMenuItemActionPerformed();
				}
			});
			busyMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					busyMenuItemActionPerformed();
				}
			});
			muteMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					muteMenuItemActionPerformed();
				}
			});
			invisibleMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					invisibleMenuItemActionPerformed();
				}
			});
			offlineMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					offlineMenuItemActionPerformed();
				}
			});

		}

		private void onlineMenuItemActionPerformed() {
			updateStatus("1");
		}

		private void omeMenuItemActionPerformed() {
			updateStatus("2");
		}

		private void awayMenuItemActionPerformed() {
			updateStatus("3");
		}

		private void busyMenuItemActionPerformed() {
			updateStatus("4");
		}

		private void muteMenuItemActionPerformed() {
			updateStatus("5");
		}

		private void invisibleMenuItemActionPerformed() {
			updateStatus("6");
		}

		private void offlineMenuItemActionPerformed() {
			updateStatus("7");
		}

		private void updateStatus(String status) {
			if (null != statusAction) {
				statusAction.execute(status);
			}
		}

		public void setStatusAction(ExecuteAction statusAction) {
			this.statusAction = statusAction;
		}
	}

}
