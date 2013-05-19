package com.eli.web;

import com.eli.util.Config;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/21/12
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpServer {
    private ServerBootstrap bootstrap;
    private ChannelGroup allChannels = new DefaultChannelGroup();
    public void run() {
        // Configure the server.
        bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        ExecutionHandler executionHandler = new ExecutionHandler(
                new OrderedMemoryAwareThreadPoolExecutor(Config.THREAD_NUM, Config.MEM_LIMIT, Config.MEM_LIMIT));
        // Set up the event pipeline factory.
        bootstrap.setPipelineFactory(new PipelineFactory(executionHandler));

        // Bind and start to accept incoming connections.
        allChannels.add(bootstrap.bind(new InetSocketAddress(Config.PORT)));
    }

    public void stop(){
        allChannels.close().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }

    public static void main(String[] args) {
        new HttpServer().run();
    }
}
