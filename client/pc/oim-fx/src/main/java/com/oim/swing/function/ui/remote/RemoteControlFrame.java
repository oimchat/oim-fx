package com.oim.swing.function.ui.remote;

import java.awt.AWTEvent;
import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 * @author: XiaHui
 * @date: 2017年4月11日 下午5:17:30
 */
public class RemoteControlFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	JScrollPane scrollPane = new JScrollPane();
	KeyListener keyListener = null;

	public RemoteControlFrame() {
		initComponent();
		initEvent();
	}

	private void initComponent() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("远程控制");
		this.setSize(900, 600);
		this.setLayout(new CardLayout());
		this.add(scrollPane);

		scrollPane.setViewportView(panel);
		//panel.setLayout(new CardLayout());
		panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
		panel.add(label);
		// label.setText("hhhh");
	}

	private void initEvent() {

		class AWTEventListenerImpl implements AWTEventListener {
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event.getClass() == KeyEvent.class) {
					// 被处理的事件是键盘事件.
					KeyEvent keyEvent = (KeyEvent) event;
					if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
						// 按下时你要做的事情
						keyPressed(keyEvent);
					} else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {
						// 放开时你要做的事情
						keyReleased(keyEvent);
					}
				}
			}

		}

		Toolkit tk = Toolkit.getDefaultToolkit();  
        tk.addAWTEventListener(new AWTEventListenerImpl(), AWTEvent.KEY_EVENT_MASK);  
        label.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent e) {
        		 panel.requestFocus();
        	 }
		});
	}

	private void keyPressed(KeyEvent e) {
		if (null != keyListener) {
			keyListener.keyPressed(e);
		}
	}

	private void keyReleased(KeyEvent e) {
		if (null != keyListener) {
			keyListener.keyReleased(e);
		}
	}

	public void setIcon(Icon icon) {
		label.setIcon(icon);
	}

	public void setKeyListener(KeyListener l) {
		keyListener = l;
		// this.addKeyListener(l);
	}

	public void setMouseListener(MouseListener l) {
		label.addMouseListener(l);
	}

	public void setMouseMotionListener(MouseMotionListener l) {
		label.addMouseMotionListener(l);
	}

	public void setMouseWheelListener(MouseWheelListener l) {
		label.addMouseWheelListener(l);
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				RemoteControlFrame frame = new RemoteControlFrame();
				frame.setVisible(true);
			}
		});
	}
	// this.addMouseListener(new MouseListener(){
	//
	// @Override
	// public void mouseClicked(MouseEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void mousePressed(MouseEvent e) {
	// System.out.println("mousePressed");
	//
	// }
	//
	// @Override
	// public void mouseReleased(MouseEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void mouseEntered(MouseEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void mouseExited(MouseEvent e) {
	// // TODO Auto-generated method stub
	//
	// }});
}
