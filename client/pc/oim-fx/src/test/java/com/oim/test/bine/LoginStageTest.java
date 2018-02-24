/*
 * To change mainFrame license header, choose License Headers in Project Properties.
 * To change mainFrame template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.bine;

import java.util.ArrayList;
import java.util.List;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.bine.LoginStage;
import com.only.fx.common.action.ValueAction;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class LoginStageTest extends Application {

	LoginStage stage = new LoginStage();

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
		stage.setBackground("Resources/Images/Wallpaper/1.jpg");
	}

	/**
	 * 测试数据
	 */
	private void initTest() {
		List<String> list = new ArrayList<String>();
		list.add("10000");
		list.add("10001");
		list.add("10002");
		list.add("10003");

		stage.setList(list);
	}

	private void initUserList() {

		stage.setAccountListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(newValue);
			}
		});

		stage.setOnAccountRemove(new ValueAction<String>() {

			@Override
			public void value(String value) {
				System.out.println(value);
			}
		});

		stage.setLoginAction(a -> {
			if (stage.verify()) {
				stage.showWaiting(true);
			}
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
