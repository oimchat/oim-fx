/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui;

import com.oim.fx.common.component.FreeStage;

/**
 *
 * @author Only
 */
public class BaseFrame extends FreeStage {

	public BaseFrame() {
		init();
	}

	private void init() {
		this.getRootPane().getStylesheets().add(this.getClass().getResource("/resources/common/css/base.css").toString());
		this.setBackground("Resources/Images/Wallpaper/default.jpg");
		this.setRadius(5);
	}
}
