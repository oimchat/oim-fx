package com.oim.swing.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.oim.swing.common.box.ImageIconBox;
import com.oim.swing.ui.video.CameraVideoPanel;
import com.oim.swing.ui.video.VideoShowPanel;

public class VideoFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel basePanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout());
	JPanel tabPanel = new JPanel();
	JPanel ownPanel = new JPanel();
	JLabel ownLabel = new JLabel();
	JTabbedPane tabbedPane = new JTabbedPane();

	CameraVideoPanel cameraVideoPanel = new CameraVideoPanel();

	public VideoFrame() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("视频聊天");
		this.setSize(482, 665);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setIconImage(ImageIconBox.getImagePath("Resources/Images/Logo/logo_64.png"));
		this.add(basePanel);
		this.add(buttonPanel);
		initUI();
	}

	private void initUI() {
		tabPanel.setLayout(new CardLayout());
		tabPanel.add(tabbedPane);

		basePanel.setBounds(0, 0, 480, 600);
		buttonPanel.setBounds(0, 600, 480, 65);

		basePanel.setLayout(null);
		basePanel.add(tabPanel);
		basePanel.add(ownPanel);
		basePanel.setBackground(new Color(125, 155, 52));

		tabPanel.setBounds(0, 0, 480, 330);
		
		
		JPanel videoPanel = new JPanel();
		JPanel basePanel = new JPanel();
		JLabel backgroundLabel = new JLabel();
		
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

		videoPanel.add(ownLabel);
		

		ownPanel.setBounds(0, 330, 480, 270);
		//ownPanel.setBackground(Color.red);
		ownPanel.setLayout(new CardLayout());
		ownPanel.add(basePanel);

		
		
		JButton selectButton = new JButton("切换摄像头");
		//buttonPanel.add(selectButton);
		selectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cameraVideoPanel.chooseDevice();
			}
		});
		cameraVideoPanel.setPreferredSize(new Dimension(800, 450));
		
		cameraVideoPanel.setBounds(0, 0, 800, 450);
	}

	public void stopVideo() {
		if (cameraVideoPanel.isStart()) {
			cameraVideoPanel.stopVideo();
		}
	}

	public void startVideo() {
		if (!cameraVideoPanel.isStart()) {
			cameraVideoPanel.startVideo();
		}
	}

	public boolean isStartVideo() {
		return cameraVideoPanel.isStart();
	}

	public void addTab(String title, Component component) {
		tabbedPane.addTab(title, component);
	}

	public void removeTab(Component component) {
		int index = tabbedPane.indexOfComponent(component);
		if (-1 != index) {
			tabbedPane.removeTabAt(index);
		}
	}

	public void removeAllTab() {
		tabbedPane.removeAll();
	}
	
	public boolean isTabEmpty(){
		return tabbedPane.getComponentCount()==0;
	}
	
	public void setOwnImageIcon(ImageIcon imageIcon) {
		this.ownLabel.setIcon(imageIcon);
	}

	 
	public BufferedImage getVideoImage() {
		BufferedImage image = null;
		if (cameraVideoPanel.isStart()) {
			try {
				 image =cameraVideoPanel.getBufferedImage();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return image;
	}

	public static void main(String[] args) {
		try {
			// UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				VideoFrame camera = new VideoFrame();
				camera.setVisible(true);
				VideoShowPanel vsp = new VideoShowPanel();
				camera.addTab("账号", vsp);
				camera.addTab("002", vsp);
			}
		});
	}
}