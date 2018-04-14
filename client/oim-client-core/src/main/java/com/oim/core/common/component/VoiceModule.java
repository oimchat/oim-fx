package com.oim.core.common.component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.core.common.action.GetAction;
import com.oim.core.common.sound.VoicePlay;
import com.oim.core.common.util.OnlyByteUtil;
import com.oim.core.net.thread.SocketData;
import com.oim.core.net.thread.SocketDataHandler;
import com.oim.core.net.thread.SocketThread;
import com.only.common.util.OnlyZipUtil;
import com.onlyxiahui.im.message.data.AddressData;

/**
 * @author: XiaHui
 * @date: 2016年10月14日 下午2:28:07
 */
public class VoiceModule {

	private Map<String, AddressData> sendMap = new ConcurrentHashMap<String, AddressData>();
	private Map<String, Float> receiveMap = new ConcurrentHashMap<String, Float>();
	private SocketThread videoSocketThread = new SocketThread();
	VoiceThread voiceThread = new VoiceThread();
	VoicePlay voicePlay = new VoicePlay();

	ByteDataHandler byteDataHandler;

	GetAction<String> getAction;
	
	public VoiceModule() {
		init();
	}

	public void start() {
		videoSocketThread.start();
		voiceThread.start();
	}

	private void init() {
		this.addHandler(new SocketDataHandler() {
			@Override
			public void received(SocketData socketData) {
				setVoiceData(socketData);
			}
		});
	}
	
	public void setGetAction(GetAction<String> getAction) {
		this.getAction=getAction;
	}

	public void setByteDataHandler(ByteDataHandler byteDataHandler) {
		this.byteDataHandler = byteDataHandler;
	}

	public void addHandler(SocketDataHandler socketDataHandler) {
		videoSocketThread.addSocketDataHandler(socketDataHandler);
	}

	public void connectServer(byte[] bytes, SocketAddress address) {
		videoSocketThread.send(bytes, address);
	}

	public void putSend(String key, AddressData addressData) {
		sendMap.put(key, addressData);
	}

	public void removeSend(String key) {
		sendMap.remove(key);
	}

	public boolean hasSend(String key) {
		return sendMap.containsKey(key);
	}

	public void removeSendAll() {
		sendMap.clear();
	}

	public void putReceive(String key) {
		receiveMap.put(key, 100f);
	}

	public void putReceiveValue(String key, float value) {
		if (receiveMap.containsKey(key)) {
			receiveMap.put(key, value);
		}
	}

	public void removeReceive(String key) {
		receiveMap.remove(key);
	}

	public boolean hasReceive(String key) {
		return receiveMap.containsKey(key);
	}

	public void removeReceiveAll() {
		receiveMap.clear();
	}

	public Set<String> keySet() {
		return sendMap.keySet();
	}

	public boolean isEmpty() {
		return sendMap.isEmpty();
	}

	class VoiceThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					createImage();
					threadSleep(30);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void threadSleep(long time) {
			try {
				sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void createImage() {
			if (sendMap.isEmpty()) {
				if (voicePlay.isListenStart()) {
					voicePlay.closeListen();
				}
				threadSleep(1000);
			} else {
				if (!voicePlay.isListenStart()) {
					voicePlay.startListen();
				}
				if (voicePlay.isListenStart()) {
					try {

						byte[] data = voicePlay.getVoice();

						if (null != byteDataHandler) {
							byteDataHandler.byteData(data);
						}

						String userId = (null == getAction) ? "" : getAction.get();

						byte[] keyByte = userId.getBytes();

						int size = keyByte.length;
						byte[] sizeBytes = OnlyByteUtil.intToBytes(size);

						byte[] allData = OnlyByteUtil.byteMerger(OnlyByteUtil.byteMerger(sizeBytes, keyByte), data);
						allData = OnlyZipUtil.zip(allData);
						List<AddressData> list = new ArrayList<AddressData>(sendMap.values());
						for (AddressData va : list) {
							SocketAddress socketAddress = new InetSocketAddress(va.getAddress(), va.getPort());
							videoSocketThread.send(allData, socketAddress);
						}
					} catch (Exception e) {
					}
				}
			}
		}
	}

	private void setVoiceData(SocketData socketData) {
		if (!receiveMap.isEmpty()) {
			if (!voicePlay.isPlayStart()) {
				voicePlay.startPlay();
			}
			byte[] bytes = socketData.getBytes();
			bytes = OnlyZipUtil.unZip(bytes);
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			byte[] sizeBytes = new byte[4];
			try {
				in.read(sizeBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int size = OnlyByteUtil.bytesToInt(sizeBytes);
			byte[] keyByte = new byte[size];
			try {
				in.read(keyByte);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String key = new String(keyByte);
			int length = bytes.length - 4 - size;

			if (receiveMap.containsKey(key)) {
				float value = receiveMap.get(key);
				byte[] data = new byte[length];
				try {
					in.read(data);
					voicePlay.playVoice(data, value);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
