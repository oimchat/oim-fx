package com.oim.fx.view.function;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.service.GroupChatService;
import com.oim.core.business.view.GroupDataView;
import com.oim.core.business.view.GroupEditView;
import com.oim.fx.ui.main.GroupContextMenu;
import com.oim.ui.fx.classics.list.HeadItem;
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
public class GroupHeadFunction extends AbstractComponent {
	
	protected GroupContextMenu gcm = new GroupContextMenu();

	public GroupHeadFunction(AppContext appContext) {
		super(appContext);
		initialize();
	}

	protected void initialize() {
		gcm.setUpdateAction(g -> {
			GroupEditView view = appContext.getSingleView(GroupEditView.class);
			view.setGroup(g);
			view.setVisible(true);
		});

		gcm.setShowAction(g -> {
			GroupDataView v = appContext.getSingleView(GroupDataView.class);
			v.setGroup(g);
			v.setVisible(true);
		});
	}

	public void setGroupHeadEvent(HeadItem head) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				Group group = head.getAttribute(Group.class);
				PersonalBox pb = appContext.getBox(PersonalBox.class);
				boolean isOwner = pb.isOwner(group.getId());
				gcm.showEdit(isOwner);
				gcm.setGroup(group);
				gcm.show(head, e.getScreenX(), e.getScreenY());
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

	public void setGroupHead(HeadItem head, Group group) {
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getGroupHead(group.getId());

		head.addAttribute(Group.class, group);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				head.setHeadImage(image);
				head.setRemark(group.getName());
				head.setNickname("(" + group.getNumber() + ")");
				head.setShowText(group.getIntroduce());
			}
		});
	}
}
