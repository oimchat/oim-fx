package com.oim.core.common.app.view;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.stage.Window;

/**
 * @author XiaHui
 * @date 2017年9月4日 下午5:41:49
 */
public interface MenuView {
	
	void show(Window ownerWindow, double anchorX, double anchorY);
	
	void show(Node anchor, double screenX, double screenY);
	
	void show(Node anchor, Side side, double dx, double dy);
}
