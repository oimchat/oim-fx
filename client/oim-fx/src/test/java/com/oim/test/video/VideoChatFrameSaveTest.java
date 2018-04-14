package com.oim.test.video;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import com.oim.swing.ui.video.CameraVideoPanel;

public class VideoChatFrameSaveTest extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel basePanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout());
	JPanel tabPanel = new JPanel();
	JPanel ownPanel = new JPanel();
	JTabbedPane tabbedPane = new JTabbedPane();

	CameraVideoPanel cameraVideoPanel = new CameraVideoPanel();


	public VideoChatFrameSaveTest() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("视频聊天");
		this.setSize(480, 680);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.add(basePanel);
		this.add(buttonPanel);
		initUI();
		init();
	}

	private void initUI() {

		tabPanel.setLayout(new CardLayout());
		tabPanel.add(tabbedPane);

		basePanel.setBounds(0, 0, 450, 550);
		buttonPanel.setBounds(0, 550, 480, 70);

		basePanel.setLayout(null);
		basePanel.add(tabPanel);
		basePanel.add(cameraVideoPanel);
		basePanel.setBackground(new Color(125, 155, 52));

		tabPanel.setBounds(0, 0, 450, 275);
		cameraVideoPanel.setBounds(0, 275, 450, 275);

		JButton selectButton = new JButton("切换摄像头");
		buttonPanel.add(selectButton);
		selectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cameraVideoPanel.chooseDevice();
			}
		});

		JButton startButton = new JButton("开始");
		buttonPanel.add(startButton);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cameraVideoPanel.startVideo();
			}
		});
		JButton stopButton = new JButton("停止");
		buttonPanel.add(stopButton);
		stopButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cameraVideoPanel.stopVideo();
			}
		});
		new ImageThread().start();
	}

	private void init() {
		final JLabel label = new JLabel();
		JPanel panel = new JPanel();
		panel.setLayout(new CardLayout());
		panel.add(label);
		// panel.setBackground(Color.red);
		tabbedPane.addTab("000", panel);
	}

	public static void main(String[] args) {
		VideoChatFrameSaveTest camera = new VideoChatFrameSaveTest();
		camera.setVisible(true);
	}

	class ImageThread extends Thread {
		String saveMp4name = "Temp/f1.flv"; // 保存的视频名称
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(saveMp4name, 640, 480);
		OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
		long time = 0;
		boolean has = false;

		public void run() {
			//recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);// 28
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_FLV1);// 28
			// recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 13
			recorder.setFormat("flv");
			// recorder.setFormat("mov,mp4,m4a,3gp,3g2,mj2,h264,ogg,MPEG4");
			recorder.setFrameRate(20);
			recorder.setPixelFormat(0);// yuv420p
			while (true) {
				try {
					createImage();
					sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void createImage() {

			if (cameraVideoPanel.isStart()) {

				try {
					if (!has) {
						recorder.start();
						has = true;
					}
					Frame frame = cameraVideoPanel.getFrame();
					//IplImage image = conveter.convert(frame);
					recorder.record(frame);
					// 释放内存？ cvLoadImage(fname); // 非常吃内存！！
					//opencv_core.cvReleaseImage(image);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				if (has) {
					try {
						recorder.stop();
						recorder.release();
						has = false;
					} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}