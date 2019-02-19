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
		//channels:���½����client����broadcast
		channels.writeAndFlush("[server] - " + incoming.remoteAddress() + "����\n");
		channels.add(incoming);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		//channels:���뿪��client����broadcast
		channels.writeAndFlush("[server] - " + incoming.remoteAddress() + "�뿪\n");
		//�رյ�channel���Զ���channelgroup���Ƴ��������ֶ�����
	}
	
	@Override
	//ÿ��msg��Ϣ������ø÷���
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
	//channel����server������׼�����Խ���ͨ�Ż����һ�Σ�
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[client]" + incoming.remoteAddress() + "����");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[client]" + incoming.remoteAddress() + "����");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[client]" + incoming.remoteAddress() + "�쳣");
		cause.printStackTrace();
		ctx.close();
	}

}
