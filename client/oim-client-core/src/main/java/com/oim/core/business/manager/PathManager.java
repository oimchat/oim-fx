package com.oim.core.business.manager;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.common.AppConstant;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.UserData;

/**
 * 路径统一管理
 * 
 * @author: XiaHui
 *
 */
public class PathManager extends AbstractManager {

	

	public PathManager(AppContext appContext) {
		super(appContext);
		init();
	}
	
	private void init(){
		
	}

	
	public String getScreenshotSavePath(){
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		StringBuilder sb=new StringBuilder();
		sb.append(AppConstant.userHome);
		sb.append("/");
		sb.append(AppConstant.app_home_path);
		sb.append("/");
		sb.append(user.getNumber());
		sb.append("/");
		sb.append("images");
		sb.append("/");
		sb.append("screenshot");
		sb.append("/");
		return sb.toString();
	}
}
