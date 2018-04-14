package com.oim.test.swing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.oim.core.net.thread.SocketData;
import com.oim.core.net.thread.SocketDataHandler;
import com.oim.core.net.thread.SocketThread;
import com.oim.swing.ui.video.CameraVideoPanel;

public class VideoFrameTest extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel basePanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout());
	JPanel tabPanel = new JPanel();
	JPanel ownPanel = new JPanel();
	JTabbedPane tabbedPane = new JTabbedPane();
	
	
	CameraVideoPanel cameraVideoPanel = new CameraVideoPanel();
	
	SocketThread socketThread = new SocketThread();

	public VideoFrameTest() {
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
		//panel.setBackground(Color.red);
		socketThread.start();

		tabbedPane.addTab("000", panel);
		socketThread.addSocketDataHandler(new SocketDataHandler() {

			@Override
			public void received(SocketData messageQueue) {
				ByteArrayInputStream input = new ByteArrayInputStream(messageQueue.getBytes());
				try {
					Image image = ImageIO.read(input);
					final ImageIcon icon = new ImageIcon(image);
					java.awt.EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							label.setIcon(icon);
						}
					});
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {

		VideoFrameTest camera = new VideoFrameTest();
		camera.setVisible(true);
	}

	class ImageThread extends Thread {
		SocketAddress address = new InetSocketAddress("", 10087);

		public void run() {
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
					BufferedImage image =cameraVideoPanel.getBufferedImage();
//					ByteArrayOutputStream output = new ByteArrayOutputStream();
//					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);// 转换成JPEG图像格式
					//JPEGEncodeParam jpeg = encoder.getDefaultJPEGEncodeParam(image);
					//jpeg.setQuality(0.5f, false);
					//encoder.setJPEGEncodeParam(jpeg);
//					encoder.encode(image);
//					output.close();
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					try {
						ImageIO.write(image, "jpg", output);
					} catch (IOException e) {
						e.printStackTrace();
					}finally {
						try {
							output.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					socketThread.send(output.toByteArray(), address);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
}