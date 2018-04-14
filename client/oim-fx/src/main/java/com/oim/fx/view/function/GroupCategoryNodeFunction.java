package com.oim.fx.view.function;

import com.oim.core.common.app.view.GroupCategoryMenuView;
import com.oim.fx.view.node.GroupCategoryMenuViewImpl;
import com.oim.ui.fx.classics.list.ListNodePane;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.GroupCategory;

import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;

/**
 * @author: XiaHui
 * @date: 2018-01-18 17:29:43
 */
public class GroupCategoryNodeFunction extends AbstractComponent {

	protected GroupCategoryMenuView gcmv;

	public GroupCategoryNodeFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {
		gcmv = new GroupCategoryMenuViewImpl(appContext);
	}

	public void setGroupCategoryNodeEvent(ListNodePane node) {
		node.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {
				GroupCategory groupCategory = (GroupCategory) node.getUserData();
				gcmv.setGroupCategory(groupCategory);
				gcmv.show(node, event.getScreenX(), event.getScreenY());
				event.consume();
			}
		});
	}
}
