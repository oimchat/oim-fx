package com.oim.fx.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.Notifications;

import com.oim.common.util.HttpUrlUtil;
import com.oim.core.business.manager.IconButtonManage;
import com.oim.core.business.sender.NoticeSender;
import com.oim.core.business.view.NoticeView;
import com.oim.ui.fx.classics.NoticeFrame;
import com.oim.ui.fx.classics.WebFrame;
import com.oim.ui.fx.classics.notice.TextNoticeItem;
import com.only.common.result.Info;
import com.only.common.util.OnlyDateUtil;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.message.data.notice.TextNotice;
import com.onlyxiahui.im.message.data.PageData;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * @author XiaHui
 * @date 2017年8月18日 下午5:11:13
 */
public class NoticeViewImpl extends AbstractView implements NoticeView {

	NoticeFrame noticeFrame;
	WebFrame webFrame;

	public NoticeViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				noticeFrame = new NoticeFrame();
				webFrame = new WebFrame();
				initEvent();
			}
		});
	}

	private void initEvent() {
		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Override
			public void lost() {
				// showWaiting(true, WaitingPanel.show_result);
			}

			@Override
			public void timeOut() {
				// showWaiting(true, WaitingPanel.show_result);
			}

			@Back
			public void back(Info info, @Define("list") List<TextNotice> list, @Define("page") PageData page) {
				setList(list, page);
				// showWaiting(false, WaitingPanel.show_waiting);
			}
		};

		initPage();
		noticeFrame.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {

				int pageNumber = (index + 1);
				// UserData sendUser = PersonalDataBox.get(UserData.class);
				PageData page = new PageData();
				page.setPageSize(30);
				page.setPageNumber(pageNumber);
				NoticeSender ns = appContext.getSender(NoticeSender.class);
				ns.getTextNoticeList(page, dataBackAction);
				// frame.showWaiting(true, WaitingPanel.show_waiting);
				return new Label("第" + pageNumber + "页");
			}
		});

	}

	public void setList(List<TextNotice> list, PageData page) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				List<TextNoticeItem> itemList = new ArrayList<TextNoticeItem>();
				if (null != list) {
					for (TextNotice tn : list) {

						String url = tn.getUrl();
						String title = tn.getTitle();
						String content = tn.getContent();
						String createTime = tn.getCreateTime();
						String openType = tn.getOpenType();
						TextNoticeItem tni = new TextNoticeItem();
						tni.setTime(createTime);
						tni.setTitle(title);
						tni.setContent(content);
						if (StringUtils.isNotBlank(url)) {
							tni.setOnOpenAction(a -> {
								if (TextNotice.open_type_app.equals(openType)) {
									if (!webFrame.isShowing()) {
										webFrame.show();
									}
									webFrame.load(url);
								} else {
									HttpUrlUtil.open(url);
								}
							});
						}
						itemList.add(tni);
					}
				}
				int totalPage = page.getTotalPage();
				noticeFrame.setTotalPage((totalPage <= 0 ? 1 : totalPage));
				noticeFrame.setItemList(itemList);
			}
		});
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					if (!isShowing()) {
						initPage();
					}
					IconButtonManage ibm=appContext.getManager(IconButtonManage.class);
					if(ibm.isNewNotice()) {
						ibm.newNotice(false);
					}
					noticeFrame.show();
					noticeFrame.toFront();
				} else {
					noticeFrame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return noticeFrame.isShowing();
	}

	@Override
	public void showPrompt(String text) {
		// TODO Auto-generated method stub

	}

	public void initPage() {
		noticeFrame.setPage(0, 10);
	}

	@Override
	public void addTextNotice(String openType, String url, String title, String content) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (noticeFrame.isShowing()) {
					TextNoticeItem tni = new TextNoticeItem();
					tni.setTime(OnlyDateUtil.getCurrentDateTime());
					tni.setTitle(title);
					tni.setContent(content);
					if (StringUtils.isNotBlank(url)) {
						tni.setOnOpenAction(a -> {
							if (TextNotice.open_type_app.equals(openType)) {
								if (!webFrame.isShowing()) {
									webFrame.show();
								}
								webFrame.load(url);
							} else {
								HttpUrlUtil.open(url);
							}
						});
					}
					noticeFrame.addItem(0, tni);
				}else {
					IconButtonManage ibm=appContext.getManager(IconButtonManage.class);
					if(!ibm.isNewNotice()) {
						ibm.newNotice(true);
					}
				}
				addNotifications(openType, url, title, content);
			}
		});
	}

	private void addNotifications(String openType, String url, String title, String content) {

		if (null != content) {
			if (content.length() > 50) {
				content = content.substring(0, 49) + "......";
			}
		}
		Notifications notificationBuilder = Notifications.create()
				.title(title)
				.text(content)
				.hideAfter(Duration.seconds(31536000))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent a) {
						if (StringUtils.isNotBlank(url)) {
							if (TextNotice.open_type_app.equals(openType)) {
								if (!webFrame.isShowing()) {
									webFrame.show();
								}
								webFrame.load(url);
							} else {
								HttpUrlUtil.open(url);
							}
						}
					}
				});

		notificationBuilder.showInformation();
	}
}
