package com.oim.fx.view.function;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.service.UserChatService;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.ui.fx.classics.chat.ChatItem;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.UserData;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

/**
 * @author XiaHui
 * @date 2017-12-22 11:16:27
 */
public class UserChatItemFunction extends AbstractComponent {

	public UserChatItemFunction(AppContext appContext) {
		super(appContext);
	}

	public void setChatUserDataHeadEvent(ChatItem head) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent e) {
				e.consume();
			}
		});
		head.setOnMouseClicked((MouseEvent me) -> {
			me.consume();
			if (me.getClickCount() == 1) {
				UserData userData = head.getAttribute(UserData.class);
				UserChatService cs = this.appContext.getService(UserChatService.class);
				cs.removeUserPrompt(userData.getId());
			}
		});
	}

	public void setChatUserDataHead(ChatItem head, UserData userData) {
		String userId = userData.getId();
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getUserHead(userId,  40);
		String name = AppCommonUtil.getDefaultShowName(userData);
		
		head.addAttribute(UserData.class, userData);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				head.setName(name);
				head.setHeadImage(image);
				if (AppCommonUtil.isOffline(userData.getStatus())) {
					head.setGray(true);
				} else {
					head.setGray(false);
				}
			}
		});
	}
}
