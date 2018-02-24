package com.oim.fx.view.function;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.service.UserChatService;
import com.oim.core.common.app.view.UserCategoryMenuView;
import com.oim.core.common.app.view.UserHeadMenuView;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.view.node.UserCategoryMenuViewImpl;
import com.oim.fx.view.node.UserHeadMenuViewImpl;
import com.oim.ui.fx.classics.list.HeadItem;
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
public class UserHeadFunction extends AbstractComponent {

	protected UserCategoryMenuView ucmv;
	protected UserHeadMenuView uhmv;

	public UserHeadFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {
		ucmv = new UserCategoryMenuViewImpl(appContext);
		uhmv = new UserHeadMenuViewImpl(appContext);
	}

	public void setUserDataHeadEvent(HeadItem head) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent e) {
				UserData userData = head.getAttribute(UserData.class);
				uhmv.setUserData(userData);
				uhmv.show(head, e.getScreenX(), e.getScreenY());
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

	public void setUserDataHead(HeadItem head, UserData userData) {

		String status = AppCommonUtil.getDefaultStatus(userData.getStatus());
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getUserHead(userData.getId(), 40);
		String remark = (null == userData.getRemarkName() || "".equals(userData.getRemarkName())) ? userData.getNickname() : userData.getRemarkName();
		String nickname = (null == userData.getRemarkName() || "".equals(userData.getRemarkName())) ? userData.getAccount() : userData.getNickname();

		head.addAttribute(UserData.class, userData);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				head.setHeadImage(image);
				head.setRemark(remark);// 备注名
				head.setNickname("(" + nickname + ")");// 昵称
				head.setShowText(userData.getSignature());// 个性签名
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// 如果用户不是在线状态，则使其头像变灰
				if (AppCommonUtil.isOffline(userData.getStatus())) {
					head.setGray(true);
					head.setStatus("[离线]");
				} else {
					head.setGray(false);
					head.setStatus("[在线]");
				}
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Image iconImage = ImageBox.getStatusImageIcon(status);

				IconPane iconButton = head.getAttribute("statusLabel");
				if (null == iconButton) {// 状态图标显示组件
					iconButton = new IconPane(iconImage);
					head.addAttribute("statusLabel", iconButton);
					head.addBusinessIcon(iconButton);
				}
				iconButton.setNormalImage(iconImage);
			}
		});
	}
}
