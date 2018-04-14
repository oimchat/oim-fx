package com.oim.core.net.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月3日 下午4:23:40
 * @version 0.0.1
 */
public class SocketThread extends Thread {

	private Set<SocketDataHandler> socketDataHandlerSet = new HashSet<SocketDataHandler>();
	DatagramSocket dataSocket = null;
	private int port = 10086;
	private boolean connected = false;

	public void addSocketDataHandler(SocketDataHandler socketDataHandler) {
		socketDataHandlerSet.add(socketDataHandler);
	}
	
	

	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	@Override
	public void run() {
		initSocket();
		while (true) {
			receive();
			//threadSleep();
		}
	}

	@Override
	public void start() {
		super.start();
	}

//	private void threadSleep() {
//		try {
//			sleep(5);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	public void initSocket() {
		while (!connected) {
			try {
				dataSocket = new DatagramSocket(port);
				connected = true;
			} catch (SocketException e) {
				connected = false;
				port++;
				//e.printStackTrace();
			}
		}
	}

	public void send(byte[] bytes, SocketAddress address) {
		try {

			if (null != dataSocket && !dataSocket.isClosed()) {
				DatagramPacket dataPacket = new DatagramPacket(bytes, bytes.length, address);
				dataSocket.send(dataPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receive() {

		try {
			byte[] buf = new byte[1920 * 1080];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			dataSocket.receive(packet);
			//System.out.println("packet.getLength():"+packet.getLength());
			SocketData messageQueue = new SocketData();
			messageQueue.setPort(packet.getPort());
			messageQueue.setInetAddress(packet.getAddress());
			messageQueue.setBytes(packet.getData());
			messageQueue.setLength(packet.getLength());
			for (SocketDataHandler mh : socketDataHandlerSet) {
				mh.received(messageQueue);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
