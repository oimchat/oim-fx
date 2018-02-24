package com.oim.fx.view.function;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import com.oim.common.chat.util.HtmlContentUtil;
import com.oim.common.chat.util.WebImagePathUtil;
import com.oim.core.business.box.FaceBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.controller.RemoteController;
import com.oim.core.business.controller.UserChatController;
import com.oim.core.business.manager.FaceManager;
import com.oim.core.business.manager.PathManager;
import com.oim.core.business.manager.SettingManager;
import com.oim.core.business.service.GroupChatService;
import com.oim.core.business.service.UserChatService;
import com.oim.core.business.view.ChatListView;
import com.oim.core.js.ChatInterface;
import com.oim.fx.common.DataConverter;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.common.component.ScreenShotFrame;
import com.oim.ui.fx.classics.chat.ChatPane;
import com.oim.ui.fx.classics.chat.FacePopup;
import com.oim.ui.fx.common.chat.ChatWritePane;
import com.only.common.util.OnlyFileUtil;
import com.only.common.util.OnlyStringUtil;
import com.only.fx.common.action.EventAction;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.oim.face.bean.FaceCategory;
import com.onlyxiahui.oim.face.bean.FaceInfo;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * @author XiaHui
 * @date 2017-12-22 11:16:27
 */
public class ChatFunction extends AbstractComponent {

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
	protected ScreenShotFrame frame = new ScreenShotFrame();
	protected FacePopup facePopup = new FacePopup();
	protected FileChooser imageFileChooser;
	protected FileChooser fileChooser;

	EventAction<FaceInfo> faceSelectAction;
	protected DataConverter<String, String> emojiConverter;

	public ChatFunction(AppContext appContext) {
		super(appContext);
		initStore();
		initEvent();
		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				int iterations = 0;
				initFace();
				return iterations;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private void initEvent() {
		emojiConverter = new DataConverter<String, String>() {

			@Override
			public String converter(String data) {
				FaceManager fm = appContext.getManager(FaceManager.class);
				String text = fm.toEmojiImage(data);
				return text;
			}
		};
	}

	private void initStore() {
		imageFileChooser = new FileChooser();
		imageFileChooser.getExtensionFilters().add(new ExtensionFilter("图片文件", "*.png", "*.jpg", "*.bmp", "*.gif"));
		fileChooser = new FileChooser();
	}

	private void initFace() {
		FaceBox fb = appContext.getBox(FaceBox.class);
		List<FaceCategory> list = fb.getAllFaceCategoryList();
		for (FaceCategory fc : list) {
			if ("classical".equals(fc.getId())) {
				set(fc.getId(), fc.getName(), fc.getFaceInfoList());
			}
		}
	}

	private void set(String key, String name, List<FaceInfo> list) {
		List<IconButton> faceList = new ArrayList<IconButton>();

		if (null != list && !list.isEmpty()) {
			for (FaceInfo data : list) {
				// String faceKey = data.getKey();
				String normalPath = data.getShowPath();
				String hoverPath = data.getRealPath();
				String text = data.getText();
				double width = data.getWidth();
				double height = data.getHeight();

				double imageWidth = data.getImageWidth();
				double imageHeight = data.getImageHeight();

				Image normalImage = ImageBox.getImageClassPath(normalPath);
				Image hoverImage = ImageBox.getImageClassPath(hoverPath);
				Tooltip tooltip = new Tooltip(text);

				IconButton button = new IconButton(normalImage, hoverImage, null);
				button.setTooltip(tooltip);

				if (width > 0 && height > 0) {
					button.setPrefSize(width, height);
					button.setMinSize(width, height);
				}

				if (imageWidth > 0 && imageHeight > 0) {
					button.setImageSize(imageWidth, imageHeight);
				}
				//

				button.setOnAction(a -> {
					facePopup.hide();
					if (null != faceSelectAction) {
						faceSelectAction.execute(data);
					}
				});
				faceList.add(button);
			}
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				facePopup.set(key, name, faceList);
			}
		});
	}

	public void showFacePopup(Node owner, EventAction<FaceInfo> action) {
		faceSelectAction = (action);
		facePopup.show(owner);
	}

	public String getPicture(Node node) {
		Scene scene = node.getScene();
		Window w = (null == scene) ? null : scene.getWindow();
		String fullPath = null;
		if (null != w) {
			File file = imageFileChooser.showOpenDialog(w);
			if (file != null) {
				if (file.exists()) {
					fullPath = file.getAbsolutePath();
				}
			}
		}
		return fullPath;
	}

	public void showScreenShot(EventAction<Image> screenShotAction) {
		frame.setOnImageAction(screenShotAction);
		frame.setVisible(true);
	}

	public void initChatPane(ChatPane chatPane) {
		ChatInterface chatInterface = appContext.getObject(ChatInterface.class);
		chatPane.getChatWritePane().setEmojiConverter(emojiConverter);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatPane.getChatShowPane().setJavaScriptInterface(chatInterface);
			}
		});

		EventAction<FaceInfo> faceAction = new EventAction<FaceInfo>() {

			ChatPane chatPane;

			@Override
			public void execute(FaceInfo value) {
				if (null != value) {
					insertFace(chatPane, value);
				}
			}

			EventAction<FaceInfo> set(ChatPane chatPane) {
				this.chatPane = chatPane;
				return this;
			}
		}.set(chatPane);

		EventHandler<MouseEvent> p = new EventHandler<MouseEvent>() {
			ChatPane chatPane;

			@Override
			public void handle(MouseEvent event) {
				String fullPath = getPicture(chatPane);
				if (OnlyStringUtil.isNotBlank(fullPath)) {
					insertWriteImage(chatPane, fullPath);
				}
			}

			EventHandler<MouseEvent> set(ChatPane chatPane) {
				this.chatPane = chatPane;
				return this;
			}
		}.set(chatPane);

		EventAction<Image> screenShotAction = new EventAction<Image>() {
			ChatPane chatPane;

			@Override
			public void execute(Image image) {
				if (null != image) {
					saveWriteImage(chatPane, image);
				}
			}

			EventAction<Image> set(ChatPane chatPane) {
				this.chatPane = chatPane;
				return this;
			}
		}.set(chatPane);

		// 表情按钮
		Image normalImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_face.png");
		// Image hoverImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_face_hover.png");
		// Image pressedImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_face_selected.png");
		IconPane faceIcon = new IconPane(normalImage);

		// 发送图片按钮
		normalImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_sendpic.png");
		// hoverImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_sendpic_hover.png");
		// pressedImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_sendpic_selected.png");
		IconPane imageIcon = new IconPane(normalImage);

		// 截屏按钮
		normalImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_cut.png");
		// hoverImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_cut_hover.png");
		// pressedImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_cut_selected.png");
		IconPane cutIcon = new IconPane(normalImage);

		faceIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				showFacePopup(faceIcon, faceAction);
			}
		});
		imageIcon.setOnMouseClicked(p);
		cutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				showScreenShot(screenShotAction);
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatPane.addMiddleTool(faceIcon);
				chatPane.addMiddleTool(imageIcon);
				chatPane.addMiddleTool(cutIcon);
			}
		});
	}

	public void initUserChatPane(ChatPane chatPane) {

		Image normalImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_twitter.png");
		// Image hoverImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_twitter_hover.png");
		// Image pressedImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_twitter_selected.png");
		IconPane shakeIcon = new IconPane(normalImage);

		shakeIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String userId = chatPane.getAttribute("userId");
				UserChatController ucc = appContext.getController(UserChatController.class);
				ucc.sendShake(userId);
			}
		});

		normalImage = ImageBox.getImageClassPath("/oim/classics/images/chat/user/video/aio_toobar_video.png");

		IconPane videoButton = new IconPane(normalImage);
		videoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				UserData userData = chatPane.getAttribute(UserData.class);
				UserChatController ucc = appContext.getController(UserChatController.class);
				ucc.requestVideo(userData);
			}
		});

		normalImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_register.png");
		// hoverImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_register_hover.png");
		// pressedImage =
		// ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_register_hover.png");
		IconPane historyButton = new IconPane(normalImage);
		historyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String userId = chatPane.getAttribute("userId");
				UserChatService cs = appContext.getService(UserChatService.class);
				cs.showUserHistory(userId);
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatPane.addMiddleTool(shakeIcon);
				chatPane.addMiddleTool(videoButton);
			}
		});
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatPane.addMiddleTool(historyButton);
			}
		});
	}

	public void initTopUserChatPane(ChatPane chatPane) {
		ChatListView clv = appContext.getSingleView(ChatListView.class);

		chatPane.setOnFileEventAction(files -> {
			if (null != files && !files.isEmpty()) {
				String receiveUserId = chatPane.getAttribute("userId");
				File file = files.get(0);
				UserDataBox ub = appContext.getBox(UserDataBox.class);
				boolean online = ub.isOnline(receiveUserId);
				if (online) {
					SettingManager sm = appContext.getManager(SettingManager.class);
					if (file.length() > sm.getChatSetting().getFileLimitSize()) {
						clv.showPrompt(sm.getChatSetting().getFileLimitInfo());
						return;
					}
					UserChatController cc = appContext.getController(UserChatController.class);
					cc.sendFile(receiveUserId, file);
				} else {
					clv.showPrompt("对方不在线！");
				}
			}
		});

		Image i = ImageBox.getImageClassPath("/oim/classics/images/chat/user/file/aio_toobar_send_normal.png");

		IconButton fileButton = new IconButton();
		fileButton.setNormalImage(i);

		fileButton.setOnAction(a -> {
			String receiveUserId = chatPane.getAttribute("userId");
			UserDataBox ub = appContext.getBox(UserDataBox.class);
			boolean online = ub.isOnline(receiveUserId);
			if (online) {
				Scene scene = chatPane.getScene();
				Window w = (null == scene) ? null : scene.getWindow();
				File file = fileChooser.showOpenDialog(w);
				if (null != file) {
					SettingManager sm = appContext.getManager(SettingManager.class);
					if (file.length() > sm.getChatSetting().getFileLimitSize()) {
						clv.showPrompt(sm.getChatSetting().getFileLimitInfo());
						return;
					}
					UserChatController cc = appContext.getController(UserChatController.class);
					cc.sendFile(receiveUserId, file);
				}
			} else {
				clv.showPrompt("对方不在线！");
			}
		});

		i = ImageBox.getImageClassPath("/oim/classics/images/chat/user/remote/Remote_MenuTextue.png");

		IconButton remoteIcon = new IconButton();
		remoteIcon.setNormalImage(i);
		remoteIcon.setOnAction(a -> {
			String receiveUserId = chatPane.getAttribute("userId");
			UserDataBox ub = appContext.getBox(UserDataBox.class);
			boolean online = ub.isOnline(receiveUserId);
			if (online) {
				RemoteController rc = appContext.getController(RemoteController.class);
				rc.requestRemoteControl(receiveUserId);
			} else {
				clv.showPrompt("对方不在线！");
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatPane.addTopTool(fileButton);
				chatPane.addTopTool(remoteIcon);
			}
		});
	}

	public void insertFace(ChatPane cp, FaceInfo fi) {
		String tag = getFaceImageTag(fi);
		if (null != tag) {
			cp.getChatWritePane().insertSelectionHtml(tag);
		}
	}

	public String getFaceImageTag(FaceInfo fi) {
		String categoryId = fi.getCategoryId();
		String key = fi.getKey();
		String value = categoryId + "," + key;
		String source = fi.getRealPath();
		String alt = fi.getText();
		String tag = HtmlContentUtil.getImageTag("", "face", value, source, alt);
		return tag;
	}

	public void saveWriteImage(ChatPane cp, Image image) {
		String fileName = dateFormat.format(new Date()).toString() + ".png";
		PathManager pm = appContext.getManager(PathManager.class);
		String savePath = pm.getScreenshotSavePath();
		String fullPath = savePath + fileName;
		try {
			OnlyFileUtil.checkOrCreateFolder(fullPath);
			File file = new File(fullPath);
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			String path = WebImagePathUtil.pathToFileImageSource(fullPath);
			ChatWritePane cwp = cp.getChatWritePane();
			String tag = HtmlContentUtil.getImageTag("", "image", path, path, "");
			cwp.insertSelectionHtml(tag);
		} catch (IOException ex) {
		}
	}

	public void insertWriteImage(ChatPane cp, String fullPath) {
		ChatWritePane cwp = cp.getChatWritePane();
		String path = WebImagePathUtil.pathToFileImageSource(fullPath);
		String tag = HtmlContentUtil.getImageTag("", "image", fullPath, path, "");
		cwp.insertSelectionHtml(tag);
	}

	public void initGroupChatPane(ChatPane chatPane) {
		Image normalImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_register.png");
		//Image hoverImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_register_hover.png");
		//Image pressedImage = ImageBox.getImageClassPath("/oim/classics/images/chat/tools/aio_quickbar_register_hover.png");

		IconPane historyButton = new IconPane(normalImage);
		historyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String groupId = chatPane.getAttribute("groupId");
				GroupChatService cs = appContext.getService(GroupChatService.class);
				cs.showGroupHistory(groupId);
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatPane.addMiddleTool(historyButton);
			}
		});
	}
}
