package com.oim.fx.view;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.PersonalManager;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.service.GroupChatService;
import com.oim.core.business.service.UserChatService;
import com.oim.core.business.view.FindView;
import com.oim.core.business.view.GroupDataView;
import com.oim.core.business.view.GroupEditView;
import com.oim.core.business.view.MainView;
import com.oim.core.business.view.ThemeView;
import com.oim.core.business.view.UpdatePasswordView;
import com.oim.core.business.view.UserDataEditView;
import com.oim.core.business.view.UserDataView;
import com.oim.core.common.app.view.GroupCategoryMenuView;
import com.oim.core.common.app.view.UserCategoryMenuView;
import com.oim.core.common.app.view.UserHeadMenuView;
import com.oim.core.common.app.view.UserPaneMenuView;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.ui.MainFrame;
import com.oim.fx.ui.list.HeadItem;
import com.oim.fx.ui.list.ListNodePanel;
import com.oim.fx.ui.list.ListRootPanel;
import com.oim.fx.ui.main.GroupContextMenu;
import com.oim.fx.ui.main.MainPopupMenu;
import com.oim.fx.ui.main.StatusPopupMenu;
import com.oim.fx.view.node.GroupCategoryMenuViewImpl;
import com.oim.fx.view.node.UserCategoryMenuViewImpl;
import com.oim.fx.view.node.UserHeadMenuViewImpl;
import com.oim.fx.view.node.UserPaneMenuViewImpl;
import com.only.fx.common.action.ExecuteAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;
import com.onlyxiahui.im.message.data.LoginData;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;

/**
 * @author: XiaHui
 * @date: 2016年10月11日 上午9:17:32
 */
public class MainViewImpl extends AbstractView implements MainView {

	protected MainFrame mainFrame;
	protected StatusPopupMenu statusPopupMenu = new StatusPopupMenu();
	protected MainPopupMenu mainPopupMenu = new MainPopupMenu();
	protected GroupContextMenu gcm = new GroupContextMenu();
	// protected UserContextMenu ucm = new UserContextMenu();

	protected GroupCategoryMenuView gcmv;

	protected UserPaneMenuView upmv;
	protected UserHeadMenuView uhmv;
	protected UserCategoryMenuView ucmv;

	/***** 存放好友分组列表组件 *****/
	protected Map<String, ListNodePanel> userListNodeMap = new ConcurrentHashMap<String, ListNodePanel>();
	/*** 存放群分组组件 **/
	protected Map<String, ListNodePanel> groupListNodeMap = new ConcurrentHashMap<String, ListNodePanel>();
	/** 存放单个好友组件 ***/
	protected Map<String, HeadItem> userHeadLabelMap = new ConcurrentHashMap<String, HeadItem>();
	/** 存放单个群 **/
	protected Map<String, HeadItem> groupHeadLabelMap = new ConcurrentHashMap<String, HeadItem>();

	protected Map<String, HeadItem> lastMap = new ConcurrentHashMap<String, HeadItem>();

	ListRootPanel userRoot = new ListRootPanel();
	ListRootPanel groupRoot = new ListRootPanel();
	ListRootPanel lastRoot = new ListRootPanel();
	Alert alert = createAlert();

	public MainViewImpl(AppContext appContext) {
		super(appContext);
		initFrame();
		initialize();
		initIocn();
		initComponent();
		initEvent();
	}

	private void initFrame() {
		mainFrame = createFrame();
	}

	protected void initialize() {
		ucmv = new UserCategoryMenuViewImpl(appContext);
		uhmv = new UserHeadMenuViewImpl(appContext);
		upmv = new UserPaneMenuViewImpl(appContext);

		gcmv = new GroupCategoryMenuViewImpl(appContext);
	}

	protected void initIocn() {
		Image businessImage = ImageBox.getImageClassPath("/resources/main/images/top/1.png");

		IconPane iconButton = new IconPane(businessImage);
		mainFrame.addBusinessIcon(iconButton);

		businessImage = ImageBox.getImageClassPath("/resources/main/images/top/2.png");

		iconButton = new IconPane(businessImage);
		mainFrame.addBusinessIcon(iconButton);

		businessImage = ImageBox.getImageClassPath("/resources/main/images/top/3.png");

		iconButton = new IconPane(businessImage);
		mainFrame.addBusinessIcon(iconButton);

		businessImage = ImageBox.getImageClassPath("/resources/main/images/top/4.png");

		iconButton = new IconPane(businessImage);
		mainFrame.addBusinessIcon(iconButton);

		businessImage = ImageBox.getImageClassPath("/resources/main/images/top/5.png");

		iconButton = new IconPane(businessImage);
		mainFrame.addBusinessIcon(iconButton);

		businessImage = ImageBox.getImageClassPath("/resources/main/images/top/skin.png");

		iconButton = new IconPane(businessImage);
		mainFrame.addBusinessIcon(iconButton);
		iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ThemeView findView = appContext.getSingleView(ThemeView.class);
				findView.setVisible(true);
			}
		});

		///////////////////////////////////////////// function
		Image normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn2_down.png");
		Image pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn_highlight.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainFrame.addFunctionIcon(iconButton);
		iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Object source = event.getSource();
				if (source instanceof Node) {
					Node node = (Node) source;
					mainPopupMenu.show(node, Side.TOP, node.getLayoutX(), node.getLayoutY());
				}
			}
		});

		normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools_hover.png");
		pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools_down.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainFrame.addFunctionIcon(iconButton);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_highlight.png");
		pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_down.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainFrame.addFunctionIcon(iconButton);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager_hover.png");
		pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager_down.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainFrame.addFunctionIcon(iconButton);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/mycollection_mainpanel.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/myCollection_mainpanel_hover.png");
		pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/myCollection_mainpanel_down.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainFrame.addFunctionIcon(iconButton);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find_hover.png");
		pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find_down.png");

		iconButton = new IconPane("查找", normalImage, hoverImage, pressedImage);
		mainFrame.addFunctionIcon(iconButton);
		iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				FindView findView = appContext.getSingleView(FindView.class);
				findView.setVisible(true);
			}
		});

		normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/store.png");

		iconButton = new IconPane("应用宝", normalImage);
		mainFrame.addRightFunctionIcon(iconButton);

		///////////////////////////////////////////////////////////////////// app
		normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn_hover.png");
		pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn_down.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainFrame.addRightAppIcon(iconButton);

		Image appImage = ImageBox.getImageClassPath("/resources/main/images/app/1.png");

		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);

		appImage = ImageBox.getImageClassPath("/resources/main/images/app/2.png");

		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);

		appImage = ImageBox.getImageClassPath("/resources/main/images/app/3.png");

		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);

		appImage = ImageBox.getImageClassPath("/resources/main/images/app/7.png");
		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);

		appImage = ImageBox.getImageClassPath("/resources/main/images/app/8.png");
		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);

		appImage = ImageBox.getImageClassPath("/resources/main/images/app/9.png");
		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);

		appImage = ImageBox.getImageClassPath("/resources/main/images/app/10.png");
		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);

		appImage = ImageBox.getImageClassPath("/resources/main/images/app/11.png");
		iconButton = new IconPane(appImage);
		mainFrame.addAppIcon(iconButton);
	}

	protected void initComponent() {

		Image normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_hover.png");
		Image selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_selected.png");

		mainFrame.addTab(normalImage, hoverImage, selectedImage, userRoot);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_normal.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_hover.png");
		selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_selected.png");

		mainFrame.addTab(normalImage, hoverImage, selectedImage, groupRoot);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_normal.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_hover.png");
		selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_selected.png");

		mainFrame.addTab(normalImage, hoverImage, selectedImage, lastRoot);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/qzone_normal.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/qzone_hover.png");
		selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/qzone_selected.png");

		VBox website = new VBox();
		WebView websiteWebView = new WebView();
		WebEngine websiteWebEngine = websiteWebView.getEngine();
		websiteWebEngine.load("http://www.oimismine.com/");
		website.getChildren().add(websiteWebView);

		mainFrame.addTab(normalImage, hoverImage, selectedImage, website);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_tab_inco_normal.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_tab_inco_hover.png");
		selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_tab_inco_selected.png");

		VBox blog = new VBox();
		WebView blogWebView = new WebView();
		WebEngine blogWebEngine = blogWebView.getEngine();
		blogWebEngine.load("https://my.oschina.net/onlyxiahui/blog/759149");
		blog.getChildren().add(blogWebView);
		// blog.setStyle("-fx-background-color:rgba(215, 165, 230, 1)");
		mainFrame.addTab(normalImage, hoverImage, selectedImage, blog);

		normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_phone_inco_normal.png");
		hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_phone_inco_hover.png");
		selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_phone_inco_selected.png");

		VBox git = new VBox();

		WebView gitWebView = new WebView();
		WebEngine gitWebEngine = gitWebView.getEngine();
		gitWebEngine.load("https://gitee.com/onlysoftware/oim-fx");

		git.getChildren().add(gitWebView);
		// git.setStyle("-fx-background-color:rgba(112, 245, 86, 1);");
		mainFrame.addTab(normalImage, hoverImage, selectedImage, git);
	}

	protected void initEvent() {

		userRoot.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {
				upmv.show(mainFrame, event.getScreenX(), event.getScreenY());
				event.consume();
			}
		});
		// groupRoot.setOnMouseClicked((MouseEvent me) -> {
		// if (me.getClickCount() == 2) {
		// ChatManage chatManage = appContext.getManage(ChatManage.class);
		// chatManage.showCahtFrame(group);
		// } else if (me.getButton() == MouseButton.SECONDARY) {
		// gcm.setGroup(group);
		// gcm.show(mainFrame, me.getScreenX(), me.getScreenY());
		// }
		// me.consume();
		// });
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
		mainFrame.setStatusOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Object source = event.getSource();
				if (source instanceof Node) {
					Node node = (Node) source;
					statusPopupMenu.show(node, Side.BOTTOM, node.getLayoutX(), node.getLayoutY());
				}
			}
		});

		mainFrame.setHeadOnMouseClicked(m -> {
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

		// ucm.setShowAction(u -> {
		// UserDataView v = appContext.getSingleView(UserDataView.class);
		// v.showUserData(u);
		// v.setVisible(true);
		// });
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					mainFrame.show();
					mainFrame.toFront();
				} else {
					mainFrame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return mainFrame.isShowing();
	}

	@Override
	public void showPrompt(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.showPrompt(text);
			}
		});
	}

	@Override
	public void setStatus(String status) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Image statusImage = ImageBox.getStatusImageIcon(status);
				mainFrame.setStatusImage(statusImage);
			}
		});
	}

	protected ListNodePanel getUserListNode(String userCategoryId) {
		ListNodePanel node = userListNodeMap.get(userCategoryId);
		if (null == node) {
			node = new ListNodePanel();
			userListNodeMap.put(userCategoryId, node);
		}
		return node;
	}

	@Override
	public void clearUserCategory() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				userRoot.clearNode();
			}
		});
	}

	@Override
	public void removeUserCategoryMember(String userCategoryId, String userId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePanel node = userListNodeMap.get(userCategoryId);
				HeadItem headItem = userHeadLabelMap.get(userId);
				if (null != node && null != headItem) {
					node.removeItem(headItem);
				}
			}
		});
	}

	@Override
	public void clearUserCategoryMember(String userCategoryId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePanel node = userListNodeMap.get(userCategoryId);
				if (null != node) {
					node.clearItem();
				}
			}
		});
	}

	public void addOrUpdateUserCategory(UserCategory userCategory) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				String userCategoryId = userCategory.getId();

				ListNodePanel node = userListNodeMap.get(userCategoryId);
				if (null == node) {
					node = new ListNodePanel();
					userListNodeMap.put(userCategoryId, node);
				}

				node.setText(userCategory.getName());
				// node.setNumberText("[0/0]");
				node.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

					@Override
					public void handle(ContextMenuEvent event) {
						ucmv.setUserCategory(userCategory);
						ucmv.show(mainFrame, event.getScreenX(), event.getScreenY());
						event.consume();
					}
				});
				userRoot.addNode(node);
			}
		});
	}

	public void addOrUpdateUser(String userCategoryId, UserData userData) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadItem head = userHeadLabelMap.get(userData.getId());
				if (null == head) {
					head = new HeadItem();
					userHeadLabelMap.put(userData.getId(), head);
				}

				setUserDataHeadEvent(head, userData);
				setUserDataHead(head, userData);

				ListNodePanel node = userListNodeMap.get(userCategoryId);
				if (null != node) {
					node.addItem(head);
				}
			}
		});
	}

	// protected void setUserList(ListNodePanel node, String userCategoryId) {
	// ListManage lm = appContext.getManage(ListManage.class);
	// List<UserCategoryMember> userCategoryMemberList =
	// lm.getUserCategoryMemberList(userCategoryId);
	// if (null != userCategoryMemberList) {
	// for (UserCategoryMember ucm : userCategoryMemberList) {
	// HeadItem head = userHeadLabelMap.get(ucm.getMemberUserId());
	// if (null != head) {
	// node.addItem(head);
	// }
	// }
	// }
	// }

	@Override
	public void updateUserCategoryMemberCount(String userCategoryId, int totalCount, int onlineCount) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePanel node = userListNodeMap.get(userCategoryId);
				if (null != node) {
					node.setNumberText("[" + onlineCount + "/" + totalCount + "]");
				}
			}
		});
	}

	//////////////////////////////////////////////////////////////////////////

	@Override
	public void clearGroupCategory() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				groupRoot.clearNode();
			}
		});
	}

	@Override
	public void clearGroupCategoryMember(String groupCategoryId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePanel node = groupListNodeMap.get(groupCategoryId);
				if (null != node) {
					node.clearItem();
				}
			}
		});
	}

	@Override
	public void removeGroupCategoryMember(String groupCategoryId, String groupId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePanel node = groupListNodeMap.get(groupCategoryId);
				HeadItem headItem = groupHeadLabelMap.get(groupId);
				if (null != node && null != headItem) {
					node.removeItem(headItem);
				}
			}
		});
	}

	public void addOrUpdateGroupCategory(GroupCategory groupCategory) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePanel node = groupListNodeMap.get(groupCategory.getId());
				if (null == node) {
					node = new ListNodePanel();
					groupListNodeMap.put(groupCategory.getId(), node);
				}

				node.setText(groupCategory.getName());
				node.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

					@Override
					public void handle(ContextMenuEvent event) {
						gcmv.setGroupCategory(groupCategory);
						gcmv.show(mainFrame, event.getScreenX(), event.getScreenY());
						event.consume();
					}
				});
				// node.setNumberText("[0]");
				groupRoot.addNode(node);
			}
		});
	}

	@Override
	public void updateGroupCategoryMemberCount(String groupCategoryId, int totalCount) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListNodePanel node = groupListNodeMap.get(groupCategoryId);
				if (null != node) {
					node.setNumberText("[" + totalCount + "]");
				}
			}
		});
	}

	// protected void setGroupList(ListNodePanel node, String groupCategoryId) {
	// ListManage lm = appContext.getManage(ListManage.class);
	// List<GroupCategoryMember> list =
	// lm.getGroupCategoryMemberList(groupCategoryId);
	// if (null != list) {
	// for (GroupCategoryMember gcm : list) {
	// HeadItem head = groupHeadLabelMap.get(gcm.getGroupId());
	// if (null != head) {
	// node.addItem(head);
	// }
	// }
	// }
	// }

	public void addOrUpdateGroup(String groupCategoryId, Group group) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadItem head = groupHeadLabelMap.get(group.getId());
				if (null == head) {
					head = new HeadItem();
					groupHeadLabelMap.put(group.getId(), head);
				}
				setGroupHead(head, group);
				setGroupHeadEvent(head, group);
				ListNodePanel node = groupListNodeMap.get(groupCategoryId);
				if (null != node) {
					node.addItem(head);
				}
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
				mainFrame.setHeadImage(headImage);
				mainFrame.setStatusImage(statusImage);
				mainFrame.setNickname(name);
				mainFrame.setText(user.getSignature());
			}
		});
	}

	@Override
	public void showUserHeadPulse(String userId, boolean pulse) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadItem node = userHeadLabelMap.get(userId);
				if (null != node) {
					node.setPulse(pulse);
				}
			}
		});
	}

	@Override
	public void showGroupHeadPulse(String groupId, boolean pulse) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadItem node = groupHeadLabelMap.get(groupId);
				if (null != node) {
					node.setPulse(pulse);
				}
			}
		});
	}

	protected void setUserDataHead(HeadItem head, UserData userData) {
		String status = UserData.status_offline;
		if (null != userData.getStatus() && !(UserData.status_invisible.equals(userData.getStatus()))) {
			status = userData.getStatus();
		}

		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getUserHead(userData.getId(), 40);
		String remark = (null == userData.getRemarkName() || "".equals(userData.getRemarkName())) ? userData.getNickname() : userData.getRemarkName();
		String nickname = (null == userData.getRemarkName() || "".equals(userData.getRemarkName())) ? userData.getAccount() : userData.getNickname();

		head.setHeadImage(image);
		head.setRemark(remark);// 备注名
		head.setNickname("(" + nickname + ")");// 昵称
		head.setShowText(userData.getSignature());// 个性签名

		IconPane iconButton = head.getAttribute("statusLabel");
		Image iconImage = ImageBox.getStatusImageIcon(status);
		if (null == iconButton) {// 状态图标显示组件
			iconButton = new IconPane(iconImage);
			head.addAttribute("statusLabel", iconButton);
			head.addBusinessIcon(iconButton);
		}
		iconButton.setNormalImage(iconImage);
		// 如果用户不是在线状态，则使其头像变灰
		if (AppCommonUtil.isOffline(userData.getStatus())) {
			head.setGray(true);
			head.setStatus("[离线]");
		} else {
			head.setGray(false);
			head.setStatus("[在线]");
		}
	}

	protected void setUserDataLastHead(HeadItem head, UserData userData, String text) {
		String statusTemp = userData.getStatus();
		String status = (null != statusTemp && !(UserData.status_invisible.equals(statusTemp))) ? statusTemp : UserData.status_offline;

		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getUserHead(userData.getId(), 40);
		String account = userData.getAccount();
		String remark = userData.getRemarkName();
		String nickname = userData.getNickname();
		boolean hasRemark = (null != remark && !"".equals(remark));

		String remarkText = (hasRemark) ? remark : nickname;
		String nicknameText = (hasRemark) ? nickname : account;

		String nt = (null != nicknameText && !"".equals(nicknameText)) ? "(" + nicknameText + ")" : "";
		head.setHeadImage(image);
		head.setRemark(remarkText);// 备注名
		head.setNickname(nt);// 昵称

		if (null != text && !"".equals(text)) {
			head.setShowText(text);
		}

		IconPane iconButton = head.getAttribute("statusLabel");
		Image iconImage = ImageBox.getStatusImageIcon(status);

		if (null == iconButton) {// 状态图标显示组件
			iconButton = new IconPane(iconImage);
			head.addAttribute("statusLabel", iconButton);
			head.addBusinessIcon(iconButton);
		}

		iconButton.setNormalImage(iconImage);

		// 如果用户不是在线状态，则使其头像变灰
		if (AppCommonUtil.isOffline(status)) {
			head.setGray(true);
			head.setStatus("[离线]");
		} else {
			head.setGray(false);
			head.setStatus("[在线]");
		}
	}

	protected void setGroupHead(HeadItem head, Group group) {
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getGroupHead(group.getId());
		head.setHeadImage(image);
		head.setRemark(group.getName());
		head.setNickname("(" + group.getNumber() + ")");
		head.setShowText(group.getIntroduce());
	}

	protected void setGroupLastHead(HeadItem head, Group group, String text) {
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getGroupHead(group.getId());
		head.setHeadImage(image);
		head.setRemark(group.getName());
		head.setNickname("(" + group.getNumber() + ")");
		if (null != text && !"".equals(text)) {
			head.setShowText(text);
		}
	}

	protected void addLastItem(HeadItem head) {
		int count = lastRoot.nodeSize();
		if (count > 50) {
			lastRoot.removeNode((count - 1));
		}
		lastRoot.addNode(0, head);
	}

	@Override
	public void shwoDifferentLogin() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				SystemModule sm = appContext.getModule(SystemModule.class);
				alert.showAndWait()
						.filter(response -> response == ButtonType.OK)
						.ifPresent(response -> sm.exit());
			}
		});
	}

	protected Alert createAlert() {

		Alert alert = new Alert(AlertType.INFORMATION, "");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(mainFrame);
		alert.getDialogPane().setContentText("你的帐号在其他的地方登录！");
		alert.getDialogPane().setHeaderText(null);

		return alert;
	}

	@Override
	public void addOrUpdateLastUser(UserData userData) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadItem head = getUserDataLastHeadItem(userData);
				setUserDataLastHead(head, userData, "");
				addLastItem(head);
			}
		});
	}

	@Override
	public void addOrUpdateLastGroup(Group group) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadItem head = getGroupLastHeadItem(group);
				setGroupLastHead(head, group, "");
				addLastItem(head);
			}
		});
	}

	protected HeadItem getUserDataLastHeadItem(UserData userData) {
		HeadItem head = lastMap.get(userData.getId());
		if (null == head) {
			head = new HeadItem();
			lastMap.put(userData.getId(), head);
			setUserDataHeadEvent(head, userData);
		}
		return head;
	}

	protected HeadItem getGroupLastHeadItem(Group group) {
		HeadItem head = lastMap.get(group.getId());
		if (null == head) {
			head = new HeadItem();
			lastMap.put(group.getId(), head);
			setGroupHeadEvent(head, group);
		}
		return head;
	}

	/**
	 * 设置用户头像点击事件
	 * 
	 * @author: XiaHui
	 * @param head
	 * @param userData
	 * @createDate: 2017年6月9日 上午11:17:31
	 * @update: XiaHui
	 * @updateDate: 2017年6月9日 上午11:17:31
	 */
	protected void setUserDataHeadEvent(HeadItem head, UserData userData) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent e) {
				uhmv.setUserData(userData);
				uhmv.show(mainFrame, e.getScreenX(), e.getScreenY());
				e.consume();
			}
		});
		head.setOnMouseClicked((MouseEvent me) -> {
			if (me.getClickCount() == 2) {

				UserChatService cs = appContext.getService(UserChatService.class);
				cs.showUserChat(userData);
			}
			me.consume();
		});
	}

	/**
	 * 设置群头像的点击事件
	 * 
	 * @author: XiaHui
	 * @param head
	 * @param group
	 * @createDate: 2017年6月9日 上午11:12:22
	 * @update: XiaHui
	 * @updateDate: 2017年6月9日 上午11:12:22
	 */
	protected void setGroupHeadEvent(HeadItem head, Group group) {
		head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent e) {
				PersonalBox pb = appContext.getBox(PersonalBox.class);
				boolean isOwner = pb.isOwner(group.getId());
				gcm.showEdit(isOwner);
				gcm.setGroup(group);
				gcm.show(mainFrame, e.getScreenX(), e.getScreenY());
				e.consume();
			}
		});
		head.setOnMouseClicked((MouseEvent me) -> {
			if (me.getClickCount() == 2) {
				GroupChatService cs = appContext.getService(GroupChatService.class);
				cs.showGroupChat(group);
			} else if (me.getButton() == MouseButton.SECONDARY) {
				// gcm.setGroup(group);
				// gcm.show(mainFrame, me.getScreenX(), me.getScreenY());
			}
			me.consume();
		});
	}

	@Override
	public void setUserHead(UserHead userHead) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshUserHead() {
		Set<String> keySet = userHeadLabelMap.keySet();
		for (String userId : keySet) {
			UserDataBox ub = appContext.getBox(UserDataBox.class);
			UserData userData = ub.getUserData(userId);
			if (null != userData) {
				HeadImageManager him = appContext.getManager(HeadImageManager.class);
				Image image = him.getUserHead(userData.getId(), 40);
				HeadItem head = userHeadLabelMap.get(userId);
				head.setHeadImage(image);

				String statusTemp = userData.getStatus();
				String status = (null != statusTemp && !(UserData.status_invisible.equals(statusTemp))) ? statusTemp : UserData.status_offline;
				boolean isGray = AppCommonUtil.isOffline(status);

				if (isGray) {
					head.setGray(true);
					head.setStatus("[离线]");
				} else {
					head.setGray(false);
					head.setStatus("[在线]");
				}

				HeadItem lastHead = lastMap.get(userId);
				if (null != lastHead) {
					lastHead.setHeadImage(image);
					if (isGray) {
						lastHead.setGray(true);
						head.setStatus("[离线]");
					} else {
						lastHead.setGray(false);
						head.setStatus("[在线]");
					}
				}
			}
		}
	}

	@Override
	public void refreshGroupHead() {
		GroupBox gb = appContext.getBox(GroupBox.class);
		Set<String> keySet = groupHeadLabelMap.keySet();
		for (String groupId : keySet) {
			Group group = gb.getGroup(groupId);
			if (null != group) {
				HeadImageManager him = appContext.getManager(HeadImageManager.class);
				Image image = him.getGroupHead(group.getId());
				HeadItem head = groupHeadLabelMap.get(groupId);
				head.setHeadImage(image);
				HeadItem lastHead = lastMap.get(groupId);
				if (null != lastHead) {
					lastHead.setHeadImage(image);
				}
			}
		}
	}

	@Override
	public void refreshPersonalHead() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HeadImageManager him = appContext.getManager(HeadImageManager.class);
				Image headImage = him.getPersonalHeadImage();
				mainFrame.setHeadImage(headImage);
			}
		});
	}

	public MainFrame createFrame() {
		return new MainFrame();
	}

	@Override
	public void setLastUserItemRed(String userId, boolean red, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLastUserChatInfo(String userId, String text, String time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastGroupItemRed(String groupId, boolean red, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLastGroupChatInfo(String groupId, String text, String time) {
		// TODO Auto-generated method stub

	}
}
