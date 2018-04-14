package com.oim.fx.view;

import java.util.Random;

import com.oim.core.business.manager.GroupListManager;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.sender.GroupSender;
import com.oim.core.business.view.GroupEditView;
import com.oim.ui.fx.classics.GroupEditFrame;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategoryMember;

import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class GroupEditViewImpl extends AbstractView implements GroupEditView {

	GroupEditFrame frame;
	Group group;
	String head;

	public GroupEditViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new GroupEditFrame();
				frame.setTitleText("");
				initEvent();
			}
		});
	}

	protected void initEvent() {
		frame.setDoneAction(a -> {
			addOrUpdate();
		});
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					frame.show();
					frame.toFront();
				} else {
					frame.hide();
				}
			}
		});
	}

	public void addOrUpdate() {
		boolean isAdd = (group == null || null == group.getId() || "".equals(group.getId()));

		if (group == null) {
			group = new Group();
			group.setHead(head);
		}

		String name = frame.getName();
		String introduce = frame.getIntroduce();
		String publicNotice=frame.getPublicNotice();

		if (null == name || "".equals(name)) {
			showPromptMessage("名称不能空！");
			return;
		}

		group.setName(name);
		group.setIntroduce(introduce);
		group.setPublicNotice(publicNotice);
		
		setVisible(false);
		if (isAdd) {
			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Back
				public void back(Info info,
						@Define("group") Group group,
						@Define("groupCategoryMember") GroupCategoryMember groupCategoryMember) {

					if (info.isSuccess()) {
//						ListManage listManage = appContext.getManage(ListManage.class);
//						listManage.add(group, groupCategoryMember);
					} else {
						setVisible(true);
						frame.showPrompt("添加失败！");
					}
				}
			};
			GroupSender gh = this.appContext.getSender(GroupSender.class);
			gh.addGroup(group, action);
		} else {
			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Back
				public void back(Info info,
						@Define("group") Group group) {
					if (info.isSuccess()) {
						GroupListManager listManage = appContext.getManager(GroupListManager.class);
						listManage.updateGroup(group);
					} else {
						setVisible(true);
						showPrompt("修改失败！");
					}
				}
			};
			GroupSender gh = this.appContext.getSender(GroupSender.class);
			gh.updateGroup(group, action);
		}
	}

	public void setGroup(Group group) {
		this.group=group;
		
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		if (null == group) {
			int i = new Random().nextInt(3);
			i = i + 1;
			this.head = i + "";
			Image image = him.getGroupHeadImageByKey(head);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					frame.setHeadImage(image);
					frame.setName("");
					frame.setIntroduce("");
					frame.setPublicNotice("");
					frame.setTitleText("新建群");
				}
			});
		} else {
			Image image =him.getGroupHead(group.getId());
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					frame.setHeadImage(image);
					frame.setName(group.getName());
					frame.setIntroduce(group.getIntroduce());
					frame.setPublicNotice(group.getPublicNotice());
					frame.setTitleText("编辑群");
				}
			});
		}
	}

	public void showPromptMessage(String text) {
		frame.showPrompt(text);
	}

	@Override
	public boolean isShowing() {
		return false;
	}
}
