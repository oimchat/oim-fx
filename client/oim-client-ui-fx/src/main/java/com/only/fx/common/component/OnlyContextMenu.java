package com.only.fx.common.component;

import com.only.fx.common.component.skin.OnlyContextMenuSkin;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Skin;

/**
 * @author: XiaHui
 * @date: 2018-01-27 12:13:41
 */
public class OnlyContextMenu extends ContextMenu {
	
	public OnlyContextMenu() {
		super();
		initComponent();
	}

	public OnlyContextMenu(MenuItem... items) {
		super(items);
		initComponent();
	}
	
	private void initComponent() {
	}
	
	@Override
	protected Skin<?> createDefaultSkin() {
		return new OnlyContextMenuSkin(this);
	}
}
