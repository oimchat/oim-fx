package com.oim.fx.view.function;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.service.UserChatService;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.ui.fx.classics.chat.LastItem;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
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
public class UserLastFunction extends AbstractComponent {

	public UserLastFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {

	}

	public void setUserDataHeadEvent(LastItem head) {
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

	public void setUserDataHead(LastItem head, UserData userData) {

		String statusTemp = userData.getStatus();
		String status = (null != statusTemp && !(UserData.status_invisible.equals(statusTemp))) ? statusTemp : UserData.status_offline;

		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getUserHead(userData.getId(), 40);
		String remark = userData.getRemarkName();
		String nickname = userData.getNickname();
		boolean hasRemark = (null != remark && !"".equals(remark));

		String remarkText = (hasRemark) ? remark : nickname;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				head.setHeadImage(image);
				head.setName(remarkText);// 备注名
				head.addAttribute(UserData.class, userData);

				// 如果用户不是在线状态，则使其头像变灰
				if (AppCommonUtil.isOffline(status)) {
					head.setGray(true);
				} else {
					head.setGray(false);
				}
			}
		});
	}
}
