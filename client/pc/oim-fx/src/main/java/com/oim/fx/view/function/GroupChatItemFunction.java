package com.oim.fx.view.function;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.service.GroupChatService;
import com.oim.ui.fx.classics.chat.ChatItem;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.Group;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

/**
 * @author XiaHui
 * @date 2017-12-22 11:16:27
 */
public class GroupChatItemFunction extends AbstractComponent {

	public GroupChatItemFunction(AppContext appContext) {
		super(appContext);
	}

	
	public void setChatGroupHeadEvent(ChatItem head) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent e) {
				e.consume();
			}
		});
		head.setOnMouseClicked((MouseEvent me) -> {
			me.consume();
			if (me.getClickCount() == 1) {
				Group group = head.getAttribute(Group.class);
				GroupChatService cs = appContext.getService(GroupChatService.class);
				cs.removeGroupPrompt(group.getId());;
			}
		});
	}

	public void setChatGroupHead(ChatItem head, Group group) {
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getGroupHead(group.getId());
		//String groupId = group.getId();
		String name = group.getName();// 昵称
		
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
