package com.only.fx.common.component.skin;

import com.sun.javafx.scene.control.skin.ContextMenuSkin;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;

/**
 * @author: XiaHui
 * @date: 2018-01-27 12:29:21
 */
public class OnlyContextMenuSkin extends ContextMenuSkin {

	public OnlyContextMenuSkin(ContextMenu popupMenu) {
		super(popupMenu);
	}

	public Node getNode() {
		Node root = super.getNode();
		return root;
	}
}
