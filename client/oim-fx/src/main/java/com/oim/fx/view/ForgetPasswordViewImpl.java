package com.oim.fx.view;

import java.util.ArrayList;
import java.util.List;

import com.oim.core.business.box.ServerAddressBox;
import com.oim.core.business.constant.ServerAddressConstant;
import com.oim.core.business.manager.ServerAddressManager;
import com.oim.core.net.http.HttpHandler;
import com.oim.core.net.http.Request;
import com.oim.ui.fx.classics.ForgetPasswordFrame;
import com.oim.ui.fx.classics.forget.AccountPane;
import com.oim.ui.fx.classics.forget.QuestionItem;
import com.oim.ui.fx.classics.forget.QuestionPane;
import com.only.common.result.Info;
import com.only.common.result.util.MessageUtil;
import com.only.common.util.OnlyMD5Util;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.SecurityQuestion;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.address.ServerAddressConfig;

import javafx.application.Platform;

/**
 * @author: XiaHui
 * @date: 2017年4月11日 下午12:07:41
 */
public class ForgetPasswordViewImpl extends AbstractView implements ForgetPasswordView {

	ForgetPasswordFrame frame;// = new ForgetPasswordFrame();
	AccountPane ap;// = new AccountPanel();
	QuestionPane qp;// = new QuestionPanel();
	List<QuestionItem> itemList = new ArrayList<QuestionItem>();
	UserData userData;

	public ForgetPasswordViewImpl(AppContext appContext) {
		super(appContext);
		init();
	}

	public void init() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new ForgetPasswordFrame();
				ap = new AccountPane();
				qp = new QuestionPane();
				initEvent();
			}
		});
	}

	private void initEvent() {

		ap.setOnNextAction(a -> {
			qp.setDoneButtonVisible(false);
			get();
		});

		qp.setOnUpAction(a -> {
			frame.setCenterNode(ap);
		});

		qp.setOnDoneAction(a -> {
			updatePassword();
		});

	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					frame.show();
					setIndex(0);
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

	@Override
	public void setIndex(int index) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame.setCenterNode(ap);
			}
		});
	}

	private void get() {
		boolean verify = ap.verify();
		if (verify) {
			String account = ap.getAccount();

			Request message = new Request();
			message.setController("user");
			message.setMethod("/getSecurityQuestionList.do");

			message.put("account", account);

			DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

				@Override
				public void lost() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							frame.showPrompt("获取账号信息失败！");
						}
					});
				}

				@Back
				public void back(Info info,
						@Define("userData") UserData user,
						@Define("list") List<SecurityQuestion> list) {
					handle(info, user, list);
				}
			};
			Runnable runnable = new Runnable() {
				Request request;
				DataBackAction dataBackAction;

				public Runnable execute(Request request, DataBackAction dataBackAction) {
					this.request = request;
					this.dataBackAction = dataBackAction;
					return this;
				}

				public void run() {
					ServerAddressManager sam = appContext.getManager(ServerAddressManager.class);
					Info backInfo = sam.loadServerAddress("");
					if (backInfo.isSuccess()) {
						ServerAddressBox sab = appContext.getBox(ServerAddressBox.class);
						ServerAddressConfig sac = sab.getAddress(ServerAddressConstant.server_main_http);
						String url = sac.getAddress();
						new HttpHandler().execute(url, request, dataBackAction);
					} else {
						String error = MessageUtil.getDefaultErrorText(backInfo);
						showPrompt(error);
					}
				}
			}.execute(message, dataBackAction);
			new Thread(runnable).start();
		}
	}

	protected void handle(Info info, UserData user, List<SecurityQuestion> list) {
		if (info.isSuccess()) {
			userData = user;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					itemList.clear();
					for (SecurityQuestion sq : list) {
						QuestionItem qi = new QuestionItem();
						qi.setQuestion(sq.getQuestion());
						qi.setQuestionId(sq.getId());
						itemList.add(qi);
					}
					qp.setQuestionItemList(itemList);
					qp.setDoneButtonVisible(true);
					frame.setCenterNode(qp);
				}
			});
		} else {
			String text = MessageUtil.getDefaultErrorText(info);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					frame.showPrompt(text);
					qp.setDoneButtonVisible(false);
				}
			});
		}
	}

	private void updatePassword() {
		boolean verify = qp.verify();
		if (verify) {
			if (null != userData) {
				String password = qp.getPassword();
				Request message = new Request();
				message.setController("user");
				message.setMethod("/updatePassword.do");

				message.put("userId", userData.getId());
				message.put("password", OnlyMD5Util.md5L32(password));
				List<SecurityQuestion> list = new ArrayList<SecurityQuestion>();
				for (QuestionItem qi : itemList) {
					if (!qi.verify()) {
						return;
					} else {
						SecurityQuestion sq = new SecurityQuestion();
						sq.setId(qi.getQuestionId());
						sq.setAnswer(qi.getAnswer());
						list.add(sq);
					}
				}
				message.put("securityQuestionList", list);
				DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

					@Override
					public void lost() {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								frame.showPrompt("修改失败！");
								qp.setDoneButtonVisible(true);
							}
						});
					}

					@Back
					public void back(Info info) {
						handle(info);
					}
				};
				Runnable runnable = new Runnable() {
					Request request;
					DataBackAction dataBackAction;

					public Runnable execute(Request request, DataBackAction dataBackAction) {
						this.request = request;
						this.dataBackAction = dataBackAction;
						return this;
					}

					public void run() {
						ServerAddressBox sab = appContext.getBox(ServerAddressBox.class);
						ServerAddressConfig sac = sab.getAddress(ServerAddressConstant.server_main_http);
						String url = sac.getAddress();
						new HttpHandler().execute(url, request, dataBackAction);
					}
				}.execute(message, dataBackAction);
				new Thread(runnable).start();

			} else {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						frame.showPrompt("获取账号信息失败！");
					}
				});
			}
		}
	}

	protected void handle(Info info) {
		if (info.isSuccess()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					frame.showPrompt("修改成功。");
					qp.setDoneButtonVisible(false);
				}
			});
		} else {
			String text = MessageUtil.getDefaultErrorText(info);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					frame.showPrompt(text);
					qp.setDoneButtonVisible(true);
				}
			});
		}
	}

	public void showPrompt(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame.showPrompt(text);
			}
		});
	}
}
