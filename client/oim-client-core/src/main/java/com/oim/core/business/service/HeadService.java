package com.oim.core.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.core.business.box.HeadBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.manager.GroupListManager;
import com.oim.core.business.manager.ImageManager;
import com.oim.core.business.manager.UserListManager;
import com.oim.core.business.view.MainView;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.component.file.FileHandleInfo;
import com.oim.core.common.component.file.FileInfo;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.data.UserSaveData;
import com.oim.core.common.data.UserSaveDataBox;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.bean.GroupHead;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-27 14:30:20
 */
public class HeadService extends AbstractService {

	public HeadService(AppContext appContext) {
		super(appContext);
	}

	public void setPersonalHead(final UserHead userHead) {
		if (null != userHead) {

			final HeadBox headBox = this.appContext.getBox(HeadBox.class);
			headBox.putUserHead(userHead);

			final MainView mainView = appContext.getSingleView(MainView.class);

			if (UserHead.type_custom.equals(userHead.getType())) {
				BackAction<FileHandleInfo> backAction = new BackAction<FileHandleInfo>() {

					@Override
					public void back(BackInfo backInfo, FileHandleInfo info) {
						if (backInfo.isSuccess()) {
							if (info.isSuccess()) {
								FileInfo fi = info.getFileInfo();
								String headPath = fi.getAbsolutePath();
								// userHead.setAbsolutePath(fi.getAbsolutePath());
								headBox.putUserHeadPath(userHead.getUserId(), headPath);

								mainView.refreshPersonalHead();

								PersonalBox pb = appContext.getBox(PersonalBox.class);
								UserData ud = pb.getUserData();

								UserSaveDataBox usdb = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);

								UserSaveData usd = usdb.get(ud.getAccount());
								if (usd == null) {
									usd = new UserSaveData();
									usd.setAccount(ud.getAccount());
								}
								usd.setHeadPath(headPath);
								usdb.put(ud.getAccount(), usd);
								ConfigManage.addOrUpdate(UserSaveDataBox.path, usdb);
							}
						}
					}
				};
				ImageManager im = this.appContext.getManager(ImageManager.class);
				im.downloadUserHead(userHead, backAction);
			} else {
				mainView.refreshPersonalHead();
			}
		}
	}

	public void setUserHeadList(List<UserHead> headList) {

		if (null != headList) {

			final HeadBox headBox = this.appContext.getBox(HeadBox.class);
			final Map<String, UserHead> map = new HashMap<String, UserHead>();
			List<UserHead> list = new ArrayList<UserHead>();
			for (UserHead h : headList) {
				if (UserHead.type_custom.equals(h.getType())) {
					map.put(h.getHeadId(), h);
					list.add(h);
				}
			}

			BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

				@Override
				public void back(BackInfo backInfo, List<FileHandleInfo> list) {
					if (backInfo.isSuccess()) {

						for (FileHandleInfo info : list) {
							if (info.isSuccess()) {
								FileInfo fi = info.getFileInfo();
								UserHead h = map.get(fi.getId());
								if (null != h) {
									headBox.putUserHeadPath(h.getUserId(), fi.getAbsolutePath());
								}
							}
						}
						UserListManager listManage = appContext.getManager(UserListManager.class);
						listManage.refreshUserHead();
					}
				}
			};
			ImageManager im = this.appContext.getManager(ImageManager.class);
			//UserListManage lm = this.appContext.getManager(UserListManage.class);
			headBox.setUserHeadList(headList);
			//lm.setUserHeadList(headList);
			im.downloadUserHead(list, backAction);
		}
	}

	public void setGroupHeadList(List<GroupHead> headList) {
		if (null != headList) {
			final HeadBox headBox = this.appContext.getBox(HeadBox.class);
			final Map<String, GroupHead> map = new HashMap<String, GroupHead>();
			List<GroupHead> list = new ArrayList<GroupHead>();
			for (GroupHead h : headList) {
				if (GroupHead.type_custom.equals(h.getType())) {
					map.put(h.getHeadId(), h);
					list.add(h);
				}
			}

			BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

				@Override
				public void back(BackInfo backInfo, List<FileHandleInfo> list) {
					if (backInfo.isSuccess()) {

						for (FileHandleInfo info : list) {
							if (info.isSuccess()) {
								FileInfo fi = info.getFileInfo();
								GroupHead h = map.get(fi.getId());
								if (null != h) {
									headBox.putGroupHeadPath(h.getGroupId(), fi.getAbsolutePath());
								}
							}
						}
						GroupListManager listManage = appContext.getManager(GroupListManager.class);
						listManage.refreshGroupHead();
					}
				}
			};
			ImageManager im = this.appContext.getManager(ImageManager.class);
			headBox.setGroupHeadList(headList);
			im.downloadGroupHeadList(list, backAction);
		}
	}
}
