/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import java.util.ArrayList;
import java.util.List;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.WaitingPane;
import com.oim.fx.ui.CommonFrame;
import com.oim.fx.ui.list.HeadImagePanel;
import com.oim.ui.fx.classics.head.HeadListPane;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class UserHeadEditFrameTest extends Application {

	CommonFrame frame = new CommonFrame();
	HeadListPane p = new HeadListPane();
	Alert alert;
	@Override
	public void start(Stage primaryStage) {
		frame.setWidth(430);
		frame.setHeight(580);
		frame.setResizable(false);
		frame.setTitlePaneStyle(2);
		frame.setTitleText("选择头像");

		frame.setCenterNode(p);
		frame.show();
		
		frame.showDoneButton(false);
		
		alert = new Alert(AlertType.CONFIRMATION, "");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(frame);
		alert.getDialogPane().setContentText("确定选择");
		alert.getDialogPane().setHeaderText(null);

		p.setPrefWrapLength(400);
		p.showWaiting(true, WaitingPane.show_waiting);
		List<HeadImagePanel> list = new ArrayList<HeadImagePanel>();
		for (int i = 1; i < 102; i++) {
			Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + i + "_100.gif");
			String value = i + "";
			HeadImagePanel hp = new HeadImagePanel();
			hp.setHeadSize(60);
			hp.setImage(image);
			hp.setOnMouseClicked(m -> {
				select(image, value);
			});
			list.add(hp);

		}
		
		for (int i = 173; i < 265; i++) {
			Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + i + "_100.gif");
			String value = i + "";
			HeadImagePanel hp = new HeadImagePanel();
			hp.setHeadSize(60);
			hp.setImage(image);
			hp.setOnMouseClicked(m -> {
				select(image, value);
			});
			list.add(hp);

		}
		p.showWaiting(false, WaitingPane.show_waiting);
		p.setNodeList(list);
	}

	private void select(Image image, String value) {
		alert.showAndWait()
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			System.out.println(value);
		});
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
