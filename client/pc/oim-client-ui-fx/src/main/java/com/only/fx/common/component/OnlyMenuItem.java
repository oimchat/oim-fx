package com.only.fx.common.component;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;

/**
 * @author: XiaHui
 * @date: 2018-01-27 12:10:05
 */
public class OnlyMenuItem extends MenuItem {

	public OnlyMenuItem() {
		super(null, null);
		initComponent();
	}

	public OnlyMenuItem(String text) {
		super(text, null);
		initComponent();
	}

	public OnlyMenuItem(String text, Node graphic) {
		super(text, graphic);
		initComponent();
	}

	private void initComponent() {
	}
	
}
