package com.oim.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

/**
 * @author XiaHui
 * @date 2017-12-21 16:29:32
 */
public class ImageUtil {

	public static Image toImage(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		Image image = new javafx.scene.image.Image(in);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static Image loadImage(String url) {
		Image image = new javafx.scene.image.Image(url, true);
		return image;
	}
}
