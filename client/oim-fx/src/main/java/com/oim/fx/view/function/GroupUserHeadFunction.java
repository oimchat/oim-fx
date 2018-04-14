package com.oim.fx.view.function;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.service.UserChatService;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.ui.fx.classics.chat.SimpleListItem;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.GroupMember;
import com.onlyxiahui.im.bean.UserData;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

/**
 * @author: XiaHui
 * @date: 2018-01-18 17:29:43
 */
public class GroupUserHeadFunction extends AbstractComponent {

	public GroupUserHeadFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {

	}

	public void setUserHeadEvent(SimpleListItem head) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				e.consume();
			}
		});
		head.setOnMouseClicked((MouseEvent me) -> {
			me.consume();
			if (me.getClickCount() == 2) {
				UserData userData = head.getAttribute(UserData.class);
				UserChatService cs = this.appContext.getService(UserChatService.class);
				cs.showUserChat(userData);
			}
		});
	}

	public void setUserDataHead(SimpleListItem head, UserData userData, GroupMember groupMember) {
		String nickname = AppCommonUtil.getDefaultShowName(userData);
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getUserHead(userData.getId(), 40);
		head.addAttribute(UserData.class, userData);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				head.setName(nickname);
				head.setHeadImage(image);
				// 如果用户不是在线状态，则使其头像变灰
				if (AppCommonUtil.isOffline(userData.getStatus())) {
					head.setGray(true);
				} else {
					head.setGray(false);
				}
			}
		});
	}
}
