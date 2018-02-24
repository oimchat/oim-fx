package com.oim.core.business.manager;

import java.util.HashMap;
import java.util.Map;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;

import javafx.scene.image.Image;

/**
 * 描述： 主界面好友列表、群列表的管理
 * 
 * @author XiaHui
 * @date 2015年4月12日 上午10:18:18
 * @version 0.0.1
 */
public class IconButtonManage extends AbstractManager {

	Map<String, IconPane> map = new HashMap<String, IconPane>();

	public IconButtonManage(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
	}

	public void put(String key, IconPane ib) {
		map.put(key, ib);
	}

	boolean newNotice = false;

	public boolean isNewNotice() {
		return newNotice;
	}

	public void newNotice(boolean newNotice) {
		this.newNotice = newNotice;
		IconPane ib = map.get("notice_icon_button");
		if (null != ib) {
			Image normalImage = null;
			Image hoverImage = null;
			Image pressedImage = null;

			if (newNotice) {
				normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_new.png");
				hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_highlight_new.png");
				pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_down_new.png");

			} else {
				normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message.png");
				hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_highlight.png");
				pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_down.png");
			}
			ib.setNormalImage(normalImage);
			ib.setHoverImage(hoverImage);
			ib.setPressedImage(pressedImage);
		}
	}
}
