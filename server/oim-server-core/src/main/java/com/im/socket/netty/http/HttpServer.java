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
package com.im.socket.netty.http;



import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.DispatcherServlet;

import com.im.socket.Server;

/**
 *
 * @author XiaHui
 */
public final class HttpServer implements Server{

	protected final Logger logger = LogManager.getLogger(this.getClass());
    private int port = 8800;
    ServerBootstrap server = new ServerBootstrap();
    boolean start = false;
    DispatcherServlet dispatcherServlet;

    public HttpServer(DispatcherServlet dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }

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
            server.handler(new LoggingHandler(LogLevel.WARN));
            server.childHandler(new HttpServerInitializer(dispatcherServlet));
            Channel ch = server.bind(port).sync().channel();
            ch.closeFuture().sync();
        } catch (InterruptedException ex) {
            start = false;
            logger.error("HttpServer启动失败", ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        return start;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
