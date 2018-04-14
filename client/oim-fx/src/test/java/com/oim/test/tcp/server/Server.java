package com.oim.test.tcp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

//import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oim.core.net.connect.codec.DataCodecFactory;

/**
 *
 * @author XiaHui
 * @date 2014年6月14日 下午10:52:40
 * @version 0.0.1
 */
public class Server {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int port_object = 13000;

    private NioSocketAcceptor acceptor;
    private boolean running = false;

    public boolean startServer() {
        return startServer(port_object);
    }

    public synchronized boolean startServer(int port) {
        if (running) {
            return running;
        }
        try {

            acceptor = new NioSocketAcceptor();
            acceptor.addListener(new ServerListener());
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new DataCodecFactory()));
            acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
            acceptor.getSessionConfig().setReadBufferSize(2048); // 设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);// 读写通道10秒内无操作进入空闲状态
            acceptor.setHandler(new ServerHandler()); // 绑定逻辑处理起器
            acceptor.bind(new InetSocketAddress(port));// 绑定端口
            acceptor.setReuseAddress(true);
            logger.info("服务器启动成功。。。。端口为：" + port);
            return running = true;
        } catch (IOException e) {
            logger.error("服务器启动异常。。。。", e);
            e.printStackTrace();
            running = false;
        }
        return running = false;
    }

    public boolean stopServer() {
        synchronized (this) {
            if (running) {
                try {
                    acceptor.unbind();
                    acceptor.dispose();
                    logger.debug("Server is stoped.");
                    running = false;
                    logger.info("服务器停止成功。。。。端口为：" + port_object);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
