/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.function.ui;

import com.oim.fx.common.component.BaseStage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Only
 */
public class WebFrame extends BaseStage {

	WebView webView = new WebView();
	VBox vBox = new VBox();

	public WebFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setBackground("Resources/Images/Wallpaper/12.jpg");
		this.setTitle("登录");
		this.setMinWidth(320);
		this.setMinHeight(240);

		this.setWidth(800);
		this.setHeight(480);

		VBox rootBox = new VBox();
		this.setCenter(rootBox);

		// webView.setPrefSize(430, 180);

		Label titleLabel = new Label();
		// titleLabel.setText("设备列表");
		titleLabel.setFont(Font.font("微软雅黑", 30));
		titleLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1)");

		HBox hBox = new HBox();
		hBox.setMinHeight(30);
		hBox.setStyle("-fx-background-color:rgba(18, 98, 217, 1)");

		hBox.getChildren().add(titleLabel);

		rootBox.setStyle("-fx-background-color:rgba(255, 255, 255, 0.2)");
		rootBox.getChildren().add(hBox);
		rootBox.getChildren().add(webView);
		WebEngine webEngine = webView.getEngine();
		webEngine.locationProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(newValue);

				// File file = new File(System.getProperty("user.home") +
				// "/Downloads/Ekko Downloads/");
				// String[] downloadableExtensions = {".doc", ".xls", ".zip",
				// ".exe", ".rar", ".pdf", ".jar", ".png", ".jpg", ".gif"};
				// for(String downloadAble : downloadableExtensions) {
				// if (newValue.endsWith(downloadAble)) {
				// try {
				// if(!file.exists()) {
				// file.mkdir();
				// }
				// File download = new File(file + "/" + newValue);
				// if(download.exists()) {
				// Dialogs.create().title("Exists").message("What you're trying
				// to download already exists").showInformation();
				// return;
				// }
				// Dialogs.create().title("Downloading").message("Started
				// Downloading").showInformation();
				// FileUtils.copyURLToFile(new URL(engine.getLocation()),
				// download);
				// Dialogs.create().title("Download").message("Download is
				// completed your download will be in: " +
				// file.getAbsolutePath()).showInformation();
				// } catch(Exception e) {
				// e.printStackTrace();
				// }
				// }
				// }
			}
		});
	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	public void load(String url) {
		WebEngine webEngine = webView.getEngine();
		webEngine.load(url);
	}
}
