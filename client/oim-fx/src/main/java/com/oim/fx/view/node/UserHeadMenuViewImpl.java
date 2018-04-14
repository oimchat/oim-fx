package com.oim.fx.view.node;

import com.oim.core.business.sender.UserCategoryMemberSender;
import com.oim.core.business.service.UserService;
import com.oim.core.business.view.UserDataView;
import com.oim.core.common.app.view.UserHeadMenuView;
import com.only.common.result.Info;
import com.only.fx.common.component.OnlyMenuItem;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.UserData;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * @author XiaHui
 * @date 2017年9月4日 下午5:22:57
 */
public class UserHeadMenuViewImpl extends AbstractView implements UserHeadMenuView {

	Alert information = new Alert(AlertType.INFORMATION);
	TextInputDialog textInput = new TextInputDialog("");
	
	private ContextMenu menu = new ContextMenu();
	private OnlyMenuItem showMenuItem = new OnlyMenuItem();
	private OnlyMenuItem updateRemarkMenuItem = new OnlyMenuItem();
	private OnlyMenuItem deleteMenuItem = new OnlyMenuItem();

	UserData userData;

	public UserHeadMenuViewImpl(AppContext appContext) {
		super(appContext);
		initMenu();
		initEvent();
	}

	private void initMenu() {
		
		information.initModality(Modality.APPLICATION_MODAL);
		information.initOwner(menu);
		information.getDialogPane().setHeaderText(null);

		textInput.setTitle("输入备注");
		textInput.setContentText("备注:");
		textInput.getEditor().setText("");
		
		showMenuItem.setText("查看信息");
		updateRemarkMenuItem.setText("修改备注");
		deleteMenuItem.setText("删除好友");

		menu.getItems().add(showMenuItem);
		menu.getItems().add(updateRemarkMenuItem);
		menu.getItems().add(deleteMenuItem);
	}

	private void initEvent() {

		showMenuItem.setOnAction(a -> {
			UserDataView v = appContext.getSingleView(UserDataView.class);
			v.showUserData(userData);
			v.setVisible(true);
		});
		updateRemarkMenuItem.setOnAction(a -> {
			textInput.getEditor().setText(null==userData?"":userData.getRemarkName());
			textInput.showAndWait().ifPresent(response -> {
				if (null == response || response.isEmpty()) {
				} else {
					updateUserRemark(response);
				}
			});
		});
		deleteMenuItem.setOnAction(a -> {
			deleteUser();
		});
	}

	@Override
	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	@Override
	public void show(Window ownerWindow, double anchorX, double anchorY) {
		menu.show(ownerWindow, anchorX, anchorY);
	}
	
	public void showPrompt(String text) {
		information.getDialogPane().setContentText(text);
		information.showAndWait();
	}

	public void clearData() {
		textInput.getEditor().setText("");
	}
	
	public void updateUserRemark(String remark) {
		if (null!=userData&&null != remark && !"".equals(remark)) {
			String userId=userData.getId();
			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Override
				public void lost() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("修改失败！");
						}
					});
				}

				@Override
				public void timeOut() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("修改失败！");
						}
					});
				}

				@Back
				public void back(Info info) {

					if (info.isSuccess()) {
						UserService userService = appContext.getService(UserService.class);
						userService.updateRemarkName(userId, remark);
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								showPrompt("修改失败！");
							}
						});
					}
				}
			};
			
			UserCategoryMemberSender ucms = this.appContext.getSender(UserCategoryMemberSender.class);
			ucms.updateUserCategoryMemberRemark(userId, remark, action);
		}
	}
	
	
	public void deleteUser() {
		if (null!=userData) {
			String userId=userData.getId();
			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Override
				public void lost() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("删除失败！");
						}
					});
				}

				@Override
				public void timeOut() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("删除失败！");
						}
					});
				}

				@Back
				public void back(Info info) {

					if (info.isSuccess()) {
						UserService userService = appContext.getService(UserService.class);
						userService.deleteUser(userId);
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								showPrompt("删除失败！");
							}
						});
					}
				}
			};
			UserCategoryMemberSender ucms = this.appContext.getSender(UserCategoryMemberSender.class);
			ucms.deleteUserCategoryMember(userId, action);
		}
	}
	
	@Override
	public void show(Node anchor, double screenX, double screenY) {
		menu.show(anchor, screenX, screenY);
	}

	@Override
	public void show(Node anchor, Side side, double dx, double dy) {
		menu.show(anchor, side, dx, dy);
	}
}
