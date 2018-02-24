package com.oim.swing.ui.video;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

import com.oim.swing.common.box.ImageIconBox;

import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年6月6日 上午9:43:44
 * @version 0.0.1
 */
public class CameraVideoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	int captureWidth = 800;
	int captureHeight = 480;
	OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
	JLabel iconLabel = new JLabel();
	JPanel videoPanel = new JPanel();
	JPanel basePanel = new JPanel();
	JLabel backgroundLabel = new JLabel();

	private boolean start = false;
	Java2DFrameConverter converter = new Java2DFrameConverter();

	public CameraVideoPanel() {
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new CardLayout());
		this.add(basePanel);

		basePanel.setLayout(new javax.swing.OverlayLayout(basePanel));

		JPanel panel = new JPanel();

		panel.setBackground(Color.white);
		panel.setLayout(new java.awt.GridBagLayout());
		panel.add(backgroundLabel);

		basePanel.add(videoPanel);
		basePanel.add(panel);

		backgroundLabel.setIcon(new ImageIcon(ImageIconBox.getImagePath("Resources/Video/tab_video_other-fs8@2x.png")));

		videoPanel.setOpaque(false);
		videoPanel.setLayout(new CardLayout());

		videoPanel.add(iconLabel);
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
		}
		return start;
	}

	public void stopVideo() {
		try {
			start = false;
			grabber.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public String getMediaLocator() {
	// return mediaLocator;
	// }

	public boolean isStart() {
		return start;
	}

	final double inverseGamma = 1.0;

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
					Image image = bufferedImage;
					ImageIcon icon = new ImageIcon(image);
					iconLabel.setIcon(icon);
				}
			}

		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}
	
	public Frame getFrame() {
		Frame capturedFrame = null;
		boolean flipChannels = false;
		BufferedImage bufferedImage = null;
		try {
			if (start) {
				if ((capturedFrame = grabber.grab()) != null) {
					int type = Java2DFrameConverter.getBufferedImageType(capturedFrame);
					double gamma = type == BufferedImage.TYPE_CUSTOM ? 1.0 : inverseGamma;
					bufferedImage = converter.getBufferedImage(capturedFrame, gamma, flipChannels, null);
					Image image = bufferedImage;
					ImageIcon icon = new ImageIcon(image);
					iconLabel.setIcon(icon);
				}
			}

		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
		return capturedFrame;
	}
}
