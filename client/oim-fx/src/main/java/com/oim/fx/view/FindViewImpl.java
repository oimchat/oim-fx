package com.oim.fx.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.GroupListManager;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.UserListManager;
import com.oim.core.business.sender.GroupSender;
import com.oim.core.business.sender.UserSender;
import com.oim.core.business.view.AddUserView;
import com.oim.core.business.view.FindView;
import com.oim.core.business.view.JoinGroupView;
import com.oim.core.common.util.OimDateUtil;
import com.oim.fx.common.component.WaitingPane;
import com.oim.ui.fx.classics.FindFrame;
import com.oim.ui.fx.classics.find.FindGroupItem;
import com.oim.ui.fx.classics.find.FindGroupPane;
import com.oim.ui.fx.classics.find.FindUserItem;
import com.oim.ui.fx.classics.find.FindUserPane;
import com.only.common.result.Info;
import com.only.common.util.OnlyDateUtil;
import com.only.fx.common.action.ExecuteAction;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.query.GroupQuery;
import com.onlyxiahui.im.message.data.query.UserQuery;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.util.Callback;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class FindViewImpl extends AbstractView implements FindView {

	ExecuteAction userExecuteAction;
	ExecuteAction groupExecuteAction;
	PageData userPage;
	PageData groupPage;
	UserQuery userQuery;
	GroupQuery groupQuery;

	FindFrame ff;// = new FindFrame();
	FindUserPane fup;
	FindGroupPane fgp;

	public FindViewImpl(AppContext appContext) {
		super(appContext);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ff = new FindFrame();
				fup = ff.getFindUserPane();
				fgp = ff.getFindGroupPane();
				initEvent();
			}
		});
	}

	private void initEvent() {
		fup.setSearchAction(a -> {

			UserQuery user = new UserQuery();
			String queryText = fup.getText();
			String age = (String) fup.getAge();
			String sex = (String) fup.getGender();
			String homeAddress = (String) fup.getHomeAddress();
			String locationAddress = (String) fup.getLocationAddress();

			user.setQueryText(queryText);

			if (null == sex || "".equals(sex)) {
				user.setGender(null);
			} else if (!"不限".equals(sex) && !"性别".equals(sex)) {
				sex = ("男".equals(sex)) ? "1" : "2";
				user.setGender(sex);
			} else {
				user.setGender(null);
			}
			user.setHomeAddress(homeAddress);
			user.setLocationAddress(locationAddress);

			// "不限",
			// "18岁以下",
			// "18-22岁",
			// "23-26岁",
			// "27-35岁",
			// "35岁以上"
			if ("18岁以下".equals(age)) {
				long now = System.currentTimeMillis();
				String start = OnlyDateUtil.dateToDate(new Date((now - (18L * 31536000000l))));
				// String end = OnlyDateUtil.dateToDate(new Date((now - (18l *
				// 31536000000l))));
				user.setStartBirthdate(start);
				user.setEndBirthdate("");
			} else if ("18-22岁".equals(age)) {
				long now = System.currentTimeMillis();
				String start = OnlyDateUtil.dateToDate(new Date((now - (22L * 31536000000l))));
				String end = OnlyDateUtil.dateToDate(new Date((now - (18L * 31536000000l))));
				user.setStartBirthdate(start);
				user.setEndBirthdate(end);
			} else if ("23-26岁".equals(age)) {
				long now = System.currentTimeMillis();
				String start = OnlyDateUtil.dateToDate(new Date((now - (26L * 31536000000l))));
				String end = OnlyDateUtil.dateToDate(new Date((now - (23L * 31536000000l))));
				user.setStartBirthdate(start);
				user.setEndBirthdate(end);
			} else if ("27-35岁".equals(age)) {
				long now = System.currentTimeMillis();
				String start = OnlyDateUtil.dateToDate(new Date((now - (35L * 31536000000l))));
				String end = OnlyDateUtil.dateToDate(new Date((now - (27L * 31536000000l))));
				user.setStartBirthdate(start);
				user.setEndBirthdate(end);
			} else if ("35岁以上".equals(age)) {
				long now = System.currentTimeMillis();
				String end = OnlyDateUtil.dateToDate(new Date((now - (35L * 31536000000l))));
				user.setStartBirthdate("");
				user.setEndBirthdate(end);
			} else {
				user.setStartBirthdate("");
				user.setEndBirthdate("");
			}

			queryUserDataList(user);
		});

		fgp.setSearchAction(a -> {
			GroupQuery group = new GroupQuery();
			String queryText = fgp.getText();
			group.setQueryText(queryText);
			queryGroupList(group);
		});

		fup.getQueryListPane().setPage(0, 10);
		fup.getQueryListPane().setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {
				userPageChange(index + 1);
				return new Label("第" + (index + 1) + "页");
			}
		});

		fgp.getQueryListPane().setPage(0, 10);
		fgp.getQueryListPane().setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {
				groupPageChange(index + 1);
				return new Label("第" + (index + 1) + "页");
			}
		});
	}

	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					ff.show();
				} else {
					ff.hide();
				}
			}
		});
	}

	public boolean isShowing() {
		return ff.isShowing();

	}

	public void initData() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				fup.initData();
				fgp.initData();
			}
		});
	}

	private void userPageChange(int number) {
		if (null == userPage) {
			userPage = new PageData();
			userPage.setPageSize(16);
		}
		userPage.setPageNumber(number);
		queryUserDataList(userQuery, userPage);
	}

	private void groupPageChange(int number) {
		if (null == groupPage) {
			groupPage = new PageData();
			groupPage.setPageSize(16);
		}
		groupPage.setPageNumber(number);
		queryGroupList(groupQuery, userPage);
	}

	public void queryUserDataList(UserQuery userQuery) {
		this.userQuery = userQuery;
		if (null == userPage) {
			userPage = new PageData();
			userPage.setPageSize(16);
		}
		userPage.setPageNumber(1);
		queryUserDataList(userQuery, userPage);
	}

	public void queryGroupList(GroupQuery groupQuery) {
		this.groupQuery = groupQuery;
		if (null == groupPage) {
			groupPage = new PageData();
			groupPage.setPageSize(16);
		}
		groupPage.setPageNumber(1);
		queryGroupList(groupQuery, groupPage);
	}

	public void queryUserDataList(UserQuery userQuery, PageData page) {
		fup.getQueryListPane().showWaiting(true, WaitingPane.show_waiting);
		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Override
			public void lost() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						fup.getQueryListPane().showWaiting(true, WaitingPane.show_result);
					}
				});
			}

			@Override
			public void timeOut() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						fup.getQueryListPane().showWaiting(true, WaitingPane.show_result);
					}
				});
			}

			@Back
			public void back(Info info, @Define("userDataList") List<UserData> userDataList, @Define("page") PageData page) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						fup.getQueryListPane().showWaiting(false, WaitingPane.show_waiting);
						setUserDataList(userDataList, page);
					}
				});
			}
		};

		UserSender uh = this.appContext.getSender(UserSender.class);
		uh.queryUserDataList(userQuery, page, dataBackAction);
	}

	public void queryGroupList(GroupQuery groupQuery, PageData page) {

		fgp.getQueryListPane().showWaiting(true, WaitingPane.show_waiting);
		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Override
			public void lost() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						fgp.getQueryListPane().showWaiting(true, WaitingPane.show_result);
					}
				});
			}

			@Override
			public void timeOut() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						fgp.getQueryListPane().showWaiting(true, WaitingPane.show_result);
					}
				});
			}

			@Back
			public void back(Info info, @Define("groupList") List<Group> groupList, @Define("page") PageData page) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						fgp.getQueryListPane().showWaiting(false, WaitingPane.show_waiting);
						setGroupList(groupList, page);
					}
				});
			}
		};
		GroupSender gh = this.appContext.getSender(GroupSender.class);
		gh.queryGroupList(groupQuery, page, dataBackAction);
	}

	public void setUserDataList(List<UserData> userDataList, PageData page) {
		List<FindUserItem> list = new ArrayList<FindUserItem>();
		if (null != userDataList) {
			
			HeadImageManager him=appContext.getManager(HeadImageManager.class);
			for (UserData userData : userDataList) {

				String nickname = userData.getNickname();
				// String account = userData.getAccount();
				String gender = userData.getGender();
				StringBuilder sb = new StringBuilder();

				String genderText = "保密";
				if ("1".equals(gender)) {
					genderText = "男";
				}
				if ("2".equals(gender)) {
					genderText = "女";
				}
				sb.append("性别：");
				sb.append(genderText);
				if (null != userData.getBirthdate()) {
					sb.append("|");
					int y = OimDateUtil.beforePresentYearCount(userData.getBirthdate());
					sb.append("年龄：");
					sb.append(y);
					sb.append("岁");
				}
				// sb.append("\n");
				// sb.append("11111111111");

				// if(null!=userData.getSignature()&&!"".equals(userData.getSignature())){
				// sb.append("|");
				// sb.append(userData.getSignature());
				// }else{
				// sb.append("\n");
				// }

				FindUserItem head = new FindUserItem();
				
				Image image =him.getUserHeadImageByKey(userData.getHead(), 100);
				head.setHeadImage(image);
				head.setNickname(nickname);
				head.setShowText(sb.toString());

				head.setPrefSize(200, 100);
				head.setMaxWidth(200);
				head.setMaxHeight(100);

				head.addAttribute("userData", userData);
				head.setAddAction(a -> {
					openAddView(userData);
				});
				list.add(head);
			}
		}
		int totalPage = 1;
		if (null != page && page.getTotalPage() > 0) {
			totalPage = page.getTotalPage();
		}
		fup.getQueryListPane().setTotalPage(totalPage);
		fup.getQueryListPane().setNodeList(list);
	}

	public void setGroupList(List<Group> groupList, PageData page) {

		List<FindGroupItem> list = new ArrayList<FindGroupItem>();
		if (null != groupList) {
			HeadImageManager him=appContext.getManager(HeadImageManager.class);
			
			for (Group group : groupList) {

				Image image =him.getGroupHeadImageByKey(group.getHead());
				
				String name = group.getName();
				String number = group.getNumber() + "";

				StringBuilder showText = new StringBuilder();
				showText.append("(");
				showText.append(number);
				showText.append(")|");
				showText.append(group.getIntroduce());

				StringBuilder infoText = new StringBuilder();
				infoText.append(group.getPublicNotice());

				FindGroupItem head = new FindGroupItem();

				head.setHeadImage(image);
				head.setName(name);
				head.setShowText(showText.toString());
				head.setInfoText(infoText.toString());

				head.setPrefSize(275, 160);
				head.setMaxWidth(275);
				head.setMaxHeight(160);

				head.setAddAction(a -> {
					openJoinGroupView(group);
				});
				list.add(head);
			}
		}

		int totalPage = 1;
		if (null != page && page.getTotalPage() > 0) {
			totalPage = page.getTotalPage();
		}
		fgp.getQueryListPane().setTotalPage(totalPage);
		fgp.getQueryListPane().setNodeList(list);
	}

	public void openAddView(UserData userData) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		if (ub.inMemberList(userData.getId())) {
			ff.showPrompt(userData.getNickname() + "已经是你的好友！");
			return;
		}
		UserListManager listManage = this.appContext.getManager(UserListManager.class);

		AddUserView addView = appContext.getSingleView(AddUserView.class);
		List<UserCategory> userCategoryList = listManage.getUserCategoryList();
		addView.set(userData, userCategoryList);
		addView.setVisible(true);
	}

	public void openJoinGroupView(Group g) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		if (gb.hasGroup(g.getId())) {
			ff.showPrompt("你已经加入" + g.getName());
			return;
		}
		GroupListManager listManage = this.appContext.getManager(GroupListManager.class);
		List<GroupCategory> groupCategoryList = listManage.getGroupCategoryList();

		JoinGroupView addView = appContext.getSingleView(JoinGroupView.class);
		addView.set(g, groupCategoryList);
		addView.setVisible(true);
	}

	@Override
	public void showPrompt(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectedTab(int index) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ff.selectedTab(index);
			}
		});
	}
}
