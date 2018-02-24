package com.only.fx.common.component;

import com.only.fx.common.component.skin.OnlyScrollPaneSkin;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;

/**
 * @author: XiaHui
 * @date: 2018-01-17 00:05:49
 */
public class OnlyScrollPane extends ScrollPane {

	@Override
	protected Skin<?> createDefaultSkin() {
		return new OnlyScrollPaneSkin(this);
	}
}
