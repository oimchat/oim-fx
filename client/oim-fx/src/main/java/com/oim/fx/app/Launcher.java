package com.oim.fx.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oim.core.business.module.LoginStartupModule;
import com.oim.core.business.module.NetModule;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.view.AddUserView;
import com.oim.core.business.view.ChatListView;
import com.oim.core.business.view.CreateGroupView;
import com.oim.core.business.view.FileDownloadView;
import com.oim.core.business.view.FileUploadView;
import com.oim.core.business.view.FindView;
import com.oim.core.business.view.GroupChatHistoryView;
import com.oim.core.business.view.GroupDataView;
import com.oim.core.business.view.GroupEditView;
import com.oim.core.business.view.JoinGroupView;
import com.oim.core.business.view.LoginView;
import com.oim.core.business.view.MainView;
import com.oim.core.business.view.NetSettingView;
import com.oim.core.business.view.NoticeView;
import com.oim.core.business.view.RegisterView;
import com.oim.core.business.view.RemoteView;
import com.oim.core.business.view.SettingView;
import com.oim.core.business.view.ThemeView;
import com.oim.core.business.view.TrayView;
import com.oim.core.business.view.UpdatePasswordView;
import com.oim.core.business.view.UserChatHistoryView;
import com.oim.core.business.view.UserDataEditView;
import com.oim.core.business.view.UserDataView;
import com.oim.core.business.view.VideoView;
import com.oim.core.common.action.CallAction;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.config.data.Theme;
import com.oim.core.common.config.global.DefaultConfigBox;
import com.oim.core.common.function.ChatInterfaceImpl;
import com.oim.core.js.ChatInterface;
import com.oim.fx.ui.util.UIBox;
import com.oim.fx.view.AddUserViewImpl;
import com.oim.fx.v1.view.ChatListViewImpl;
import com.oim.fx.view.CreateGroupViewImpl;
import com.oim.fx.view.FileDownloadViewImpl;
import com.oim.fx.view.FileUploadViewImpl;
import com.oim.fx.view.FindViewImpl;
import com.oim.fx.view.ForgetPasswordView;
import com.oim.fx.view.ForgetPasswordViewImpl;
import com.oim.fx.view.GroupChatHistoryViewImpl;
import com.oim.fx.view.GroupDataViewImpl;
import com.oim.fx.view.GroupEditViewImpl;
import com.oim.fx.view.JoinGroupViewImpl;
import com.oim.fx.v1.view.LoginViewImpl;
import com.oim.fx.v1.view.MainViewImpl;
import com.oim.fx.v1.view.SettingViewImpl;
import com.oim.fx.view.NoticeViewImpl;
import com.oim.fx.view.RegisterViewImpl;
import com.oim.fx.view.ThemeViewImpl;
import com.oim.fx.view.UpdatePasswordViewImpl;
import com.oim.fx.view.UserChatHistoryViewImpl;
import com.oim.fx.view.UserDataEditViewImpl;
import com.oim.fx.view.UserDataViewImpl;
import com.oim.fx.view.UserHeadEditView;
import com.oim.fx.view.UserHeadEditViewImpl;
import com.oim.fx.view.context.ChatListItemContext;
import com.oim.fx.view.context.ChatListPaneContext;
import com.oim.fx.view.function.ChatFunction;
import com.oim.fx.view.function.GroupCategoryNodeFunction;
import com.oim.fx.view.function.GroupHeadFunction;
import com.oim.fx.view.function.GroupLastFunction;
import com.oim.fx.view.function.UserCategoryNodeFunction;
import com.oim.fx.view.function.UserHeadFunction;
import com.oim.fx.view.function.UserLastFunction;
import com.oim.fx.view.NetSettingViewImpl;
import com.oim.swing.view.RemoteViewImpl;
import com.oim.swing.view.TrayViewImpl;
import com.oim.swing.view.VideoViewImpl;
import com.only.common.util.OnlyPropertiesUtil;
import com.onlyxiahui.app.base.AppContext;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

/**
 * @author: XiaHui
 * @date: 2017年9月7日 下午8:09:04
 */
public class Launcher {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected AppContext appContext = new AppContext();

	public Launcher() {
		initBase();
		initTheme();
		registerView();
		initPartView();
		showStartView();
		startThread();
		initView();
		logger.debug("startup");
	}

	protected void initBase() {
		NetModule nm = appContext.getModule(NetModule.class);
		SystemModule sm = appContext.getModule(SystemModule.class);
		nm.start();
		sm.start();
		// DAOFactory df = appContext.getModule(DAOFactory.class);
		// df.initialize();
		String address=OnlyPropertiesUtil.getProperty("Resources/Config/server.properties", "server.http");
		DefaultConfigBox.address=address;
		appContext.register(ChatInterface.class, ChatInterfaceImpl.class);
	}

	protected void initTheme() {
		Theme theme = (Theme) ConfigManage.get(Theme.config_file_path, Theme.class);
		String backgroundImageUrl = theme.getBackgroundImage();
		Color color = Color.color(theme.getRed(), theme.getGreen(), theme.getBlue(), theme.getOpacity());

		UIBox.setBackgroundImageUrl(backgroundImageUrl);
		UIBox.setColor(color);
	}

	protected void registerView() {
		appContext.register(LoginView.class, LoginViewImpl.class);
		appContext.register(ChatListView.class, ChatListViewImpl.class);
		appContext.register(NetSettingView.class, NetSettingViewImpl.class);
		appContext.register(RegisterView.class, RegisterViewImpl.class);
		appContext.register(ForgetPasswordView.class, ForgetPasswordViewImpl.class);
		appContext.register(UserHeadEditView.class, UserHeadEditViewImpl.class);
		appContext.register(TrayView.class, TrayViewImpl.class);
		appContext.register(JoinGroupView.class, JoinGroupViewImpl.class);
		appContext.register(AddUserView.class, AddUserViewImpl.class);
		appContext.register(FindView.class, FindViewImpl.class);
		appContext.register(MainView.class, MainViewImpl.class);
		appContext.register(SettingView.class, SettingViewImpl.class);
		appContext.register(ThemeView.class, ThemeViewImpl.class);
		appContext.register(UpdatePasswordView.class, UpdatePasswordViewImpl.class);
		appContext.register(CreateGroupView.class, CreateGroupViewImpl.class);
		appContext.register(GroupDataView.class, GroupDataViewImpl.class);
		appContext.register(GroupEditView.class, GroupEditViewImpl.class);
		appContext.register(UserDataView.class, UserDataViewImpl.class);
		appContext.register(UserDataEditView.class, UserDataEditViewImpl.class);
		appContext.register(VideoView.class, VideoViewImpl.class);
		appContext.register(FileUploadView.class, FileUploadViewImpl.class);
		appContext.register(FileDownloadView.class, FileDownloadViewImpl.class);
		appContext.register(RemoteView.class, RemoteViewImpl.class);
		appContext.register(UserChatHistoryView.class, UserChatHistoryViewImpl.class);
		appContext.register(GroupChatHistoryView.class, GroupChatHistoryViewImpl.class);
		appContext.register(NoticeView.class, NoticeViewImpl.class);
	}

	protected void initPartView() {
		ChatListViewImpl chatListViewImpl = new ChatListViewImpl(appContext);
		appContext.put(ChatListViewImpl.class, chatListViewImpl);

		appContext.getObject(ChatListItemContext.class);
		appContext.getObject(ChatListPaneContext.class);
		
		appContext.getObject(UserCategoryNodeFunction.class);
		appContext.getObject(UserHeadFunction.class);

		appContext.getObject(GroupCategoryNodeFunction.class);
		appContext.getObject(GroupHeadFunction.class);

		appContext.getObject(UserLastFunction.class);
		appContext.getObject(GroupLastFunction.class);

		appContext.getObject(ChatFunction.class);
	}

	protected void showStartView() {
		LoginView loginView = appContext.getSingleView(LoginView.class);
		loginView.setVisible(true);
	}

	protected void startThread() {
		CallAction exitAction = new CallAction() {
			@Override
			public void execute() {
				Platform.exit();
				System.exit(0);
			}
		};
		NetModule nm = appContext.getModule(NetModule.class);
		SystemModule sm = appContext.getModule(SystemModule.class);
		nm.getConnectThread().setAutoConnectCount(10);// 设置自动重试次数
		sm.setExitAction(exitAction);
	}

	protected void initView() {

		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				int iterations = 0;

				appContext.getSingleView(TrayView.class);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						appContext.getSingleView(MainView.class);
						// appContext.getSingleView(ChatListView.class);
						// appContext.getSingleView(RegisterView.class);
						SystemModule sm = appContext.getModule(SystemModule.class);
						sm.setViewReady(true);

						LoginStartupModule lsm = appContext.getModule(LoginStartupModule.class);
						lsm.startupLoginConfig();
					}
				});
				return iterations;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
}
