package com.oim.fx.view.node;

import com.oim.core.business.sender.GroupCategorySender;
import com.oim.core.business.service.GroupService;
import com.oim.core.business.view.FindView;
import com.oim.core.business.view.GroupEditView;
import com.oim.core.common.app.view.GroupCategoryMenuView;
import com.only.common.result.Info;
import com.only.fx.common.component.OnlyMenuItem;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.GroupCategory;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * @author XiaHui
 * @date 2017年9月4日 下午5:22:57
 */
public class GroupCategoryMenuViewImpl extends AbstractView implements GroupCategoryMenuView {

	Alert information = new Alert(AlertType.INFORMATION);
	TextInputDialog textInput = new TextInputDialog("");

	private ContextMenu menu = new ContextMenu();
	private OnlyMenuItem addCategoryMenuItem = new OnlyMenuItem();
	private OnlyMenuItem findGroupMenuItem = new OnlyMenuItem();
	private OnlyMenuItem updateCategoryNameMenuItem = new OnlyMenuItem();
	
	private OnlyMenuItem addMenuItem = new OnlyMenuItem();
	
	GroupCategory groupCategory;

	public GroupCategoryMenuViewImpl(AppContext appContext) {
		super(appContext);
		initMenu();
		initEvent();
	}

	private void initMenu() {

		//BaseFrame frame=new BaseFrame();
		
		information.initModality(Modality.APPLICATION_MODAL);
		information.initOwner(menu);
		information.getDialogPane().setHeaderText(null);
		//information.initOwner(frame);
		
		//textInput.initOwner(frame);
		textInput.setTitle("输入分组");
		textInput.setContentText("名称:");
		textInput.getEditor().setText("");

		addCategoryMenuItem.setText("新建分组");
		findGroupMenuItem.setText("查找群");
		updateCategoryNameMenuItem.setText("重命名分组");
		addMenuItem.setText("创建一个群");
		
		menu.getItems().add(addCategoryMenuItem);
		menu.getItems().add(findGroupMenuItem);
		menu.getItems().add(updateCategoryNameMenuItem);
		menu.getItems().add(addMenuItem);
	}

	private void initEvent() {

		addCategoryMenuItem.setOnAction(a -> {
			clearData();
			textInput.showAndWait().ifPresent(response -> {
				if (null == response || response.isEmpty()) {
				} else {
					addUserCategory(response);
				}
			});
		});

		findGroupMenuItem.setOnAction(a -> {
			FindView findView = appContext.getSingleView(FindView.class);
			findView.selectedTab(1);
			findView.setVisible(true);
		});

		updateCategoryNameMenuItem.setOnAction(a -> {
			textInput.getEditor().setText(null==groupCategory?"":groupCategory.getName());
			textInput.showAndWait().ifPresent(response -> {
				if (null == response || response.isEmpty()) {
				} else {
					updateUserCategory(response);
				}
			});
		});
		addMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				GroupEditView view = appContext.getSingleView(GroupEditView.class);
				view.setGroup(null);
				view.setVisible(true);
			}
		});
	}

	@Override
	public void show(Window ownerWindow, double anchorX, double anchorY) {
		menu.show(ownerWindow, anchorX, anchorY);
	}
	
	@Override
	public void setGroupCategory(GroupCategory groupCategory) {
		this.groupCategory=groupCategory;
	}

	public void showPrompt(String text) {
		information.getDialogPane().setContentText(text);
		information.showAndWait();
	}

	public void clearData() {
		textInput.getEditor().setText("");
	}

	public void addUserCategory(String name) {
		if (null != name && !"".equals(name)) {

			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Override
				public void lost() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("添加失败！");
						}
					});
				}

				@Override
				public void timeOut() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("添加失败！");
						}
					});
				}

				@Back
				public void back(Info info, @Define("groupCategory") GroupCategory groupCategory) {
					if (info.isSuccess()) {
						GroupService groupService = appContext.getService(GroupService.class);
						groupService.addGroupCategory(groupCategory);
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								showPrompt("添加失败！");
							}
						});
					}
				}
			};

			GroupCategorySender gcs = this.appContext.getSender(GroupCategorySender.class);
			gcs.addGroupCategory(name, action);
		}
	}
	
	
	public void updateUserCategory(String name) {
		if (null!=groupCategory&&null != name && !"".equals(name)) {

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
				public void back(Info info, @Define("groupCategory") GroupCategory groupCategory) {

					if (info.isSuccess()) {
						GroupService groupService = appContext.getService(GroupService.class);
						groupService.updateGroupCategory(groupCategory);
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
			
			String groupCategoryId=groupCategory.getId();

			GroupCategorySender gcs = this.appContext.getSender(GroupCategorySender.class);
			gcs.updateGroupCategoryName(groupCategoryId, name, action);
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
