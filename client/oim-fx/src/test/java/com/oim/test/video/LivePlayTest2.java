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

public class LivePlayTest2 extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel basePanel = new JPanel();
	JPanel playPanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout());

	JLabel playLabel = new JLabel();

	boolean start = false;

	public LivePlayTest2() {
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
		// LivePlayTest2 camera = new LivePlayTest2();
		// camera.setVisible(true);

		//String inputFile = "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0";

		//String outputFile = "rtmp://192.168.30.21/live/pushFlow";
		String inputFile = "rtmp://192.168.1.200:1935/oflaDemo/stream1519435890474";

		try {
			recordPush(inputFile, 25);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 转流器
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @throws Exception
	 * @throws org.bytedeco.javacv.FrameRecorder.Exception
	 * @throws InterruptedException
	 */
	public static void recordPush(String inputFile, int v_rs) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception, InterruptedException {
		Loader.load(opencv_objdetect.class);
		long startTime = 0;
		FrameGrabber grabber = FFmpegFrameGrabber.createDefault(inputFile);
		try {
			grabber.start();
		} catch (Exception e) {
			try {
				grabber.restart();
			} catch (Exception e1) {
				throw e;
			}
		}

		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
		Frame grabframe = grabber.grab();
		IplImage grabbedImage = null;
		if (grabframe != null) {
			System.out.println("取到第一帧");
			grabbedImage = converter.convert(grabframe);
		} else {
			System.out.println("没有取到第一帧");
		}

		System.out.println("开始推流");
		CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		while (frame.isVisible() && (grabframe = grabber.grab()) != null) {
			System.out.println("推流...");
			frame.showImage(grabframe);
			grabbedImage = converter.convert(grabframe);
			Frame rotatedFrame = converter.convert(grabbedImage);

			if (startTime == 0) {
				startTime = System.currentTimeMillis();
			}

			Thread.sleep(40);
		}
		frame.dispose();

		grabber.stop();
		System.exit(2);
	}

	class ImageThread extends Thread {

		String url = "rtmp://192.168.1.200:1935/oflaDemo/stream1517215039242"; // 保存的视频名称
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url);
		OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
		Java2DFrameConverter converter = new Java2DFrameConverter();
		long time = 0;
		boolean has = false;

		public void run() {

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