package com.oim.test.video;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class LivePlayTest3 extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel basePanel = new JPanel();
	JPanel playPanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout());

	JLabel playLabel = new JLabel();

	boolean start = false;

	public LivePlayTest3() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("桌面录制");
		this.setSize(480, 680);
		this.setLocationRelativeTo(null);
		this.setLayout(new CardLayout());
		this.add(basePanel);
		initUI();
	}

	private void initUI() {

		playPanel.setLayout(new CardLayout());
		playPanel.add(playLabel);

		basePanel.setLayout(new BorderLayout());
		basePanel.setBackground(new Color(125, 155, 52));

		basePanel.add(playPanel, BorderLayout.CENTER);
		basePanel.add(buttonPanel, BorderLayout.SOUTH);

		JButton startButton = new JButton("开始");
		buttonPanel.add(startButton);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				start = true;
			}
		});
		JButton stopButton = new JButton("停止");
		buttonPanel.add(stopButton);
		stopButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				start = false;
			}
		});
		new ImageThread().start();
	}

	public static void main(String[] args) {
		LivePlayTest3 camera = new LivePlayTest3();
		camera.setVisible(true);

	}

	class ImageThread extends Thread {

		String url = "rtmp://192.168.1.200:1935/oflaDemo/110"; // 保存的视频名称
		int v_rs = 25;

		long startTime = 0;
		FrameGrabber grabber = null;
		// OpenCVFrameConverter.ToIplImage converter = new
		// OpenCVFrameConverter.ToIplImage();
		Java2DFrameConverter converter = new Java2DFrameConverter();

		long time = 0;
		boolean has = false;

		public void run() {
			Loader.load(opencv_objdetect.class);

			try {
				grabber = FFmpegFrameGrabber.createDefault(url);
				grabber.start();
			} catch (Exception e) {
				try {
					grabber.restart();
				} catch (Exception e1) {
				}
			}
			while (true) {
				// try {
				getBufferedImage();
				// sleep(1);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			}
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
						playLabel.setIcon(icon);
					}
				}

			} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
				e.printStackTrace();
			}
			return bufferedImage;
		}
	}
}