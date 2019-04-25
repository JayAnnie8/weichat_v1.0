
package com.imooc.netty;

import java.util.MissingFormatArgumentException;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class WSserver_start {
	private static class SingletionWSserver {
		static final WSserver_start instance=new WSserver_start();
	}
	public static WSserver_start getInstance() {
		return SingletionWSserver.instance;
	}
	private EventLoopGroup mainGroup;
	private EventLoopGroup subGroup;;		 
	private ServerBootstrap serverBootstrap;
	private ChannelFuture future;
	public WSserver_start() {
		mainGroup=new NioEventLoopGroup();
		subGroup=new NioEventLoopGroup();
		serverBootstrap=new ServerBootstrap();
		serverBootstrap.group(mainGroup,subGroup)
						.channel(NioServerSocketChannel.class)
						.childHandler(new WSServerInitialzer());
	}
	public void start() {
		this.future=serverBootstrap.bind(8088);
		System.err.println("netty websocket server 启动完毕...");
	}	
}
