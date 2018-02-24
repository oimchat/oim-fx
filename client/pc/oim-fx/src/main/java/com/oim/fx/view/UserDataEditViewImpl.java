package com.oim.fx.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.oim.core.business.box.HeadBox;
import com.oim.core.business.box.PathBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.ImageManager;
import com.oim.core.business.sender.PersonalSender;
import com.oim.core.business.sender.UserSender;
import com.oim.core.business.view.MainView;
import com.oim.core.business.view.UserDataEditView;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.component.file.FileHandleInfo;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.data.UserSaveData;
import com.oim.core.common.data.UserSaveDataBox;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.WaitingPane;
import com.oim.fx.ui.list.HeadImagePanel;
import com.oim.ui.fx.classics.HeadChooseFrame;
import com.oim.ui.fx.classics.UserEditFrame;
import com.oim.ui.fx.classics.UserHeadUploadFrame;
import com.oim.ui.fx.classics.head.HeadListPane;
import com.only.common.result.Info;
import com.only.common.util.OnlyFileUtil;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class UserDataEditViewImpl extends AbstractView implements UserDataEditView {

	UserEditFrame frame;
	HeadChooseFrame hcf;
	UserHeadUploadFrame uhuf;

	public UserDataEditViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new UserEditFrame();
				hcf = new HeadChooseFrame();
				uhuf = new UserHeadUploadFrame();

				frame.setTitleText("个人资料修改");
				initEvent();
			}
		});
	}

	protected void initEvent() {
		frame.setOnDoneAction(a -> {
			update();
		});

		uhuf.setDoneAction(a -> {
			save();
		});

		uhuf.setSelectAction(a -> {
			hcf.show();
			hcf.toFront();
		});

		frame.setOnHeadMouseClicked(m -> {
			uhuf.show();
			uhuf.toFront();
		});

		HeadListPane p = hcf.getHeadListPanel();
		p.setPrefWrapLength(400);
		appContext.add(new ExecuteTask() {

			@Override
			public void execute() {
				init(p);
			}
		});

		// p.showWaiting(true, WaitingPane.show_waiting);
		//
		// List<HeadImagePanel> list = new ArrayList<HeadImagePanel>();
		//
		// HeadImageManager him = appContext.getManager(HeadImageManager.class);
		// List<HeadData> pathList = him.getUserHeadPathList();
		//
		// for (HeadData hi : pathList) {
		// String key = hi.getKey();
		// String path = hi.getPath();
		// Image image = ImageBox.getImagePath(path);
		// HeadImagePanel hp = new HeadImagePanel();
		// hp.setHeadSize(60);
		// hp.setHeadRadius(60);
		// hp.setImage(image);
		// hp.setOnMouseClicked(m -> {
		// select(image, key);
		// });
		// list.add(hp);
		//
		// }
		// p.showWaiting(false, WaitingPane.show_waiting);
		// p.setNodeList(list);
	}

	private void select(Image image, String value) {
		hcf.select(b -> {
			hcf.hide();

			PersonalBox pb = appContext.getBox(PersonalBox.class);
			UserData user = pb.getUserData();

			UserHead uh = new UserHead();
			uh.setHeadId(value);
			uh.setType(UserHead.type_system);
			uh.setUserId(user.getId());

			saveHead(uh, "", image);
		});
	}

	private void save() {
		boolean v = uhuf.verify();
		if (v) {
			uhuf.hide();
			Image image = uhuf.getImage();
			save(image);
		}
	}

	public void save(Image image) {
		try {

			PathBox pb = appContext.getBox(PathBox.class);
			String path = pb.getPersonalHeadPath();
			String filePath = path + "temp.png";
			OnlyFileUtil.checkOrCreateFolder(filePath);
			File file = new File(filePath);
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			ImageManager im = appContext.getManager(ImageManager.class);

			BackAction<FileHandleInfo> backAction = new BackAction<FileHandleInfo>() {
				@Override
				public void back(BackInfo backInfo, FileHandleInfo info) {
					if (backInfo.isSuccess()) {
						String id = info.getFileInfo().getId();
						String newFilePath = path + id + ".png";
						File newFile = new File(newFilePath);
						try {
							FileUtils.copyFile(file, newFile);
						} catch (IOException e) {
							e.printStackTrace();
						}

						PersonalBox pb = appContext.getBox(PersonalBox.class);
						UserData user = pb.getUserData();

						UserHead uh = new UserHead();
						uh.setHeadId(id);
						uh.setType(UserHead.type_custom);
						uh.setUserId(user.getId());
						uh.setFileName(newFile.getName());

						String headPath = newFile.getAbsolutePath();

						saveHead(uh, headPath, image);
					} else {
						showPrompt("上传失败！");
					}
				}
			};
			im.uploadUserHeadImage(file, backAction);
		} catch (IOException ex) {
			showPrompt("上传失败！");
		}
	}

	private void saveHead(UserHead userHead, String headPath, Image image) {
		final HeadBox headBox = this.appContext.getBox(HeadBox.class);
		DataBackActionAdapter action = new DataBackActionAdapter() {

			@Back
			public void back(Info info, @Define("userHead") UserHead uh) {
				if (info.isSuccess()) {
					headBox.putUserHeadPath(uh.getUserId(), headPath);
					headBox.putUserHead(userHead);

					frame.setHeadImage(image);
					MainView mainView = appContext.getSingleView(MainView.class);
					mainView.refreshPersonalHead();

					PersonalBox pb = appContext.getBox(PersonalBox.class);
					UserData ud = pb.getUserData();

					UserSaveDataBox usdb = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);

					UserSaveData usd = usdb.get(ud.getAccount());
					if (usd == null) {
						usd = new UserSaveData();
						usd.setAccount(ud.getAccount());
					}

					if (UserHead.type_system.equals(uh.getType())) {
						usd.setHead(uh.getHeadId());
					}

					usd.setHeadPath(headPath);
					usdb.put(ud.getAccount(), usd);

					ConfigManage.addOrUpdate(UserSaveDataBox.path, usdb);

					UserSender us = appContext.getSender(UserSender.class);
					us.sendUpdate();
				} else {
					showPrompt("修改失败！");
				}
			}
		};
		PersonalSender ps = this.appContext.getSender(PersonalSender.class);
		ps.uploadHead(userHead, action);
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					frame.show();
					frame.toFront();
					setUserData(frame);
				} else {
					frame.hide();
				}
			}
		});
	}

	private void setUserData(UserEditFrame frame) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image headImage = him.getPersonalHeadImage();

		String nickname = user.getNickname();
		String gender = user.getGender();
		String blood = user.getBlood();
		Date birthdate = user.getBirthdate();
		String homeAddress = user.getHomeAddress();
		String locationAddress = user.getLocationAddress();
		String mobile = user.getMobile();
		String email = user.getEmail();
		String signature = user.getSignature();
		String introduce = user.getIntroduce();

		frame.setHeadImage(headImage);
		frame.setNickname(nickname);
		frame.setGender(gender);
		frame.setBloodType(blood);
		frame.setBirthday(birthdate);
		frame.setHomeAddress(homeAddress);
		frame.setLocationAddress(locationAddress);
		frame.setMobile(mobile);
		frame.setEmail(email);
		frame.setSignature(signature);
		frame.setIntroduce(introduce);

	}

	public void update() {
		boolean v = frame.verify();
		if (!v) {
			return;
		}
		String nickname = frame.getNickname();
		String gender = frame.getGender();
		String blood = frame.getBloodType();
		Date birthdate = frame.getBirthday();
		String homeAddress = frame.getHomeAddress();
		String locationAddress = frame.getLocationAddress();
		String mobile = frame.getMobile();
		String email = frame.getEmail();
		String signature = frame.getSignature();
		String introduce = frame.getIntroduce();

		if (null == nickname || "".equals(nickname)) {
			showPromptMessage("昵称不能空！");
			return;
		}

		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData userData = pb.getUserData();

		UserData user = new UserData();
		user.setId(userData.getId());
		// user.setHead(head);
		user.setNickname(nickname);
		user.setGender(gender);
		user.setBlood(blood);
		user.setBirthdate(birthdate);
		user.setHomeAddress(homeAddress);
		user.setLocationAddress(locationAddress);
		user.setMobile(mobile);
		user.setEmail(email);
		user.setSignature(signature);
		user.setIntroduce(introduce);

		DataBackActionAdapter action = new DataBackActionAdapter() {

			@Back
			public void back(Info info) {
				if (info.isSuccess()) {
					// userData.setHead(head);
					userData.setNickname(nickname);
					userData.setGender(gender);
					userData.setBlood(blood);
					userData.setBirthdate(birthdate);
					userData.setHomeAddress(homeAddress);
					userData.setLocationAddress(locationAddress);
					userData.setMobile(mobile);
					userData.setEmail(email);
					userData.setSignature(signature);
					userData.setIntroduce(introduce);
					setVisible(false);
					MainView mainView = appContext.getSingleView(MainView.class);
					// mainView.setVisible(true);
					mainView.setPersonalData(userData);
					UserSender us = appContext.getSender(UserSender.class);
					us.sendUpdate();
				} else {
					setVisible(true);
					showPrompt("修改失败！");
				}
			}
		};
		PersonalSender ps = this.appContext.getSender(PersonalSender.class);
		ps.updateUser(user, action);
	}

	public void showPromptMessage(String text) {
		frame.showPrompt(text);
	}

	@Override
	public boolean isShowing() {
		return false;
	}

	private void init(HeadListPane p) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				p.showWaiting(true, WaitingPane.show_waiting);
			}
		});

		List<HeadImagePanel> list = new ArrayList<HeadImagePanel>();
		for (int i = 1; i < 102; i++) {
			Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + i + "_100.gif");
			String value = i + "";
			HeadImagePanel hp = new HeadImagePanel();
			hp.setHeadSize(60);
			hp.setHeadRadius(60);
			hp.setImage(image);
			hp.setOnMouseClicked(m -> {
				select(image, value);
			});
			list.add(hp);

		}

		for (int i = 173; i < 265; i++) {
			Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + i + "_100.gif");
			String value = i + "";
			HeadImagePanel hp = new HeadImagePanel();
			hp.setHeadSize(60);
			hp.setHeadRadius(60);
			hp.setImage(image);
			hp.setOnMouseClicked(m -> {
				select(image, value);
			});
			list.add(hp);

		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				p.showWaiting(false, WaitingPane.show_waiting);
				p.setNodeList(list);
			}
		});
	}
}
