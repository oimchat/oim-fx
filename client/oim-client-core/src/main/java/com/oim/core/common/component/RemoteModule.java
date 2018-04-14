package com.oim.core.common.component;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oim.core.common.component.remote.EventData;
import com.oim.core.common.component.remote.RemoteClient;
import com.oim.core.common.component.remote.ScreenHandler;
import com.oim.core.net.connect.remote.RemoteConnector;
import com.oim.core.net.connect.remote.codec.BytesData;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.common.util.OnlyZipUtil;
import com.only.net.connect.ConnectData;

import net.sf.json.util.JSONUtils;

/**
 * @author: XiaHui
 * @date: 2016年10月14日 下午2:28:07
 */
public class RemoteModule {

	public static String action_auth = "0001";
	public static String action_client = "0002";
	public static String action_server = "0003";

	ImageThread imageThread = new ImageThread();
	RemoteClient remoteClient = new RemoteClient();
	JsonParser jsonParser = new JsonParser();
	private Set<ScreenHandler> screenHandlerSet = new HashSet<ScreenHandler>();
	private CopyOnWriteArraySet<String> clientIdSet = new CopyOnWriteArraySet<String>();
	private String serverId = null;

	private String userId = "";

	RemoteConnector connector;
	ConnectData connectData;

	public RemoteModule() {
		init();
	}

	// public boolean start(String address, int port) {
	// ConnectData cd = new ConnectData();
	// cd.setAddress(address);
	// cd.setPort(port);
	// return connector.connect(cd);
	// }

	public void setConnectData(ConnectData connectData) {
		this.connectData = connectData;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	public String getServerId() {
		return serverId;
	}

	public boolean hasServerId() {
		return null != serverId && !"".equals(serverId);
	}

	public boolean isStart() {
		return connector.isConnected();
	}

	public boolean doStart() {
		return connector.connect(connectData);
	}

	public boolean hasConnectData() {
		return null != connectData;
	}

	public void addClientId(String clientId) {
		this.clientIdSet.add(clientId);
	}
	
	public void removeClientId(String clientId) {
		this.clientIdSet.remove(clientId);
	}

	public boolean hasClientId(String serverId) {
		return !clientIdSet.isEmpty();
	}

	private void init() {

		connector = new RemoteConnector(new IoHandlerAdapter() {
			JsonParser jp = new JsonParser();

			@Override
			public void sessionOpened(IoSession session) throws Exception {
				Map<String, Object> bodyMap = new HashMap<String, Object>();
				bodyMap.put("userId", userId);
				bodyMap.put("action", action_auth);
				String json = OnlyJsonUtil.objectToJson(bodyMap);
				BytesData bd = new BytesData();
				bd.setMessage(json);
				connector.write(bd);
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
			}

//			byte[] tempData=null;
//			private byte[] get(byte[] data){
//				if(null==tempData){
//					tempData=data;
//					return data;
//				}else{
//					int tl=tempData.length;
//					int l=data.length;
//					byte[] bytes=new byte[l];
//					for(int i=0;i<l;i++){
//						if(i<tl){
//							if(tempData[i]!=data[i]){
//								bytes[i]=data[i];
//							}else{
//								bytes[i]=tempData[i];
//							}
//						}else{
//							bytes[i]=data[i];
//						}
//					}
//					tempData=bytes;
//					return bytes;
//				}
//			}
			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				if (message instanceof BytesData) {
					BytesData bd = (BytesData) message;
					String json = bd.getMessage();
					if (JSONUtils.mayBeJSON(json)) {
						JsonObject jo = jp.parse(json).getAsJsonObject();
						boolean hasAction = jo.has("action");
						if (hasAction) {
							String action = jo.get("action").getAsString();
							if (action_auth.equals(action)) {

							}

							if (action_client.equals(action)) {
								// String receiveId = jo.has("receiveId") ?
								// jo.get("receiveId").getAsString() : "";
								String sendId = jo.has("sendId") ? jo.get("sendId").getAsString() : "";
								byte[] bytes = bd.getBytes();
								bytes =OnlyZipUtil.unZip(bytes);
								//bytes= get(bytes);
								for (ScreenHandler sh : screenHandlerSet) {
									sh.handle(sendId, bytes);
								}
							}
							if (action_server.equals(action)) {
								EventData event = OnlyJsonUtil.getParameterValue("event", jo, EventData.class);
								handle(event);
							}
						}
					}
				}
			}
		});
		imageThread.start();
	}

	public void addHandler(ScreenHandler screenHandler) {
		screenHandlerSet.add(screenHandler);
	}

	public void sendEvent(String receiveId, EventData event) {
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("receiveId", receiveId);
		bodyMap.put("sendId", userId);
		bodyMap.put("action", action_server);
		bodyMap.put("event", event);
		String json = OnlyJsonUtil.objectToJson(bodyMap);
		BytesData bd = new BytesData();
		bd.setMessage(json);
		connector.write(bd);
	}

	private void handle(EventData eventData) {
		if (null != eventData) {
			handleEvent(eventData);
		}
	}

	/*-事件处理-*/
	private void handleEvent(EventData event) {

		switch (event.getId()) {
		case MouseEvent.MOUSE_MOVED: // 鼠标移动
			remoteClient.mouseMove(event.getMouseX(), event.getMouseY());
			break;

		case MouseEvent.MOUSE_PRESSED: // 鼠标键按下
			remoteClient.mousePress(event.getMouseButton());
			break;

		case MouseEvent.MOUSE_RELEASED: // 鼠标键松开
			remoteClient.mouseRelease(event.getMouseButton());
			break;

		case MouseEvent.MOUSE_WHEEL: // 鼠标滚动
			remoteClient.mouseWheel(event.getMouseRotation());
			break;

		case MouseEvent.MOUSE_DRAGGED: // 鼠标拖拽
			remoteClient.mouseMove(event.getMouseX(), event.getMouseY());
			break;

		case KeyEvent.KEY_PRESSED: // 按键
			remoteClient.keyPress(event.getKeyCode());
			break;

		case KeyEvent.KEY_RELEASED: // 松键
			remoteClient.keyRelease(event.getKeyCode());
			break;

		default:
			break;
		}
	}

	/*
	 * 鼠标按键翻译
	 */
	// private int getMouseClick(int button) {
	// if (button == MouseEvent.BUTTON1) //左键 ,中间键为BUTTON2
	// return InputEvent.BUTTON1_MASK;
	// if (button == MouseEvent.BUTTON3) //右键
	// return InputEvent.BUTTON3_MASK;
	// return -100;
	// }

	// /*
	// * 鼠标单击
	// */
	// public void mouseClick(int mousebuttonmask) {
	// action.mousePress(mousebuttonmask);
	// action.mouseRelease(mousebuttonmask);
	// }

	class ImageThread extends Thread {

		byte[] tempData=null;
		
		@Override
		public void run() {
			while (true) {
				createImage();
				threadSleep(35);
			}
		}

		private void threadSleep(long time) {
			try {
				sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		private byte[] get(byte[] data){
//			if(null==tempData){
//				tempData=data;
//				return data;
//			}else{
//				int tl=tempData.length;
//				int l=data.length;
//				//int tempSize=(tl-1);
//				byte[] bytes=new byte[l];
//				for(int i=0;i<l;i++){
//					if(i<tl){
//						if(tempData[i]!=data[i]){
//							bytes[i]=data[i];
//						}
//					}else{
//						bytes[i]=data[i];
//					}
//				}
//				tempData=data;
//				return bytes;
//			}
//		}

		public void createImage() {

			if ((null == serverId || "".equals(serverId)) && clientIdSet.isEmpty()) {
				if (connector.isConnected()) {
					connector.closeConnect();
					tempData=null;
				}
				threadSleep(1000);
			} else {

				if (!isStart() && hasConnectData()) {
					doStart();
				}
				if (connector.isConnected() && serverId != null && !"".equals(serverId)) {
					try {
						byte[] data = remoteClient.getScreenBytes();
						//data= get(data);
						data =OnlyZipUtil.zip(data);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("receiveId", serverId);
						map.put("sendId", userId);
						map.put("action", action_client);

						String json = OnlyJsonUtil.objectToJson(map);
						BytesData bd = new BytesData();
						bd.setMessage(json);
						bd.setBytes(data);
						connector.write(bd);

					} catch (Exception e) {
					}
				} else {
					threadSleep(1000);
				}
			}
		}
	}
}
