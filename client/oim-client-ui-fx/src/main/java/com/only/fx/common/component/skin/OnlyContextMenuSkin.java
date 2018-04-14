package com.only.fx.common.component.skin;

import com.sun.javafx.scene.control.skin.ContextMenuSkin;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Region;

/**
 * @author: XiaHui
 * @date: 2018-01-27 12:29:21
 */
public class OnlyContextMenuSkin extends ContextMenuSkin {

	public OnlyContextMenuSkin(ContextMenu popupMenu) {
		super(popupMenu);
		Node node = super.getNode();
		if (node instanceof Region) {
			Region root = (Region) node;
			root.setPadding(new Insets(8, 0, 8, 0));
		}
	}

	public Node getNode() {
		Node root = super.getNode();
		return root;
	}
}
