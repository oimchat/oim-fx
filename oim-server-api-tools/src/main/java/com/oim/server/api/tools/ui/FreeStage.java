package com.oim.server.api.tools.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.only.fx.OnlyFrame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

/**
 * @author XiaHui
 * @date 2017-10-27 10:46:16
 */
public class FreeStage extends OnlyFrame {

	Alert information = new Alert(AlertType.INFORMATION);
	Color color = Color.rgb(245, 245, 245, 0.0D);

	public FreeStage() {
		init();
	}

	private void init() {
		this.setRadius(5);
		Image image = new Image("/images/logo/icon/logo_64.png", false);
		this.getIcons().clear();
		this.getIcons().add(image);
		getOnlyTitlePane().addOnCloseAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FreeStage.this.hide();
			}
		});
		initPrompt();
	}

	private void initPrompt() {
		information.initModality(Modality.APPLICATION_MODAL);
		information.initOwner(this);
		information.getDialogPane().setHeaderText(null);
	}

	public void showPrompt(String text) {
		information.getDialogPane().setContentText(text);
		information.showAndWait();
	}

	public void setBackgroundOpacity(double opacity) {
		color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
		// color=Color.rgb(245, 245, 245, opacity);
		super.setBackgroundColor(color);
	}

	public void setBackground(String imagePath) {
		////
		// Image image=ImageBox.getImagePath(imagePath);
		// BackgroundImage bi= new BackgroundImage(image,
		// BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
		//// BackgroundPosition.DEFAULT,
		// BackgroundSize.DEFAULT);
		// backgroundPane.setBackground(new Background(bi));
		// backgroundImagePane.setStyle("-fx-background-image:url(\"" +
		//// imagePath + "\");");
		try {
			imagePath = imagePath.replace("\\", "/");
			String pathString = new File(imagePath).toURI().toURL().toString();
			backgroundImagePane.setStyle("-fx-background-image:url(\"" + pathString + "\");");
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
	}

	public void setBackground(URL url) {
		String pathString = url.toString();
		backgroundImagePane.setStyle("-fx-background-image:url(\"" + pathString + "\");");
	}
}
