package com.oim.fx.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.oim.core.business.view.ThemeView;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.config.data.Theme;
import com.oim.fx.common.component.IconButton;
import com.oim.fx.ui.util.UIBox;
import com.oim.ui.fx.classics.ThemeStage;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class ThemeViewImpl extends AbstractView implements ThemeView {

	ThemeStage themeStage;// = new ThemeStage();;
	String backgroundImage = null;

	public ThemeViewImpl(AppContext appContext) {
		super(appContext);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				themeStage = new ThemeStage();
				initEvent();
			}
		});
		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				int iterations = 0;
				initData();
				return iterations;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private void initEvent() {
		themeStage.setDoneAction(a -> {
			themeStage.hide();

			Task<Integer> task = new Task<Integer>() {
				@Override
				protected Integer call() throws Exception {
					int iterations = 0;
					Theme theme = new Theme();
					Color color = themeStage.getColor();
					if (null != backgroundImage && !"".equals(backgroundImage)) {
						theme.setBackgroundImage(backgroundImage);
					}
					theme.setRed(color.getRed());
					theme.setGreen(color.getGreen());
					theme.setBlue(color.getBlue());
					theme.setOpacity(color.getOpacity());
					ConfigManage.addOrUpdate(Theme.config_file_path, theme);
					UIBox.setColor(color);
					UIBox.setBackgroundImageUrl(backgroundImage);
					UIBox.update();
					return iterations;
				}
			};
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
		});
	}

	private void initData() {

		double imageWidth = 130D;
		double imageHeight = 80D;

		List<IconButton> list = new ArrayList<IconButton>();
		for (int i = 0; i < 26; i++) {
			String path = "Resources/Images/Wallpaper/" + i + ".jpg";

			//Image normalImage = ImageBox.getImagePath(path);
			String pathString = new File(path).toURI().toString();
			Image normalImage  = new Image(pathString, false);
			IconButton isb = new IconButton();
			isb.setNormalImage(normalImage);
			isb.setImageSize(imageWidth, imageHeight);
			isb.setOnAction(a -> {
				backgroundImage = path;
				themeStage.setBackground(path);
			});
			list.add(isb);
		}

		for (int i = 101; i < 111; i++) {
			String path = "Resources/Images/Wallpaper/" + i + ".jpg";

			//Image normalImage = ImageBox.getImagePath(path);
			String pathString = new File(path).toURI().toString();
			Image normalImage  = new Image(pathString, false);
			IconButton isb = new IconButton();
			isb.setNormalImage(normalImage);
			isb.setImageSize(imageWidth, imageHeight);
			isb.setOnAction(a -> {
				backgroundImage = path;
				themeStage.setBackground(path);
			});
			list.add(isb);
		}

		for (int i = 201; i < 224; i++) {
			String path = "Resources/Images/Wallpaper/" + i + ".jpg";

			//Image normalImage = ImageBox.getImagePath(path);
			String pathString = new File(path).toURI().toString();
			Image normalImage  = new Image(pathString, false);
			IconButton isb = new IconButton();
			isb.setNormalImage(normalImage);
			isb.setImageSize(imageWidth, imageHeight);
			isb.setOnAction(a -> {
				backgroundImage = path;
				themeStage.setBackground(path);
			});
			list.add(isb);
		}

		String path = "Resources/Images/Wallpaper/default.jpg";

		//Image normalImage = ImageBox.getImagePath(path);
		String pathString = new File(path).toURI().toString();
		Image normalImage  = new Image(pathString, false);
		IconButton isb = new IconButton();
		isb.setNormalImage(normalImage);
		isb.setImageSize(imageWidth, imageHeight);
		isb.setOnAction(a -> {
			backgroundImage = path;
			themeStage.setBackground(path);
		});
		list.add(isb);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				themeStage.setImageStyleButtonList(list);
			}
		});
	}

	public void setVisible(boolean visible) {
		Theme theme = (Theme) ConfigManage.get(Theme.config_file_path, Theme.class);
		String backgroundImageUrl = theme.getBackgroundImage();
		Color color = Color.color(theme.getRed(), theme.getGreen(), theme.getBlue(), theme.getOpacity());

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible&&!isShowing()) {
					themeStage.setColor(color);
					if(null!=backgroundImageUrl&&!"".equals(backgroundImageUrl)){
						themeStage.setBackground(backgroundImageUrl);
					}
				}
				if (visible) {
					themeStage.show();
					themeStage.toFront();
				} else {
					themeStage.hide();
				}
			}
		});
	}

	public boolean isShowing() {
		return null != themeStage && themeStage.isShowing();
	}

	@Override
	public void showPrompt(String text) {

	}

}
