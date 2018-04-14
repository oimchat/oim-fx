/*
 * To change mainFrame license header, choose License Headers in Project Properties.
 * To change mainFrame template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.bine;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.ui.list.ListRootPanel;
import com.oim.ui.fx.bine.MainStage;
import com.oim.ui.fx.bine.main.ItemListPane;
import com.oim.ui.fx.bine.main.MainPane;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class MainStageTest extends Application {

	MainStage stage = new MainStage();

	ItemListPane userListPane=new ItemListPane();
	ListRootPanel userFindPane = userListPane.getFindListPane();
	ListRootPanel userRoot = userListPane.getListRootPane();
	ListRootPanel lastRoot = new ListRootPanel();

	MainPane userMainPane = new MainPane();
	MainPane lastMainPane = new MainPane();
	
	@Override
	public void start(Stage primaryStage) {
		stage.show();
		initOwn();
		initTest();
		initUserList();
	}

	private void initOwn() {
		Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + 4 + ".png");
		stage.setHeadImage(image);
	}

	/**
	 * 测试数据
	 */
	private void initTest() {
		//stage.setBackgroundColor(Color.rgb(245,245,245,1));
		
		userFindPane.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");
		
		userListPane.getBorderPane().setStyle("-fx-background-color:rgba(220, 220, 220, 0.7)");
		lastRoot.setStyle("-fx-background-color:rgba(220, 220, 220, 0.7)");

		userMainPane.setLeftWidth(230);
		lastMainPane.setLeftWidth(230);

		userMainPane.setLeftCenter(userListPane);
		lastMainPane.setLeftCenter(lastRoot);
		
		Image normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_hover.png");
		Image selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_selected.png");

		normalImage = ImageBox.getImageClassPath("/bine/main/images/tab/message_normal.png");
		hoverImage = ImageBox.getImageClassPath("/bine/main/images/tab/message_hover.png");
		selectedImage = ImageBox.getImageClassPath("/bine/main/images/tab/message_selected.png");

		stage.addTab(normalImage, hoverImage, selectedImage, lastMainPane);

		normalImage = ImageBox.getImageClassPath("/bine/main/images/tab/user_normal.png");
		hoverImage = ImageBox.getImageClassPath("/bine/main/images/tab/user_hover.png");
		selectedImage = ImageBox.getImageClassPath("/bine/main/images/tab/user_selected.png");

		stage.addTab(normalImage, hoverImage, selectedImage, userMainPane);

		normalImage = ImageBox.getImageClassPath("/bine/main/images/tab/group_normal.png");
		hoverImage = ImageBox.getImageClassPath("/bine/main/images/tab/group_hover.png");
		selectedImage = ImageBox.getImageClassPath("/bine/main/images/tab/group_selected.png");

		lastRoot = new ListRootPanel();
		//lastRoot.setStyle("-fx-background-color:rgba(144, 50, 59, 0.8)");
		stage.addTab(normalImage, hoverImage, selectedImage, lastRoot);

	}

	private void initUserList() {

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
