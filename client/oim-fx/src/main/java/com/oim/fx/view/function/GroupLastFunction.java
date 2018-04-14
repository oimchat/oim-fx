package com.oim.fx.view.function;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.service.GroupChatService;
import com.oim.ui.fx.classics.chat.LastItem;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.Group;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

/**
 * @author: XiaHui
 * @date: 2018-01-18 17:29:43
 */
public class GroupLastFunction extends AbstractComponent {

	public GroupLastFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {

	}

	public void setGroupHeadEvent(LastItem head) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				e.consume();
			}
		});
		head.setOnMouseClicked((MouseEvent me) -> {
			if (me.getClickCount() == 2) {
				Group group = head.getAttribute(Group.class);
				GroupChatService cs = appContext.getService(GroupChatService.class);
				cs.showGroupChat(group);
			}
			me.consume();
		});
	}

	public void setGroupHead(LastItem head, Group group) {
		String groupId = group.getId();
		String name = group.getName();//
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getGroupHead(groupId);
		
		head.addAttribute(Group.class, group);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				head.setName(name);
				head.setHeadImage(image);
			}
		});
	}
}
