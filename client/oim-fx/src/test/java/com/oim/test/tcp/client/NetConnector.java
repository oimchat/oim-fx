/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.tcp.client;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.oim.core.net.connect.codec.DataCodecFactory;
import com.only.net.connect.ConnectData;

/**
 * date 2012-9-14 17:40:54
 *
 * @author XiaHui
 */
public class NetConnector {

    private IoSession session;
    private IoConnector ioConnector;
    private IoHandlerAdapter handler;
    private int port = 0;

    public NetConnector(IoHandlerAdapter handler) {
        this.handler = handler;
        initConnector();
    }

    private void initConnector() {
        ioConnector = new NioSocketConnector();
        ioConnector.getFilterChain().addLast("mis", new ProtocolCodecFilter(new DataCodecFactory()));// 添加过滤器
        ioConnector.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        ioConnector.setHandler(handler);// 添加业务逻辑处理类
    }

    public void setDefaultPort(int port) {
        try {
            ioConnector.setDefaultLocalAddress(new InetSocketAddress( InetAddress.getLocalHost().getHostAddress(), port));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean connect(ConnectData connectData) {
        boolean mark = true;
        try {
            ConnectFuture connect = ioConnector.connect(new InetSocketAddress(connectData.getAddress(), connectData.getPort()));// 创建连接
            connect.awaitUninterruptibly(connectData.getTimeOut());// 30000//
            session = connect.getSession();// 获取session
            mark = null != session;
            SocketAddress socketAddress = ioConnector.getDefaultLocalAddress();
            if (socketAddress instanceof InetSocketAddress) {
                InetSocketAddress isa = (InetSocketAddress) socketAddress;
                port = isa.getPort();
            }
            System.out.println(ioConnector.getDefaultLocalAddress());
        } catch (Exception e) {
            e.printStackTrace();
            mark = false;
        }
        return mark;
    }

    public IoSession getSession() {
        return session;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConnected() {
        return (null != session && session.isConnected() && !session.isClosing());
    }

    public void closeConnect() {
        if (null != session) {
            session.closeNow();
            session = null;
        }
    }

    public boolean write(String text) {
        boolean mark = isConnected();
        if (mark) {
            session.write(text);
        }
        return mark;
    }

}
