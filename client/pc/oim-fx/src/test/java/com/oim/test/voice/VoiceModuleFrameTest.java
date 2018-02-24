/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.voice;

import com.oim.core.common.component.VoiceModule;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.AddressData;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class VoiceModuleFrameTest extends Application {
	VoiceModule vm = new VoiceModule();

	@Override
	public void start(Stage stage) {
		vm.start();
		VBox box = new VBox();
		Scene scene = new Scene(box);
		UserData user = new UserData();
		user.setId("10000");
		
		CheckBox checkBox1 = new CheckBox();
		CheckBox checkBox2 = new CheckBox();
		
		Slider slider=new Slider();
		box.getChildren().add(checkBox1);
		box.getChildren().add(checkBox2);
		box.getChildren().add(slider);

		
		stage.setResizable(false);
		stage.setWidth(445);
		stage.setHeight(345);
		stage.show();
		stage.setScene(scene);
		AddressData addressData=new AddressData();
		addressData.setAddress("127.0.0.1");
		addressData.setPort(10086);
		 
		checkBox1.setOnAction(a -> {
			boolean isSelected = checkBox1.isSelected();
			System.out.println("checkBox1:" + isSelected);
			
			if(isSelected) {
				vm.putSend("10000", addressData);
			}else {
				vm.removeSend("10000");
			}
		});

		checkBox2.setOnAction(a -> {
			boolean isSelected = checkBox2.isSelected();
			System.out.println("checkBox2:" + isSelected);
			if(isSelected) {
				vm.putReceive("10000");
			}else {
				vm.removeReceive("10000");
			}
		});
		
		slider.valueProperty().addListener(l->{
			float size=(float)slider.getValue();
			vm.putReceiveValue("10000",size);
			System.out.println((float)slider.getValue());
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
