package com.oim.fx.view;

import java.util.List;

import com.oim.core.business.manager.GroupListManager;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.sender.GroupCategoryMemberSender;
import com.oim.core.business.sender.GroupCategorySender;
import com.oim.core.business.service.GroupService;
import com.oim.core.business.view.JoinGroupView;
import com.oim.core.common.util.HereStringUtil;
import com.oim.ui.fx.classics.AddFrame;
import com.oim.ui.fx.classics.add.InfoPane;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.GroupCategoryMember;

import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * 描述： 添加好友或者加入群显示窗口
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class JoinGroupViewImpl extends AbstractView implements JoinGroupView {

	AddFrame addFrame;// = new AddFrame();
	private Group group;

	public JoinGroupViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				addFrame = new AddFrame();
				addFrame.setTitle("加入群");
				initEvent();
			}
		});
	}

	private void initEvent() {
		addFrame.setDoneAction(a -> {
			doneAction();
		});

		addFrame.setNewCategoryAction(name -> {
			if (null != name && !"".equals(name)) {
				addGroupCategory(name);
			}
		});
	}

	public void setGroupCategoryList(List<GroupCategory> groupCategoryList) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				addFrame.clearCategory();
				for (GroupCategory groupCategory : groupCategoryList) {
					addFrame.addCategory(groupCategory.getId(), groupCategory.getName());
				}
				addFrame.selectCategory(0);
			}
		});
	}

	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					addFrame.show();
					addFrame.toFront();
				} else {
					addFrame.hide();
				}
			}
		});
	}

	public boolean isShowing() {
		return addFrame.isShowing();
	}

	public void setGroup(Group group) {
		this.group = group;
		HeadImageManager him=appContext.getManager(HeadImageManager.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				//String name = group.getName();
				
				StringBuilder sb = new StringBuilder();
				sb.append("名称：");
				sb.append(HereStringUtil.value(group.getName()));
				sb.append("\n");
				
				sb.append("群号：");
				sb.append(HereStringUtil.value(group.getNumber() + ""));
				sb.append("\n");
				
				sb.append("简介：");
				sb.append(HereStringUtil.value(group.getIntroduce()));
				sb.append("\n");
				
				Image image =him.getGroupHeadImageByKey(group.getHead());
				addFrame.clearData();
				InfoPane ip = addFrame.getInfoPanel();
				ip.setHeadImage(image);
//				ip.setName(name);
//				ip.setNumber(group.getNumber() + "");
				ip.setInfoText(sb.toString());
			}
		});
	}

	public void set(Group group, List<GroupCategory> groupCategoryList) {
		setGroup(group);
		setGroupCategoryList(groupCategoryList);
	}

	private void doneAction() {
		String groupCategoryId = this.addFrame.getCategoryId();
		String remarks = addFrame.getRemark();
		addGroupCategoryMember(group, groupCategoryId, remarks);
	}

	public void addGroupCategory(String name) {
		if (null != name && !"".equals(name)) {
			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Override
				public void lost() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							addFrame.showPrompt("添加失败！");
						}
					});
				}

				@Override
				public void timeOut() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							addFrame.showPrompt("添加失败！");
						}
					});
				}

				@Back
				public void back(Info info, @Define("groupCategory") GroupCategory groupCategory) {

					if (info.isSuccess()) {
						GroupService groupService = appContext.getService(GroupService.class);
						groupService.addGroupCategory(groupCategory);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								addFrame.addCategory(groupCategory.getId(), groupCategory.getName());
							}
						});
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								addFrame.showPrompt("添加失败！");
							}
						});
					}
				}
			};
			GroupCategorySender gch = this.appContext.getSender(GroupCategorySender.class);
			gch.addGroupCategory(name, action);
		}
	}

	public void addGroupCategoryMember(Group group, String groupCategoryId, String remark) {

		DataBackActionAdapter action = new DataBackActionAdapter() {
			@Override
			public void lost() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						addFrame.showPrompt("加入失败！");
					}
				});
			}

			@Override
			public void timeOut() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						addFrame.showPrompt("加入失败！");
					}
				});
			}

			@Back
			public void back(Info info, @Define("groupCategoryMember") GroupCategoryMember groupCategoryMember) {
				if (info.isSuccess()) {
					setVisible(false);
					GroupListManager listManage = appContext.getManager(GroupListManager.class);
					Group group = this.getAttribute(Group.class);
					listManage.add(group, groupCategoryMember);
				} else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							addFrame.showPrompt("加入失败！");
						}
					});
				}
			}
		};
		action.addAttribute(Group.class, group);
		GroupCategoryMemberSender gch = this.appContext.getSender(GroupCategoryMemberSender.class);
		gch.addGroupCategoryMember(groupCategoryId, group.getId(), remark, action);
	}

	public void showWaiting(boolean show) {
	}

	@Override
	public void showPrompt(String text) {
	}
}
