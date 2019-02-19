package io.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		//channels:对新接入的client进行broadcast
		channels.writeAndFlush("[server] - " + incoming.remoteAddress() + "加入\n");
		channels.add(incoming);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		//channels:对离开的client进行broadcast
		channels.writeAndFlush("[server] - " + incoming.remoteAddress() + "离开\n");
		//关闭的channel会自动从channelgroup中移除，无需手动处理。
	}
	
	@Override
	//每条msg信息都会调用该方法
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel incoming = ctx.channel();
		for(Channel channel : channels) {
			if(channel != incoming) {
				channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
			}else {
				channel.writeAndFlush("[youself:]" + msg + "\n");
			}
		}
	}
	
	@Override
	//channel接入server后，做好准备可以进行通信会调用一次：
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[client]" + incoming.remoteAddress() + "在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[client]" + incoming.remoteAddress() + "掉线");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[client]" + incoming.remoteAddress() + "异常");
		cause.printStackTrace();
		ctx.close();
	}

}
