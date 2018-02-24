package com.oim.fx.v1.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.common.chat.util.ChatUtil;
import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.PersonalManager;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.view.FindView;
import com.oim.core.business.view.MainView;
import com.oim.core.business.view.SettingView;
import com.oim.core.business.view.ThemeView;
import com.oim.core.business.view.UpdatePasswordView;
import com.oim.core.business.view.UserDataEditView;
import com.oim.core.business.view.UserDataView;
import com.oim.core.common.app.view.UserPaneMenuView;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.ui.main.MainPopupMenu;
import com.oim.fx.ui.main.StatusPopupMenu;
import com.oim.fx.view.function.GroupCategoryNodeFunction;
import com.oim.fx.view.function.GroupHeadFunction;
import com.oim.fx.view.function.GroupLastFunction;
import com.oim.fx.view.function.UserCategoryNodeFunction;
import com.oim.fx.view.function.UserHeadFunction;
import com.oim.fx.view.function.UserLastFunction;
import com.oim.fx.view.node.UserPaneMenuViewImpl;
import com.oim.ui.fx.classics.MainStage;
import com.oim.ui.fx.classics.chat.LastItem;
import com.oim.ui.fx.classics.list.HeadItem;
import com.oim.ui.fx.classics.list.ListNodePane;
import com.oim.ui.fx.classics.list.ListRootPane;
import com.oim.ui.fx.classics.list.TitleListNodePane;
import com.oim.ui.fx.classics.main.MainPane;
import com.only.fx.OnlyTitlePane;
import com.only.fx.common.action.ExecuteAction;
import com.only.fx.common.component.choose.ChooseGroup;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.GroupCategoryMember;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;
import com.onlyxiahui.im.message.data.LoginData;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 17:53:20
 */
public class MainViewImpl extends AbstractView implements MainView {

	protected MainStage mainStage = new MainStage();
	protected MainPane mainPane = new MainPane();
	/** 存放好友分组列表组件 **/
	protected Map<String, ListNodePane> userListNodeMap = new ConcurrentHashMap<String, ListNodePane>();
	/** 存放群分组组件 **/
	protected Map<String, ListNodePane> groupListNodeMap = new ConcurrentHashMap<String, ListNodePane>();
	/** 存放单个好友组件 ***/
	protected Map<String, Map<String, HeadItem>> userHeadItemMap = new ConcurrentHashMap<String, Map<String, HeadItem>>();
	/** 存放单个群 **/
	protected Map<String, Map<String, HeadItem>> groupHeadItemMap = new ConcurrentHashMap<String, Map<String, HeadItem>>();

	protected Map<String, LastItem> userLastItemMap = new ConcurrentHashMap<String, LastItem>();
	protected Map<String, LastItem> groupLastItemMap = new ConcurrentHashMap<String, LastItem>();
	protected ChooseGroup lastChooseGroup = new ChooseGroup();
	protected ChooseGroup userHeadChooseGroup = new ChooseGroup();
	protected ChooseGroup groupHeadChooseGroup = new ChooseGroup();
	
	protected ListRootPane userRootListPane = new ListRootPane();
	protected ListRootPane groupRootListPane = new ListRootPane();
	protected ListRootPane lastRootListPane = new ListRootPane();
	protected Alert alert = createAlert();

	protected StatusPopupMenu statusPopupMenu = new StatusPopupMenu();
	protected MainPopupMenu mainPopupMenu = new MainPopupMenu();
	protected UserPaneMenuView upmv;
	protected TitleListNodePane findUserListPane = new TitleListNodePane();
	protected TitleListNodePane findGroupListPane = new TitleListNodePane();
	
	public MainViewImpl(AppContext appContext) {
		super(appContext);
		initialize();
		initIocn();
		initComponent();
		initEvent();
	}

	private void initialize() {
		mainStage.setCenter(mainPane);
		mainStage.getRootPane().getStylesheets().add(this.getClass().getResource("/classics/css/main.css").toString());
		upmv = new UserPaneMenuViewImpl(appContext);
	}

	private void initIocn() {

		Image businessImage = null;

		businessImage = ImageBox.getImageClassPath("/oim/classics/images/main/title/btn_Skin_normal_2.png");
		IconButton themeIconButton = new IconButton(businessImage);
		themeIconButton.setPrefSize(30, 27);
		themeIconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ThemeView findView = appContext.getSingleView(ThemeView.class);
				findView.setVisible(true);
			}
		});
		OnlyTitlePane titlePane = mainStage.getOnlyTitlePane();
		titlePane.getChildren().add(0, themeIconButton);
		
		///////////////////////////////////////////// function
		Image normalImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/mainmenu.png");
		Image hoverImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/mainmenu.png");
		Image pressedImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/mainmenu.png");
		IconPane menuIconButton = new IconPane(normalImage, hoverImage, pressedImage);

		normalImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/find.png");
		hoverImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/find_hover.png");
		pressedImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/find_down.png");
		IconPane findIconButton = new IconPane(normalImage, hoverImage, pressedImage);
		
		normalImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/tools.png");
		hoverImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/tools_hover.png");
		pressedImage = ImageBox.getImageClassPath("/oim/classics/images/main/bottom/tools_down.png");
		IconPane settingIconButton = new IconPane(normalImage, hoverImage, pressedImage);

		menuIconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Object source = event.getSource();
				if (source instanceof Node) {
					Node node = (Node) source;
					mainPopupMenu.show(node, Side.TOP, node.getLayoutX(), node.getLayoutY());
				}
			}
		});
		
		findIconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				FindView findView = appContext.getSingleView(FindView.class);
				findView.setVisible(true);
			}
		});
		
		settingIconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				SettingView view = appContext.getSingleView(SettingView.class);
				view.setVisible(true);
			}
		});

		mainPane.addFunctionIcon(menuIconButton);
		mainPane.addFunctionIcon(findIconButton);
		mainPane.addFunctionIcon(settingIconButton);
	}

	private void initComponent() {

		Image normalImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_contacts_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_contacts_hover.png");
		Image selectedImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_contacts_selected.png");

		mainPane.addTab(normalImage, hoverImage, selectedImage, userRootListPane);

		normalImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_group_normal.png");
		hoverImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_group_hover.png");
		selectedImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_group_selected.png");

		mainPane.addTab(normalImage, hoverImage, selectedImage, groupRootListPane);

		normalImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_last_normal.png");
		hoverImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_last_hover.png");
		selectedImage = ImageBox.getImageClassPath("/oim/classics/images/main/tab/icon_last_selected.png");

		mainPane.addTab(normalImage, hoverImage, selectedImage, lastRootListPane);
		
		findUserListPane.setText("查到的联系人");
		findGroupListPane.setText("查到的群");
		mainPane.getFindListPane().addNode(findUserListPane);
		mainPane.getFindListPane().addNode(findGroupListPane);
	}

	private void initEvent() {

		userRootListPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {
				upmv.show(mainStage, event.getScreenX(), event.getScreenY());
				event.consume();
			}
		});

		statusPopupMenu.setStatusAction(new ExecuteAction() {

			@Override
			public <T, E> E execute(T value) {
				if (value instanceof String) {
					PersonalManager pm = appContext.getManager(PersonalManager.class);
					pm.updateStatus((String) value);
				}
				return null;
			}

		});
		mainPopupMenu.setStatusAction(new ExecuteAction() {

			@Override
			public <T, E> E execute(T value) {
				if (value instanceof String) {
					PersonalManager pm = appContext.getManager(PersonalManager.class);
					pm.updateStatus((String) value);
				}
				return null;
			}

		});
		mainPane.setOnStatusMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Object source = event.getSource();
				if (source instanceof Node) {
					Node node = (Node) source;
					statusPopupMenu.show(node, Side.BOTTOM, node.getLayoutX(), node.getLayoutY());
				}
			}
		});

		mainPane.setOnHeadMouseClicked(m -> {
			PersonalBox pb = appContext.getBox(PersonalBox.class);
			UserData user = pb.getUserData();
			UserDataView v = appContext.getSingleView(UserDataView.class);
			v.setVisible(true);
			v.showUserData(user);
		});

		mainPopupMenu.setQuitAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SystemModule sm = appContext.getModule(SystemModule.class);
				sm.exit();
			}
		});
		mainPopupMenu.setUpdatePasswordAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				UpdatePasswordView upv = appContext.getSingleView(UpdatePasswordView.class);
				upv.setVisible(true);
			}
		});

		mainPopupMenu.setUpdateAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				UserDataEditView view = appContext.getSingleView(UserDataEditView.class);
				view.setVisible(true);
			}
		});
		
		TextField textField = mainPane.getFindTextField();
		textField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String text = textField.getText();
				find(text);
			}
		});
	}
	private void find(String text) {
		ExecuteTask run = new ExecuteTask() {
			@Override
			public void execute() {
				UserDataBox ub = appContext.getBox( UserDataBox.class);
				GroupBox gb = appContext.getBox( GroupBox.class);
				UserHeadFunction uhf = appContext.getObject(UserHeadFunction.class);
				GroupHeadFunction ghf = appContext.getObject(GroupHeadFunction.class);
				
				List<UserData> userList = ub.findUserDataList(text);
				List<Group> groupList = gb.findGroupList(text);

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						findUserListPane.clearItem();
						findGroupListPane.clearItem();
					}
				});
				ChooseGroup chooseGroup = new ChooseGroup();
				for (UserData userData : userList) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							HeadItem head = new HeadItem();
							head.setChooseGroup(chooseGroup);
							uhf.setUserDataHead(head, userData);
							uhf.setUserDataHeadEvent(head);
							findUserListPane.addItem(head);
						}
					});
				}
				for (Group group : groupList) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							HeadItem head = new HeadItem();
							head.setChooseGroup(chooseGroup);
							ghf.setGroupHead(head, group);
							ghf.setGroupHeadEvent(head);
							findGroupListPane.addItem(head);
						}
					});
				}
			}
		};
		appContext.add(run);
	}
	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					mainStage.show();
					mainStage.toFront();
				} else {
					mainStage.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return mainStage.isShowing();
	}

	@Override
	public void showPrompt(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mainStage.showPrompt(text);
			}
		});
	}

	@Override
	public void setStatus(String status) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Image statusImage = ImageBox.getStatusImageIcon(status);
				mainPane.setStatusImage(statusImage);
			}
		});
	}

	@Override
	public void setPersonalData(UserData user) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				PersonalBox pb = appContext.getBox(PersonalBox.class);
				LoginData loginData = pb.getLoginData();
				Image statusImage = ImageBox.getStatusImageIcon(loginData.getStatus());
				HeadImageManager him = appContext.getManager(HeadImageManager.class);
				Image headImage = him.getPersonalHeadImage();
				String name = user.getNickname();
				if (null == name || "".equals(name)) {
					name = user.getName();
				}
				if (null == name || "".equals(name)) {
					name = user.getAccount();
				}
				if (null == name || "".equals(name)) {
					name = user.getNumber() + "";
				}
				mainPane.setHeadImage(headImage);
				mainPane.setStatusImage(statusImage);
				mainPane.setNickname(name);
				mainPane.setText(user.getSignature());
			}
		});
	}

	/****************** user list start **********************/
	@Override
	public void clearUserCategory() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				userRootListPane.clearNode();
				userListNodeMap.clear();
			}
		});
	}

	@Override
	public void addOrUpdateUserCategory(UserCategory userCategory) {
		UserCategoryNodeFunction ucnf = appContext.getObject(UserCategoryNodeFunction.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String userCategoryId = userCategory.getId();
				ListNodePane node = userListNodeMap.get(userCategoryId);
				if (null == node) {
					node = new ListNodePane();
					userListNodeMap.put(userCategoryId, node);
					ucnf.setUserCategoryNodeEvent(node);
				}
				node.setText(userCategory.getName());
				node.setUserData(userCategory);
				userRootListPane.addNode(node);
			}
		});
	}

	@Override
	public void addOrUpdateUser(String userCategoryId, UserData userData) {
		UserHeadFunction uhf = appContext.getObject(UserHeadFunction.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String userId = userData.getId();
				Map<String, HeadItem> map = userHeadItemMap.get(userCategoryId);
				if (null == map) {
					map = new HashMap<String, HeadItem>();
					userHeadItemMap.put(userCategoryId, map);
				}

				HeadItem head = map.get(userData.getId());
				if (null == head) {
					head = new HeadItem();
					map.put(userId, head);
					head.setChooseGroup(userHeadChooseGroup);
					uhf.setUserDataHeadEvent(head);
				}
				uhf.setUserDataHead(head, userData);
				ListNodePane node = userListNodeMap.get(userCategoryId);
				if (null != node) {
					node.addItem(head);
				}
			}
		});

	}

	@Override
	public void removeUserCategoryMember(String userCategoryId, String userId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePane node = userListNodeMap.get(userCategoryId);
				Map<String, HeadItem> map = userHeadItemMap.get(userCategoryId);
				if (null != map) {
					HeadItem headItem = map.get(userId);
					if (null != node && null != headItem) {
						node.removeItem(headItem);
					}
				}
			}
		});

	}

	@Override
	public void clearUserCategoryMember(String userCategoryId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePane node = userListNodeMap.get(userCategoryId);
				if (null != node) {
					node.clearItem();
				}
			}
		});
	}

	@Override
	public void updateUserCategoryMemberCount(String userCategoryId, int totalCount, int onlineCount) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePane node = userListNodeMap.get(userCategoryId);
				if (null != node) {
					node.setNumberText("[" + onlineCount + "/" + totalCount + "]");
				}
			}
		});
	}

	/****************** user list end **********************/

	/****************** group list start **********************/
	@Override
	public void clearGroupCategory() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				groupRootListPane.clearNode();
				groupListNodeMap.clear();
			}
		});

	}

	@Override
	public void addOrUpdateGroupCategory(GroupCategory groupCategory) {
		GroupCategoryNodeFunction gcnf = appContext.getObject(GroupCategoryNodeFunction.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				String groupCategoryId = groupCategory.getId();
				ListNodePane node = groupListNodeMap.get(groupCategoryId);
				if (null == node) {
					node = new ListNodePane();
					groupListNodeMap.put(groupCategoryId, node);
					gcnf.setGroupCategoryNodeEvent(node);
				}
				node.setText(groupCategory.getName());
				node.setUserData(groupCategory);
				groupRootListPane.addNode(node);
			}
		});
	}

	@Override
	public void addOrUpdateGroup(String groupCategoryId, Group group) {
		GroupHeadFunction ghf = appContext.getObject(GroupHeadFunction.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String groupId = group.getId();
				Map<String, HeadItem> map = groupHeadItemMap.get(groupCategoryId);
				if (null == map) {
					map = new HashMap<String, HeadItem>();
					groupHeadItemMap.put(groupCategoryId, map);
				}

				HeadItem head = map.get(group.getId());
				if (null == head) {
					head = new HeadItem();
					map.put(groupId, head);
					head.setChooseGroup(groupHeadChooseGroup);
					ghf.setGroupHeadEvent(head);
				}
				ghf.setGroupHead(head, group);
				ListNodePane node = groupListNodeMap.get(groupCategoryId);
				if (null != node) {
					node.addItem(head);
				}
			}
		});

	}

	@Override
	public void removeGroupCategoryMember(String groupCategoryId, String groupId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePane node = groupListNodeMap.get(groupCategoryId);
				Map<String, HeadItem> map = groupHeadItemMap.get(groupCategoryId);
				if (null != map) {
					HeadItem headItem = map.get(groupId);
					if (null != node && null != headItem) {
						node.removeItem(headItem);
					}
				}
			}
		});
	}

	@Override
	public void clearGroupCategoryMember(String groupCategoryId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePane node = groupListNodeMap.get(groupCategoryId);
				if (null != node) {
					node.clearItem();
				}
			}
		});

	}

	@Override
	public void updateGroupCategoryMemberCount(String groupCategoryId, int totalCount) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePane node = groupListNodeMap.get(groupCategoryId);
				if (null != node) {
					node.setNumberText("[" + totalCount + "]");
				}
			}
		});
	}

	/****************** group list end **********************/

	/****************** user last start **********************/
	@Override
	public void addOrUpdateLastUser(UserData userData) {
		UserLastFunction ulf = appContext.getObject(UserLastFunction.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String userId = userData.getId();
				LastItem head = userLastItemMap.get(userId);
				if (null == head) {
					head = new LastItem();
					head.setChooseGroup(lastChooseGroup);
					userLastItemMap.put(userId, head);
					ulf.setUserDataHeadEvent(head);
				}
				addLastItem(head);
				ulf.setUserDataHead(head, userData);
			}
		});
	}

	@Override
	public void showUserHeadPulse(String userId, boolean pulse) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		List<UserCategoryMember> list = ub.getUserInCategoryMemberListByUserId(userId);

		if (null != list && !list.isEmpty()) {
			for (UserCategoryMember m : list) {
				String categoryId = m.getCategoryId();
				Map<String, HeadItem> map = userHeadItemMap.get(categoryId);
				if (null != map) {
					HeadItem head = map.get(userId);
					if (null != head) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								head.setPulse(pulse);
							}
						});
					}
				}
			}
		}
	}

	@Override
	public void setLastUserItemRed(String userId, boolean red, int count) {
		LastItem head = userLastItemMap.get(userId);
		if (null != head) {
			redHead(head, red, count);
		}
	}

	@Override
	public void updateLastUserChatInfo(String userId, String text, String time) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				LastItem head = userLastItemMap.get(userId);
				if (null != head) {
					head.setText(text);
					head.setTime(time);
					if (null == text) {
						head.setText("");
					} else {
						head.setText(text);
					}
					head.setTime(time);
				}
			}
		});

	}

	/****************** user last end **********************/

	/****************** group last start **********************/
	@Override
	public void addOrUpdateLastGroup(Group group) {
		GroupLastFunction glf = appContext.getObject(GroupLastFunction.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String groupId = group.getId();
				LastItem head = groupLastItemMap.get(groupId);
				if (null == head) {
					head = new LastItem();
					head.setChooseGroup(lastChooseGroup);
					groupLastItemMap.put(groupId, head);
					glf.setGroupHeadEvent(head);
				}
				addLastItem(head);
				glf.setGroupHead(head, group);
			}
		});

	}

	@Override
	public void showGroupHeadPulse(String groupId, boolean pulse) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		List<GroupCategoryMember> list = gb.getGroupInCategoryMemberListByGroupId(groupId);

		if (null != list && !list.isEmpty()) {
			for (GroupCategoryMember m : list) {
				String categoryId = m.getGroupCategoryId();
				Map<String, HeadItem> map = groupHeadItemMap.get(categoryId);
				if (null != map) {
					HeadItem head = map.get(groupId);
					if (null != head) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								head.setPulse(pulse);
							}
						});
					}
				}
			}
		}
	}

	@Override
	public void setLastGroupItemRed(String groupId, boolean red, int count) {
		LastItem head = groupLastItemMap.get(groupId);
		if (null != head) {
			redHead(head, red, count);
		}
	}

	@Override
	public void updateLastGroupChatInfo(String groupId, String text, String time) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				LastItem head = groupLastItemMap.get(groupId);
				if (null != head) {
					head.setText(text);
					head.setTime(time);
					if (null == text) {
						head.setText("");
					} else {
						head.setText(text);
					}
					head.setTime(time);
				}
			}
		});

	}

	/****************** group list end **********************/

	@Override
	public void setUserHead(UserHead userHead) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshPersonalHead() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadImageManager him = appContext.getManager(HeadImageManager.class);
				Image headImage = him.getPersonalHeadImage();
				mainPane.setHeadImage(headImage);
			}
		});
	}

	@Override
	public void refreshUserHead() {

		UserDataBox ub = appContext.getBox(UserDataBox.class);
		HeadImageManager him = appContext.getManager(HeadImageManager.class);

		Set<String> keySet = userHeadItemMap.keySet();
		List<String> keyList = new ArrayList<String>(keySet);
		for (String categoryId : keyList) {
			Map<String, HeadItem> map = userHeadItemMap.get(categoryId);
			Set<String> userIdSet = map.keySet();
			List<String> userIdList = new ArrayList<String>(userIdSet);
			for (String userId : userIdList) {
				UserData userData = ub.getUserData(userId);
				if (null != userData) {
					HeadItem head = map.get(userId);
					Image image = him.getUserHead(userData.getId(),  40);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							head.setHeadImage(image);
						}
					});
				}
			}
		}
		Set<String> userIdSet = userLastItemMap.keySet();
		List<String> userIdList = new ArrayList<String>(userIdSet);
		for (String userId : userIdList) {
			UserData userData = ub.getUserData(userId);
			if (null != userData) {
				boolean isGray=AppCommonUtil.isOffline(userData.getStatus());
				LastItem head = userLastItemMap.get(userId);
				Image image = him.getUserHead(userData.getId(), 40);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						head.setHeadImage(image);
						head.setGray(isGray);
					}
				});
			}
		}
	}

	@Override
	public void refreshGroupHead() {
		GroupBox gb = appContext.getBox(GroupBox.class);
		HeadImageManager him = appContext.getManager(HeadImageManager.class);

		Set<String> keySet = groupHeadItemMap.keySet();
		List<String> keyList = new ArrayList<String>(keySet);
		for (String categoryId : keyList) {
			Map<String, HeadItem> map = groupHeadItemMap.get(categoryId);
			Set<String> groupIdSet = map.keySet();
			List<String> groupIdList = new ArrayList<String>(groupIdSet);
			for (String groupId : groupIdList) {
				Group group = gb.getGroup(groupId);
				if (null != group) {
					HeadItem head = map.get(groupId);
					Image image = him.getGroupHead(group.getId());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							head.setHeadImage(image);
						}
					});
				}
			}
		}
		Set<String> groupIdSet = groupLastItemMap.keySet();
		List<String> groupIdList = new ArrayList<String>(groupIdSet);
		for (String groupId : groupIdList) {
			Group group = gb.getGroup(groupId);
			if (null != group) {
				LastItem head = groupLastItemMap.get(groupId);
				Image image = him.getGroupHead(group.getId());
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						head.setHeadImage(image);
					}
				});
			}
		}
	}

	@Override
	public void shwoDifferentLogin() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				SystemModule sm = appContext.getModule(SystemModule.class);
				alert
						.showAndWait()
						.filter(response -> response == ButtonType.OK)
						.ifPresent(response -> sm.exit());
			}
		});
	}

	protected void addLastItem(LastItem head) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				int count = lastRootListPane.nodeSize();
				if (count > 50) {
					lastRootListPane.removeNode((count - 1));
				}
				lastRootListPane.addNode(0, head);
			}
		});
	}

	public void redHead(LastItem head, boolean red, int count) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				head.setRed(red);
				head.setRedText(ChatUtil.getCountText(red, count));
			}
		});
	}

	protected Alert createAlert() {
		Alert alert = new Alert(AlertType.INFORMATION, "");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(mainStage);
		alert.getDialogPane().setContentText("你的帐号在其他的地方登录！");
		alert.getDialogPane().setHeaderText(null);
		return alert;
	}
}
