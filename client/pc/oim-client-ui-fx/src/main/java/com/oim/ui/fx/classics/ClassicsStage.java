package com.oim.ui.fx.classics;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.FreeStage;

import javafx.scene.image.Image;

/**
 * @author XiaHui
 * @date 2017-11-28 16:42:24
 */
public class ClassicsStage extends FreeStage {

	public ClassicsStage() {
		init();
	}

	private void init() {
		Image image = ImageBox.getImageClassPath("/common/images/logo/icon/logo_64.png");
		this.getIcons().clear();
		this.getIcons().add(image);
		this.getScene().getStylesheets().add(this.getClass().getResource("/classics/css/base.css").toString());
		this.getScene().getStylesheets().add(this.getClass().getResource("/classics/css/component.css").toString());
		//this.setBackground(this.getClass().getResource("/wallpaper/default.jpg"));
	}
}
