package com.oim.fx.view.function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.ui.fx.classics.list.HeadItem;
import com.oim.ui.fx.classics.list.ListNodePane;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author: XiaHui
 * @date: 2018-01-18 17:29:43
 */
public class UserListFunction extends AbstractComponent {

	/** 存放好友分组列表组件 **/
	protected Map<String, ListNodePane> userListNodeMap = new ConcurrentHashMap<String, ListNodePane>();
	/** 存放单个好友组件 **/
	protected Map<String, HeadItem> userHeadItemMap = new ConcurrentHashMap<String, HeadItem>();


	public UserListFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {
	}

	
}
