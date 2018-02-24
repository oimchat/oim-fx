package com.oim.fx.view;

import java.util.List;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.UserListManager;
import com.oim.core.business.sender.UserCategoryMemberSender;
import com.oim.core.business.sender.UserCategorySender;
import com.oim.core.business.service.UserService;
import com.oim.core.business.view.AddUserView;
import com.oim.core.common.util.HereStringUtil;
import com.oim.core.common.util.OimDateUtil;
import com.oim.ui.fx.classics.AddFrame;
import com.oim.ui.fx.classics.add.InfoPane;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;

import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * 描述： 添加好友或者加入群显示窗口
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class AddUserViewImpl extends AbstractView implements AddUserView {

	AddFrame addFrame;// = new AddFrame();
	private UserData userData;

	public AddUserViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				addFrame = new AddFrame();
				addFrame.setTitle("添加好友");
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
				addUserCategory(name);
			}
		});
	}

	public void setUserCategoryList(List<UserCategory> userCategoryList) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				addFrame.clearCategory();
				for (UserCategory userCategory : userCategoryList) {
					addFrame.addCategory(userCategory.getId(), userCategory.getName());
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

	public void setUserData(UserData userData) {
		this.userData = userData;
		HeadImageManager him=appContext.getManager(HeadImageManager.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Image image =him.getUserHeadImageByKey( userData.getHead(), 100);
				
				//String nickname = userData.getNickname();
				// String account = userData.getAccount();
				String signature = userData.getSignature();
				// StringBuilder sb = new StringBuilder();
				String gender = userData.getGender();

				StringBuilder showText = new StringBuilder();
				showText.append("昵称：");
				showText.append(HereStringUtil.value(userData.getNickname()));
				showText.append("\n");
				
				showText.append("账号：");
				showText.append(HereStringUtil.value(userData.getAccount())+ "");
				showText.append("\n");
				
				String genderText = "保密";
				if ("1".equals(gender)) {
					genderText = "男";
				}
				if ("2".equals(gender)) {
					genderText = "女";
				}

				showText.append("性别：");
				showText.append(genderText);
				showText.append("\n");

				showText.append("年龄：");
				if (null != userData.getBirthdate()) {
					int y = OimDateUtil.beforePresentYearCount(userData.getBirthdate());
					showText.append(y);
					showText.append("岁");
				}
				showText.append("\n");

				showText.append("故乡：");
				showText.append(userData.getHomeAddress());
				showText.append("\n");

				showText.append(signature);
				showText.append("\n");

				addFrame.clearData();
				InfoPane ip = addFrame.getInfoPanel();
				ip.setHeadImage(image);
//				ip.setName(nickname);
//				ip.setNumber(userData.getNumber() + "");
				ip.setInfoText(showText.toString());
			}
		});
	}

	public void set(UserData userData, List<UserCategory> userCategoryList) {
		setUserData(userData);
		setUserCategoryList(userCategoryList);
	}

	private void doneAction() {
		String userCategoryId = this.addFrame.getCategoryId();
		String remarks = addFrame.getRemark();
		addUserCategoryMember(userData, userCategoryId, remarks);
	}

	public void addUserCategory(String name) {
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
				public void back(Info info, @Define("userCategory") UserCategory userCategory) {

					if (info.isSuccess()) {
						UserService userService = appContext.getService(UserService.class);
						userService.addUserCategory(userCategory);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								addFrame.addCategory(userCategory.getId(), userCategory.getName());
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

			UserCategorySender uch = this.appContext.getSender(UserCategorySender.class);
			uch.addUserCategory(name, action);
		}
	}

	public void addUserCategoryMember(UserData user, String userCategoryId, String remark) {
		if (null == userCategoryId || "".equals(userCategoryId)) {
			addFrame.showPrompt("请选择或者新建分组！");
			return;
		}

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
			public void back(Info info, @Define("userCategoryMember") UserCategoryMember userCategoryMember) {
				if (info.isSuccess()) {
					setVisible(false);
					UserListManager listManage = appContext.getManager(UserListManager.class);
					UserData user = this.getAttribute(UserData.class.getName());
					listManage.add(user, userCategoryMember);
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
		action.addAttribute(UserData.class.getName(), user);
		UserCategoryMemberSender uch = this.appContext.getSender(UserCategoryMemberSender.class);
		uch.addUserCategoryMember(userCategoryId, user.getId(), remark, action);
	}

	public void showWaiting(boolean show) {

	}

	@Override
	public void showPrompt(String text) {

	}
}
