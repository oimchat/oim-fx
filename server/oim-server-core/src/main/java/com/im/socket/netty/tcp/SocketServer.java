/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.im.socket.netty.tcp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.im.socket.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * @author XiaHui
 */
public final class SocketServer implements Server{

    Logger logger = LogManager.getLogger(SocketServer.class.getName());
    private int port = 12000;
    ServerBootstrap server = new ServerBootstrap();
    boolean start = false;

    public boolean start() {
        if (start) {
            return start;
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            start = true;
            server.group(bossGroup, workerGroup);
            server.channel(NioServerSocketChannel.class);
            server.handler(new LoggingHandler(LogLevel.INFO));
            server.childHandler(new SocketServerInitializer());
            Channel ch = server.bind(port).sync().channel();
            ch.closeFuture().sync();
        } catch (InterruptedException ex) {
            start = false;
            logger.error("WebSocketServer启动失败", ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("WebSocketServer:-----shutdown---------");
        }
        return start;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.start();
    }
}
