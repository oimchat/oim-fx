package com.im.server.general.business.thread;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.business.push.RemotePush;
import com.im.server.general.business.push.VideoPush;
import com.im.server.general.business.push.VoicePush;
import com.onlyxiahui.im.message.data.AddressData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月3日 下午4:23:40
 * @version 0.0.1
 */
@Component
public class SocketThread extends Thread {

	@Resource
	VideoPush videoPush;
	@Resource
	VoicePush voicePush;
	@Resource
	RemotePush remotePush;

	private Map<String, AddressData> videoMap = new ConcurrentHashMap<String, AddressData>();
	private Map<String, AddressData> voiceMap = new ConcurrentHashMap<String, AddressData>();
	private Map<String, AddressData> remoteMap = new ConcurrentHashMap<String, AddressData>();

	private Set<SocketDataHandler> socketDataHandlerSet = new HashSet<SocketDataHandler>();
	DatagramSocket dataSocket = null;
	private int port = 8310;
	private boolean connected = false;

	public SocketThread() {
		init();
	}

	private void init() {
		this.addSocketDataHandler(new SocketDataHandler() {

			@Override
			public void received(SocketData socketData) {

				ByteArrayInputStream in = new ByteArrayInputStream(socketData.getBytes());

				byte[] stringBytes = new byte[socketData.getLength()];

				try {
					in.read(stringBytes);
					String text = new String(stringBytes);
					String[] array = text.split(",");
					if (array.length == 2) {
						String userId = array[0];
						String type = array[1];
						if ("1".equals(type)) {
							AddressData va = new AddressData();
							va.setAddress(socketData.getInetAddress().getHostAddress());
							va.setPort(socketData.getPort());
							videoMap.put(userId, va);
							videoPush.pushReceivedVideoAddress(userId);
						}
						if ("2".equals(type)) {
							AddressData va = new AddressData();
							va.setAddress(socketData.getInetAddress().getHostAddress());
							va.setPort(socketData.getPort());
							voiceMap.put(userId, va);
							voicePush.pushReceivedVoiceAddress(userId);
						}
						if ("3".equals(type)) {
							AddressData va = new AddressData();
							va.setAddress(socketData.getInetAddress().getHostAddress());
							va.setPort(socketData.getPort());
							remoteMap.put(userId, va);
							remotePush.pushReceivedRemoteAddress(userId);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	public AddressData getVideoAddress(String key) {
		return videoMap.get(key);
	}

	public AddressData getVoiceAddress(String key) {
		return voiceMap.get(key);
	}

	public void removeVideoAddress(String key) {
		videoMap.remove(key);
	}

	public void removeVoiceAddress(String key) {
		voiceMap.remove(key);
	}

	public void removeAddress(String key) {
		removeVoiceAddress(key);
		removeVideoAddress(key);
	}

	public void addSocketDataHandler(SocketDataHandler socketDataHandler) {
		socketDataHandlerSet.add(socketDataHandler);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void run() {
		initSocket();
		while (true) {
			receive();
			threadSleep();
		}
	}

	public void start() {
		super.start();
	}

	private void threadSleep() {
		try {
			sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void initSocket() {
		while (!connected) {
			try {
				dataSocket = new DatagramSocket(port);
				connected = true;
			} catch (SocketException e) {
				connected = false;
				port++;
				e.printStackTrace();
			}
		}
	}

	public void send(byte[] bytes, InetSocketAddress address) {
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
			SocketData socketData = new SocketData();
			socketData.setPort(packet.getPort());
			socketData.setInetAddress(packet.getAddress());
			socketData.setBytes(packet.getData());
			socketData.setLength(packet.getLength());
			for (SocketDataHandler mh : socketDataHandlerSet) {
				mh.received(socketData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
