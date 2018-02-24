package com.oim.core.business.view;

import java.util.List;

import com.onlyxiahui.app.base.view.View;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;

/**
 * @author: XiaHui
 * @date: 2016年9月28日 下午4:32:29
 */
public interface JoinGroupView extends View {
	
	public void showWaiting(boolean show) ;

	public void set(Group group, List<GroupCategory> groupCategoryList);

}
