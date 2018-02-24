package com.oim.test.video;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import com.oim.common.system.ClientRobot;
public class DesktopLiveTest extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel basePanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout());
	JPanel tabPanel = new JPanel();
	JPanel ownPanel = new JPanel();
	JTabbedPane tabbedPane = new JTabbedPane();

	boolean start = false;

	public DesktopLiveTest() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("桌面录制");
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
		basePanel.setBackground(new Color(125, 155, 52));

		tabPanel.setBounds(0, 0, 450, 275);

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

	private void init() {
		final JLabel label = new JLabel();
		JPanel panel = new JPanel();
		panel.setLayout(new CardLayout());
		panel.add(label);
		// panel.setBackground(Color.red);
		tabbedPane.addTab("000", panel);
	}

	public static void main(String[] args) {
		DesktopLiveTest camera = new DesktopLiveTest();
		camera.setVisible(true);
	}

	class ImageThread extends Thread {
		
		long startTime = 0;

		long videoTS = 0;
		final private static int FRAME_RATE = 30;
		final private static int GOP_LENGTH_IN_FRAMES = 60;

		
		String url = "rtmp://192.168.1.200:1935/oflaDemo/110"; // 保存的视频名称
		
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(url, 1920, 1080, 2);
		//FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(url, 1920, 1080);
		OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
		Java2DFrameConverter converter = new Java2DFrameConverter();
		long time = 0;
		boolean has = false;

		public void run() {
			
			// org.bytedeco.javacv.FFmpegFrameRecorder.FFmpegFrameRecorder(String

			// filename, int imageWidth, int imageHeight, int audioChannels)
			// For each param, we're passing in...
			// filename = either a path to a local file we wish to create, or an
			// RTMP url to an FMS / Wowza server
			// imageWidth = width we specified for the grabber
			// imageHeight = height we specified for the grabber
			// audioChannels = 2, because we like stereo
			
			recorder.setInterleaved(true);
			// decrease "startup" latency in FFMPEG (see:
			// https://trac.ffmpeg.org/wiki/StreamingGuide)

			recorder.setVideoOption("tune", "zerolatency");

			// tradeoff between quality and encode speed
			// possible values are ultrafast,superfast, veryfast, faster, fast,
			// medium, slow, slower, veryslow
			// ultrafast offers us the least amount of compression (lower encoder
			// CPU) at the cost of a larger stream size
			// at the other end, veryslow provides the best compression (high
			// encoder CPU) while lowering the stream size
			// (see: https://trac.ffmpeg.org/wiki/Encode/H.264)

			recorder.setVideoOption("preset", "ultrafast");

			// Constant Rate Factor (see: https://trac.ffmpeg.org/wiki/Encode/H.264)

			recorder.setVideoOption("crf", "28");

			// 2000 kb/s, reasonable "sane" area for 720

			recorder.setVideoBitrate(2000000);
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
			recorder.setFormat("flv");

			// FPS (frames per second)

			recorder.setFrameRate(FRAME_RATE);

			// Key frame interval, in our case every 2 seconds -> 30 (fps) * 2 = 60

			// (gop length)
			recorder.setGopSize(GOP_LENGTH_IN_FRAMES);

			// We don't want variable bitrate audio

			recorder.setAudioOption("crf", "0");
			// Highest quality

			recorder.setAudioQuality(0);
			// 192 Kbps

			recorder.setAudioBitrate(192000);
			recorder.setSampleRate(44100);
			recorder.setAudioChannels(2);
			recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);

			// Jack 'n coke... do it...

			while (true) {
//				try {
					createImage();
					//sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
		}

		public void createImage() {

			if (start) {

				try {
					if (!has) {
						recorder.start();
						has = true;
					}
					BufferedImage img = ClientRobot.getScreen();
					//IplImage..createFrom(img);
					 //IplImage image = cvLoadImage(""); 
					Frame frame = converter.convert(img);
					//converter.getFrame(image, gamma)
					// IplImage image = conveter.convert(frame);
					
					if (startTime == 0) {
						startTime = System.currentTimeMillis();
					}

					// Create timestamp for this frame

					videoTS = 1000 * (System.currentTimeMillis() - startTime);

					// Check for AV drift

					if (videoTS > recorder.getTimestamp()) {
						//System.out.println("Lip-flap correction:" + videoTS + " :" + recorder.getTimestamp() + " ->" + (videoTS - recorder.getTimestamp()));
						// We tell the recorder to write this frame at this timestamp
						recorder.setTimestamp(videoTS);
					}
					// Send the frame to the org.bytedeco.javacv.FFmpegFrameRecorder
					recorder.record(frame);
					
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