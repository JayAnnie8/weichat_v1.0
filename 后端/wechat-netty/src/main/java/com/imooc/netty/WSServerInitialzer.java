package com.imooc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 
 * @author Jayyan
 *项目名称：
 *类名称：childhandler的初始化化器
 *类描述:
 *创建时间:2019年4月15日下午5:29:21
 * @version
 *TODO：
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//获取当期channel管道
		ChannelPipeline pipeline=ch.pipeline();
		
		//ws的编解码器
		//ws服务的初始化化，httpServerCodec因为websocket的开头报文需要用到http协议
		pipeline.addLast(new HttpServerCodec());
		//文件读写的handler
		pipeline.addLast(new ChunkedWriteHandler());
		//传输信息的最大值1024*64把fullhttpresponse聚合在一起  实质把httpmessage进行聚合
		//所有的大数据传输都需要用到httpobjectaggregator定义一个内存块所有的操作都通过该内存块进行
		//netty要进行大数据传输使用的对象 httpobjectaggregator
		pipeline.addLast(new HttpObjectAggregator(1024*64));
		
		//============以上是用于支持http协议的handler==============
		
		//============开始对websocket的支持进行设置================
		/*
		 * websocket服务器处理的协议，用于指定给客户端连接访问的路由：/ws
		 * 本handler会帮你处理一些繁重的的复杂的是
		 * 会帮你处理握手动作：handshaking(close,ping,pong)ping+pong=心跳用于请求与发送
		 * 对于websocket来说，都是以frames进行传输的，不同类型的frames也不同
		 * */
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		
		pipeline.addLast(new ChatHandler());
	}
	

}
