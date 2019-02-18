package io.netty.example.echo;

import java.util.Scanner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


@Sharable				//标记该类的实例可以被多个Channel共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
 
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Scanner sc = new Scanner(System.in);
        System.out.println("请输入："); 
		String msg = sc.nextLine();
		//ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));	//当被通知Channel是活跃的时候，发送一条消息
		ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("Client received: " + msg.toString(CharsetUtil.UTF_8));		//记录已接收消息转储
	}
	
	//在发生异常时。记录错误并关闭Channel
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
 

}
