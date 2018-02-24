package com.oim.fx.view.function;

import com.oim.core.common.app.view.UserCategoryMenuView;
import com.oim.fx.view.node.UserCategoryMenuViewImpl;
import com.oim.ui.fx.classics.list.ListNodePane;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.UserCategory;

import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;

/**
 * @author: XiaHui
 * @date: 2018-01-18 17:29:43
 */
public class UserCategoryNodeFunction extends AbstractComponent {

	protected UserCategoryMenuView ucmv;

	public UserCategoryNodeFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {
		ucmv = new UserCategoryMenuViewImpl(appContext);
	}

	public void setUserCategoryNodeEvent(ListNodePane node) {
		node.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {
				UserCategory userCategory = (UserCategory) node.getUserData();
				ucmv.setUserCategory(userCategory);
				ucmv.show(node, event.getScreenX(), event.getScreenY());
				event.consume();
			}
		});
	}
}
