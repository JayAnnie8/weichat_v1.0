package com.imooc.netty;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author Jayyan
 *项目名称：自定义对用户传入数据进行处理的handler类
 *类名称：
 *类描述:TextWebSocketFrame,在netty中，是为websocket专门处理文本的对象，frame是消息的载体
 *创建时间:2019年4月9日下午10:35:35
 * @version
 *TODO：
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
	
	//channelGroup用于对信息转发给所有在同一group内channel的对象进行信息转发
		private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) 
			throws Exception {
		//获取客户端传输过来的一串字符串,消息
		String content=msg.text();
		//打印msg的信息内容
		System.out.println(content);
		//有两种回传的方式一种是自己定义循环，一种是利用channelgroup的定义好的writeAndFlush的方式
		TextWebSocketFrame remsg=new TextWebSocketFrame("[服务器返回的信息]\n"+"时间："+LocalDateTime.now()+"\n返回信息为："+msg.text());
		//方式1利用循环的方式使得channel背遍历然后发送信息
		/*
		 * for(Channel channel:clients)
		  {
			channel.writeAndFlush(remsg);
		  }
		*/
		//利用channelgroup本身的writeAndFlush有向全部channel推送信息的作用实现
		clients.writeAndFlush(remsg);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		/*
		 * 当客户端连接服务器之后
		 * 获取客户端的channdle，然后保存到channelgroup中管理
		 */
		clients.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//clients.remove(ctx.channel()); 当handlerRomoved的时候channelgroup会自动的把该channel移除即自动执行了channelgroup.remove()
		System.out.println("客户端断开，channel的长id是唯一标识的："+ctx.channel().id().asLongText());
		System.out.println("客户端断开，channel的短id当数据量比较大的时候可能会重复："+ctx.channel().id().asShortText());
	}

}
