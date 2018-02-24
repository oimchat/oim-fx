package com.main;

import org.springframework.boot.SpringApplication;

import com.im.base.common.box.ConfigBox;
import com.im.server.general.remote.socket.netty.tcp.RemoteSocketServer;
import com.im.socket.Server;
import com.im.socket.netty.tcp.SocketServer;
import com.im.socket.netty.web.WebSocketServer;
import com.only.common.util.OnlyPropertiesUtil;
import com.startup.boot.BootApplication;

/**
 * 服务器启动入口
 * 
 * @author XiaHui
 * @date 2017-11-25 14:08:50
 *
 */
public final class StartupServer {

	public static void main(String[] args) {
		initConfig();
		SpringApplication.run(BootApplication.class, args);
	}

	public static void initConfig() {

		System.setProperty("log4j.configurationFile", "config/log4j2.xml");
		System.setProperty("log4j.configuration", "file:config/log4j.properties");

		String category = "";
		String key = "";
		String value = "";

		/**
		 * 文件服务相关配置,这些配置是用来启动项目的时候需要的配置
		 */
		/*************** config start ***********************/
		category = "server.config.file";

		key = "server.config.file.upload.path";
		value = OnlyPropertiesUtil.getProperty("config/setting/config.properties", key);
		ConfigBox.put(category, key, value);

		key = "server.config.file.request.path";
		value = OnlyPropertiesUtil.getProperty("config/setting/config.properties", key);
		ConfigBox.put(category, key, value);
		/*************** config end ***********************/

		/**
		 * 这里的配置是用来通知客户端，相关服务的地址在哪。需要参考config.properties配置文件相应配置
		 */
		/*************** path start ***********************/
		category = "server.path.im";
		// 聊天服务地址
		key = "im.server.tcp.address";
		value = OnlyPropertiesUtil.getProperty("config/setting/path.properties", key);
		ConfigBox.put(category, key, value);
		// 聊天服务端口
		key = "im.server.tcp.port";
		value = OnlyPropertiesUtil.getProperty("config/setting/path.properties", key);
		ConfigBox.put(category, key, value);
		// websocket地址
		key = "im.server.websocket.path";
		value = OnlyPropertiesUtil.getProperty("config/setting/path.properties", key);
		ConfigBox.put(category, key, value);
		// 聊天服务http地址
		key = "im.server.http.url";
		value = OnlyPropertiesUtil.getProperty("config/setting/path.properties", key);
		ConfigBox.put(category, key, value);

		/**
		 * 远程服务
		 */
		category = "server.path.remote";
		// 远程服务地址
		key = "remote.server.tcp.address";
		value = OnlyPropertiesUtil.getProperty("config/setting/path.properties", key);
		ConfigBox.put(category, key, value);
		// 远程服务端口
		key = "remote.server.tcp.port";
		value = OnlyPropertiesUtil.getProperty("config/setting/path.properties", key);
		ConfigBox.put(category, key, value);

		/**
		 * 文件服务地址
		 */
		category = "server.path.file";
		key = "file.server.http.url";
		value = OnlyPropertiesUtil.getProperty("config/setting/path.properties", key);
		ConfigBox.put(category, key, value);
		/*************** path end ***********************/

		/////////////////////////////////////////////////////
		// 获取服务启动配置
		key = "server.config.im.tcp.port";
		String tcpPortValue = OnlyPropertiesUtil.getProperty("config/setting/config.properties", key);

		key = "server.config.im.websocket.port";
		String websocketPortValue = OnlyPropertiesUtil.getProperty("config/setting/config.properties", key);

		key = "server.config.im.websocket.path";
		String websocketPath = OnlyPropertiesUtil.getProperty("config/setting/config.properties", key);

		key = "server.config.remote.tcp.port";
		String remoteTcpPortValue = OnlyPropertiesUtil.getProperty("config/setting/config.properties", key);

		int tcpPort = Integer.parseInt(tcpPortValue);
		int websocketPort = Integer.parseInt(websocketPortValue);
		int remoteTcpPort = Integer.parseInt(remoteTcpPortValue);

		SocketServer socketServer = new SocketServer();
		WebSocketServer webSocketServer = new WebSocketServer(websocketPath);
		RemoteSocketServer remoteSocketServer = new RemoteSocketServer();

		socketServer.setPort(tcpPort);
		webSocketServer.setPort(websocketPort);
		remoteSocketServer.setPort(remoteTcpPort);

		new StartThread(socketServer).start();
		new StartThread(webSocketServer).start();
		new StartThread(remoteSocketServer).start();
	}

	static class StartThread extends Thread {

		private Server server;

		public StartThread(Server server) {
			this.server = server;
		}

		public void run() {
			server.start();
		}
	}
}
