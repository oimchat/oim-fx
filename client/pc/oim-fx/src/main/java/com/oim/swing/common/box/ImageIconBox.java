/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.swing.common.box;

import java.awt.Image;
import java.util.HashMap;

import com.oim.swing.common.util.ImageIconUtil;

/**
 * 为了减少io操作，系统中用到的一些
 * 
 * @author Hero
 */
public class ImageIconBox {

	private static HashMap<String, Image> imageMap = new HashMap<String, Image>();

	///////////////////////////////////
	private static HashMap<String, Image> emptyImageMap = new HashMap<String, Image>();

	public static Image getImagePath(String path) {
		Image image = imageMap.get(path);
		if (null == image) {
			image = ImageIconUtil.getImagePath(path);
			if (null != image) {
				imageMap.put(path, image);
			}
			return image;
		}
		return image;
	}

	public static Image getImagePath(String path, int w, int h) {
		Image image = imageMap.get(path + "_" + w + "_" + h);
		if (null == image) {
			image = ImageIconUtil.getImagePath(path, w, h);
			if (null != image) {
				imageMap.put(path + "_" + w + "_" + h, image);
			}
			return image;
		}
		return image;
	}

	public static Image getImagePath(String path, int width, int height, int cornersWidth, int cornerHeight) {
		Image image = imageMap.get(path + "_" + width + "_" + height + "_" + cornersWidth + "_" + cornerHeight);
		if (null == image) {
			image = ImageIconUtil.getRoundedCornerImage(path, width, height, cornersWidth, cornerHeight);
			if (null != image) {
				imageMap.put(path + "_" + width + "_" + height + "_" + cornersWidth + "_" + cornerHeight, image);
			}
			return image;
		}
		return image;
	}

	////////////////////////////////////

	public static Image getEmptyImage(int w, int h) {
		String key = w + "_" + h;
		Image image = emptyImageMap.get(key);
		if (null == image) {
			image = ImageIconUtil.getEmptyImage(w, h);
			if (null != image) {
				emptyImageMap.put(key, image);
			}
		}
		return image;
	}
}
