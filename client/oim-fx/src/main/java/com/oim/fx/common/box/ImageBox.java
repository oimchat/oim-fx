/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.common.box;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.fx.common.util.ImageUtil;
import com.onlyxiahui.im.bean.UserData;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author XiaHui
 */
public class ImageBox {

	private static final HashMap<String, Image> classPathImageMap = new HashMap<String, Image>();
	private static final HashMap<String, Image> imageMap = new HashMap<String, Image>();

	public static Image getImageClassPath(String classPath) {
		Image image = getImageClassPath(classPath, false);
		return image;
	}

	public static Image getImageClassPath(String classPath, boolean loadNew) {
		Image image = classPathImageMap.get(classPath);
		if (null == image || loadNew) {
			image = new Image(classPath, false);
		}
		return image;
	}

	public static Image getImagePath(String imagePath) {
		Image image = getImagePath(imagePath, false);
		return image;
	}

	public static Image getImagePath(String imagePath, boolean loadNew) {
		Image image = imageMap.get(imagePath);
		if (null == image || loadNew) {
			image = getImage(imagePath);
		}
		return image;
	}

	public static Image getImagePath(String imagePath, int w, int h) {
		String key = imagePath + "_" + w + "_" + h;
		Image image = imageMap.get(key);
		if (null == image) {
			WritableImage writableImage = new WritableImage(w, h);
			BufferedImage bufferedImage = ImageUtil.getBufferedImage(imagePath, w, h, 0, 0);
			SwingFXUtils.toFXImage(bufferedImage, writableImage);
			image = writableImage;
		}
		return image;
		// BufferedImage bufferedImage =
		// ImageUtil.getRoundedCornerBufferedImage("Resources/Images/Head/User/90_100.gif",
		// 80, 80, 8, 8);
		// SwingFXUtils.toFXImage(bufferedImage, image);
	}

	public static Image getImagePath(String imagePath, int w, int h, int cornersWidth, int cornerHeight) {
		String key = imagePath + "_" + w + "_" + h + "_" + cornersWidth + "_" + cornerHeight;
		Image image = imageMap.get(key);
		if (null == image) {
			WritableImage writableImage = new WritableImage(w, h);
			BufferedImage bufferedImage = ImageUtil.getBufferedImage(imagePath, w, h, cornersWidth, cornerHeight);
			SwingFXUtils.toFXImage(bufferedImage, writableImage);
			image = writableImage;
		}
		return image;
		// BufferedImage bufferedImage =
		// ImageUtil.getRoundedCornerBufferedImage("Resources/Images/Head/User/90_100.gif",
		// 80, 80, 8, 8);
		// SwingFXUtils.toFXImage(bufferedImage, image);
	}

	private static Image getImage(String imagePath) {
		Image image = null;
		String pathString = new File(imagePath).toURI().toString();
		image = new Image(pathString, false);
		return image;
	}

	private static Map<String, Image> statusImageIconMap = new ConcurrentHashMap<String, Image>();

	public static Image getStatusImageIcon(String status) {
		status = (null == status) ? "" : status;
		Image image = statusImageIconMap.get(status);
		if (null == image) {
			switch (status) {
			case UserData.status_online:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/imonline.png");
				break;
			case UserData.status_call_me:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/call_me.png");
				break;
			case UserData.status_away:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/away.png");
				break;
			case UserData.status_busy:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/busy.png");
				break;
			case UserData.status_mute:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/mute.png");
				break;
			case UserData.status_invisible:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/invisible.png");
				break;
			case UserData.status_offline:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/imoffline.png");
				break;
			default:
				image = ImageBox.getImageClassPath("/oim/common/images/status/flag/big/imoffline.png");
				break;
			}
			statusImageIconMap.put(status, image);
		}
		return image;
	}
}
