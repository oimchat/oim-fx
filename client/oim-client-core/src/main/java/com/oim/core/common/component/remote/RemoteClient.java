package com.oim.core.common.component.remote;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class RemoteClient {

	public byte[] getScreenBytes() {

		BufferedImage image = ClientRobot.getScreen();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			//ImageIO.write(image, "jpg", output);
			//ByteArrayOutputStream output = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);// 转换成JPEG图像格式
			
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(image); 
			/* 压缩质量 */ jep.setQuality(0.5f, true); 
			encoder.encode(image, jep);
			//encoder.encode(image);
			output.close();
			//byte[] data = output.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		byte[] bytes = output.toByteArray();
		return bytes;
	}

	public void mouseMove(int x, int y) {
		ClientRobot.mouseMove(x, y);
	}

	public void mousePress(int button) {
		ClientRobot.mousePress(button);
	}

	public void mouseRelease(int button) {
		ClientRobot.mouseRelease(button);
	}

	public void mouseWheel(int rotation) {
		ClientRobot.mouseWheel(rotation);
	}

	public void keyPress(int keycode) {
		ClientRobot.keyPress(keycode);
	}

	public void keyRelease(int keycode) {
		ClientRobot.keyRelease(keycode);
	}
}
