package com.only.net.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.log4j.Logger;

import com.only.net.server.DataQueueItem.Type;
import com.only.net.session.SocketSession;

/**
 * @author XiaHui
 * @date 2015年3月10日 上午8:53:09
 */
public class DataHandlerThread extends Thread {

	private static final Logger logger = Logger.getLogger(DataHandlerThread.class);
	private SessionServer sessionServer;

	private long sleepTime = 1;

	public DataHandlerThread(SessionServer sessionServer) {
		this.sessionServer = sessionServer;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		DataQueueItem dataQueue;
		while (true) {
			while (!sessionServer.isEmpty()) {
				dataQueue = sessionServer.poll();
				sendMessage(dataQueue);
			}
			threadSleep(sleepTime);
		}
	}

	private void threadSleep(long time) {
		try {
			sleep(time);
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}

	private void sendMessage(DataQueueItem dataQueue) {
		if (null != dataQueue) {
			List<String> keyList = dataQueue.getKeyList();

			if (dataQueue.getType() == Type.one || dataQueue.getType() == Type.list) {
				if (null != keyList) {
					for (String key : keyList) {
						CopyOnWriteArraySet<SocketSession> set = sessionServer.get(key);
						sendMessage(set, dataQueue);
					}
				}
			} else if (dataQueue.getType() == Type.all) {
				sendMessage(sessionServer.getAll(), dataQueue);
			} else if (dataQueue.getType() == Type.without) {
				Map<String, String> map = new HashMap<String, String>();
				if (null != keyList) {
					for (String key : keyList) {
						map.put(key, key);
					}
				}
				for (SocketSession sd : sessionServer.getAll()) {
					if (!map.containsKey(sd.getKey())) {
						sendMessage(sd, dataQueue);
					}
				}
			}
		}
	}

	private void sendMessage(CopyOnWriteArraySet<SocketSession> set, DataQueueItem dataQueue) {
		if (null != set) {
			for (SocketSession sd : set) {
				sendMessage(sd, dataQueue);
			}
		}
	}

	private void sendMessage(SocketSession socketSession, DataQueueItem dataQueue) {
		if (null != socketSession) {
			write(socketSession, dataQueue.getData());
		}
	}

	private void write(SocketSession socketSession, Object data) {
		if (null != socketSession && null != data) {
			try {
				socketSession.write(data);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
}
