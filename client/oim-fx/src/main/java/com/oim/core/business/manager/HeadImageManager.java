package com.oim.core.business.manager;

import java.util.List;

import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.HeadBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.head.HeadData;
import com.oim.core.head.HeadStore;
import com.oim.fx.common.box.ImageBox;
import com.only.common.util.OnlyStringUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupHead;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

import javafx.scene.image.Image;

/**
 * @author: XiaHui
 * @date: 2018-01-27 14:35:36
 */
public class HeadImageManager extends AbstractManager {

	HeadStore hr = new HeadStore();

	public HeadImageManager(AppContext appContext) {
		super(appContext);
	}

	public Image getUserHead(String userId) {
		return getUserHead(userId, 100);

	}

	public Image getUserHead(String userId, int size) {
		String path = getUserHeadPath(userId, size);
		Image image = null;
		if (OnlyStringUtil.isNotBlank(path)) {
			image = ImageBox.getImagePath(path);
		}
		return image;
	}

	public String getUserHeadPath(String userId) {
		String path = getUserHeadPath(userId, 100);
		return path;
	}

	private String getUserHeadPath(String userId, int size) {
		HeadBox hb = appContext.getBox(HeadBox.class);
		String path = hb.getUserHeadPath(userId);

		if (null == path || path.isEmpty()) {
			UserHead uh = hb.getUserHead(userId);
			if (null != uh && UserHead.type_system.equals(uh.getType())) {
				path = hr.getUserHeadPath(uh.getHeadId(), size);
			}
		}
		if (null == path || path.isEmpty()) {
			UserDataBox ub = appContext.getBox(UserDataBox.class);
			UserData ud = ub.getUserData(userId);
			String head = null == ud ? "1" : ud.getHead();
			path = hr.getUserHeadPath(head, size);
		}
		if (OnlyStringUtil.isBlank(path)) {
			path = hr.getUserHeadPath("1");
		}
		return path;
	}

	public Image getGroupHead(String groupId) {
		String path = getGroupHeadPath(groupId);
		Image image = null;
		if (OnlyStringUtil.isNotBlank(path)) {
			image = ImageBox.getImagePath(path);
		} 
		return image;
	}

	public String getGroupHeadPath(String groupId) {
		HeadBox hb = appContext.getBox(HeadBox.class);
		String path = hb.getGroupHeadPath(groupId);
		if (OnlyStringUtil.isBlank(path)) {
			GroupHead gh = hb.getGroupHead(groupId);
			if (null != gh && GroupHead.type_system.equals(gh.getType())) {
				path = hr.getGroupHeadPath(gh.getHeadId());
			}
		}
		if (OnlyStringUtil.isBlank(path)) {
			GroupBox gb = appContext.getBox(GroupBox.class);
			Group g = gb.getGroup(groupId);
			String head = null == g ? "1" : g.getHead();
			path = hr.getGroupHeadPath(head);
		}
		if (OnlyStringUtil.isBlank(path)) {
			path = hr.getGroupHeadPath("1");
		}
		return path;
	}

	public Image getPersonalHeadImage() {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData ud = pb.getUserData();
		String userId = ud.getId();
		String path = getUserHeadPath(userId);
		Image image = null;
		if (OnlyStringUtil.isNotBlank(path)) {
			image = ImageBox.getImagePath(path);
		} else {
			String head = ud.getHead();
			path = hr.getUserHeadPath(head);
			if (OnlyStringUtil.isBlank(path)) {
				path = hr.getUserHeadPath("1");
			}
			if (OnlyStringUtil.isNotBlank(path)) {
				image = ImageBox.getImagePath(path);
			}
		}
		return image;
	}

	public Image getUserHeadImageByKey(String key, int size) {
		Image image = null;
		String path = hr.getUserHeadPath(key, size);
		if (OnlyStringUtil.isBlank(path)) {
			path = hr.getUserHeadPath("1");
		}
		if (OnlyStringUtil.isNotBlank(path)) {
			image = ImageBox.getImagePath(path);
		}
		return image;
	}

	public Image getUserHeadImageByPath(String path) {
		Image image = null;
		if (OnlyStringUtil.isNotBlank(path)) {
			image = ImageBox.getImagePath(path);
		}
		return image;
	}

	public List<HeadData> getUserHeadPathList() {
		return hr.getUserHeadPathList();
	}

	public List<HeadData> getGroupHeadPathList() {
		return hr.getGroupHeadPathList();
	}

	public Image getGroupHeadImageByKey(String key) {
		Image image = null;
		String path = hr.getGroupHeadPath(key);
		if (OnlyStringUtil.isBlank(path)) {
			path = hr.getGroupHeadPath("1");
		}
		if (OnlyStringUtil.isNotBlank(path)) {
			image = ImageBox.getImagePath(path);
		}
		return image;
	}

}
