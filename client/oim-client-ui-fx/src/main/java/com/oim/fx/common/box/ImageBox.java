/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.common.box;

import java.io.File;
import java.util.HashMap;

import javafx.scene.image.Image;

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
			String pathString = new File(imagePath).toURI().toString();
			image = new Image(pathString, w, h, false, false);
		}
		return image;
		// BufferedImage bufferedImage =
		// ImageUtil.getRoundedCornerBufferedImage("Resources/Images/Head/User/90_100.gif",
		// 80, 80, 8, 8);
		// SwingFXUtils.toFXImage(bufferedImage, image);
	}

//	public static Image getImagePath(String imagePath, int w, int h, int cornersWidth, int cornerHeight) {
//		String key = imagePath + "_" + w + "_" + h + "_" + cornersWidth + "_" + cornerHeight;
//		Image image = imageMap.get(key);
//		if (null == image) {
//			WritableImage writableImage = new WritableImage(w, h);
//			BufferedImage bufferedImage = ImageUtil.getBufferedImage(imagePath, w, h, cornersWidth, cornerHeight);
//			SwingFXUtils.toFXImage(bufferedImage, writableImage);
//			image = writableImage;
//		}
//		return image;
//		// BufferedImage bufferedImage =
//		// ImageUtil.getRoundedCornerBufferedImage("Resources/Images/Head/User/90_100.gif",
//		// 80, 80, 8, 8);
//		// SwingFXUtils.toFXImage(bufferedImage, image);
//	}

	private static Image getImage(String imagePath) {
		Image image = null;
		String pathString = new File(imagePath).toURI().toString();
		image = new Image(pathString, false);
		return image;
	}
}
