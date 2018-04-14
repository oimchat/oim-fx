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

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class LivePlayTest extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel basePanel = new JPanel();
	JPanel playPanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout());

	JLabel playLabel = new JLabel();

	boolean start = false;

	public LivePlayTest() {
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
		LivePlayTest camera = new LivePlayTest();
		camera.setVisible(true);
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

	/**
	 * 按帧录制视频
	 * 
	 * @param inputFile-该地址可以是网络直播/录播地址，也可以是远程/本地文件路径
	 * @param outputFile
	 *            -该地址只能是文件地址，如果使用该方法推送流媒体服务器会报错，原因是没有设置编码格式
	 * @throws FrameGrabber.Exception
	 * @throws FrameRecorder.Exception
	 * @throws org.bytedeco.javacv.FrameRecorder.Exception
	 */
	public static void frameRecord(String inputFile, String outputFile, int audioChannel)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {

		boolean isStart = true;// 该变量建议设置为全局控制变量，用于控制录制结束
		// 获取视频源
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
		// 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);
		// 开始取视频源
		recordByFrame(grabber, recorder, isStart);
	}

	private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		try {// 建议在线程中使用该方法
			grabber.start();
			recorder.start();
			Frame frame = null;
			while (status && (frame = grabber.grabFrame()) != null) {
				recorder.record(frame);
			}
			recorder.stop();
			grabber.stop();
		} finally {
			if (grabber != null) {
				grabber.stop();
			}
		}
	}
}