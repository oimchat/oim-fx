package com.oim.core.common.component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 * @author: XiaHui
 * @date: 2016年10月14日 下午2:31:14
 */
public class CameraVideo {
	int captureWidth = 480;
	int captureHeight = 270;
	OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
	Java2DFrameConverter converter = new Java2DFrameConverter();
	private boolean start = false;
	final double inverseGamma = 1.0;
	private boolean camera = true;

	public CameraVideo() {
	}

	public void chooseDevice() {
		if (start) {
			startVideo();
		}
	}

	public boolean startVideo() {
		try {
			grabber.setImageWidth(captureWidth);
			grabber.setImageHeight(captureHeight);
			grabber.start();
			start = true;
		} catch (Exception e) {
			e.printStackTrace();
			start = false;
			camera = false;
		}
		return start;
	}

	public void stopVideo() {
		try {
			start = false;
			grabber.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isStart() {
		return start;
	}

	public boolean hasCamera() {
		return camera;
	}

	public byte[] getVideoBytes() {
		BufferedImage image = getBufferedImage();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", output);
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

		// ByteArrayOutputStream output = new ByteArrayOutputStream();
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);//
		// 转换成JPEG图像格式
		// encoder.encode(image);
		// output.close();
		// byte[] data = output.toByteArray();
	}

	public BufferedImage getBufferedImage() {
		Frame capturedFrame = null;
		boolean flipChannels = false;
		BufferedImage bufferedImage = null;
		try {
			if (start) {
				if ((capturedFrame = grabber.grab()) != null) {
					int type = Java2DFrameConverter.getBufferedImageType(capturedFrame);
					double gamma = type == BufferedImage.TYPE_CUSTOM ? 1.0 : inverseGamma;
					bufferedImage = converter.getBufferedImage(capturedFrame, gamma, flipChannels, null);
				}
			}
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}
}
