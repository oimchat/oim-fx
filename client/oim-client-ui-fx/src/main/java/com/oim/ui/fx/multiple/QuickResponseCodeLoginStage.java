package com.oim.ui.fx.multiple;

import com.oim.ui.fx.classics.CommonStage;

import javafx.scene.image.Image;

/**
 * @author XiaHui
 * @date 2017-12-16 10:51:17
 */
public class QuickResponseCodeLoginStage extends CommonStage {

	QuickResponseCodeLoginPane pane = new QuickResponseCodeLoginPane();

	public QuickResponseCodeLoginStage() {
		initComponent();
		initEvent();
	}

	private void initComponent() {
		this.setCenter(pane);
	}

	private void initEvent() {

	}

	public void setImage(Image value) {
		pane.setImage(value);
	}
}
