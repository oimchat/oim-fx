package com.oim.fx.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.common.chat.util.WebImagePathUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.ImageManager;
import com.oim.core.business.sender.UserChatSender;
import com.oim.core.business.service.ContentToHtmlService;
import com.oim.core.business.view.UserChatHistoryView;
import com.oim.core.common.AppConstant;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.component.file.FileHandleInfo;
import com.oim.core.common.component.file.FileInfo;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.fx.common.component.WaitingPane;
import com.oim.ui.fx.classics.ChatHistoryFrame;
import com.oim.ui.fx.common.chat.ChatShowPane;
import com.only.common.result.Info;
import com.only.common.util.OnlyDateUtil;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;
import com.onlyxiahui.im.message.data.query.ChatQuery;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Callback;

/**
 * @author: XiaHui
 * @date: 2017年4月11日 下午12:07:41
 */
public class UserChatHistoryViewImpl extends AbstractView implements UserChatHistoryView {

	ChatHistoryFrame frame;// = new ChatHistoryFrame();
	String ownerUserId;
	String userId;

	public UserChatHistoryViewImpl(AppContext appContext) {
		super(appContext);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new ChatHistoryFrame();
				initEvent();
			}
		});
	}

	private void initEvent() {

		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Override
			public void lost() {
				showWaiting(true, WaitingPane.show_result);
			}

			@Override
			public void timeOut() {
				showWaiting(true, WaitingPane.show_result);
			}

			@Back
			public void back(Info info, @Define("sendUserId") String sendUserId, @Define("receiveUserId") String receiveUserId, @Define("contents") List<UserChatHistoryData> contents, @Define("page") PageData page) {
				setList(contents, page);
				showWaiting(false, WaitingPane.show_waiting);
			}
		};

		initPage();
		frame.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {

				int pageNumber = (index + 1);

				String sendUserId = (userId == null || "".equals(userId)) ? "00000" : userId;
				String receiveUserId = ownerUserId;

				PageData page = new PageData();
				page.setPageSize(30);
				page.setPageNumber(pageNumber);
				UserChatSender cs = appContext.getSender(UserChatSender.class);
				cs.queryUserChatLog(sendUserId, receiveUserId, new ChatQuery(), page, dataBackAction);
				frame.showWaiting(true, WaitingPane.show_waiting);
				return new Label("第" + pageNumber + "页");
			}
		});
	}

	public void setList(List<UserChatHistoryData> contents, PageData page) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (null != contents) {
			ContentToHtmlService cths = appContext.getService(ContentToHtmlService.class);
			int size = contents.size();

			for (int i = size - 1; i >= 0; i--) {
				UserChatHistoryData chd = contents.get(i);

				UserData sendUserData = chd.getSendUserData();
				String chatUserId = sendUserData.getId();
				Content content = chd.getContent();

				PersonalBox pb = appContext.getBox(PersonalBox.class);
				UserData user = pb.getUserData();
				boolean isOwn = user.getId().equals(chatUserId);

				HeadImageManager him = appContext.getManager(HeadImageManager.class);
				String head = him.getUserHeadPath(sendUserData.getId());
				String headPath = WebImagePathUtil.pathToFileImageSource(head);
				String time = OnlyDateUtil.dateToDateTime(new Date(content.getTimestamp()));
				String name = isOwn ? "我" : AppCommonUtil.getDefaultShowName(sendUserData);
				String orientation = isOwn ? "right" : "left";
				String contentTag = cths.getBubbleInsertContentTag(content);

				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				map.put("head", headPath);
				map.put("time", time);
				map.put("orientation", orientation);
				map.put("contentTag", contentTag);
				list.add(map);
			}
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				int totalPage = page.getTotalPage();
				frame.setTotalPage((totalPage <= 0 ? 1 : totalPage));
				ChatShowPane sp = frame.getShowPane();
				sp.setBodyHtml("");
				for (Map<String, String> map : list) {
					String name = map.get("name");
					String head = map.get("head");
					String time = map.get("time");
					String orientation = map.get("orientation");
					String contentTag = map.get("contentTag");
					sp.insertBeforeShowHtml(name, head, time, orientation, contentTag);
				}
			}
		});

		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

			@Override
			public void back(BackInfo backInfo, List<FileHandleInfo> list) {
				for (FileHandleInfo fd : list) {
					FileInfo fi = fd.getFileInfo();
					if (fd.isSuccess()) {
						File file = (null != fi) ? fi.getFile() : null;
						String name = (null != file) ? file.getName() : "";
						String id = (null != fi) ? fi.getId() : "";
						String appPath = AppConstant.userHome + "/" + AppConstant.app_home_path + "/" + user.getNumber() + "/image/" + name;

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								String path = "";

								if (appPath.startsWith("/")) {
									path = "file:" + appPath;
								} else {
									path = "file:/" + appPath;
								}
								frame.replaceImage(id, path);
							}
						});
					} else {
						String id = (null != fi) ? fi.getId() : "";
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								String tempImage = "Resources/Images/Default/Chat/ImageLoading/image_download_fail.png";
								File file = new File(tempImage);
								if (file.exists()) {
									tempImage = file.getAbsolutePath();
								}
								frame.replaceImage(id, tempImage);
							}
						});
					}
				}
			}
		};
		if (null != contents) {
			for (UserChatHistoryData chd : contents) {
				Content content = chd.getContent();
				ImageManager im = this.appContext.getManager(ImageManager.class);
				List<Item> items = CoreContentUtil.getImageItemList(content);
				im.downloadImage(items, backAction);
			}
		}
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!isShowing()) {
					initPage();
				}
				if (visible) {
					frame.show();
					frame.toFront();
				} else {
					frame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return frame.isShowing();
	}

	public void showWaiting(boolean show, String key) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame.showWaiting(show, key);
			}
		});
	}

	public void initPage() {
		frame.setPage(0, 10);
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
