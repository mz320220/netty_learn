package io.netty.example.echo;

import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


@Sharable		
//标示一个ChannelHandler可以被多个Channel安全的共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
 
    private AtomicInteger integer = new AtomicInteger(0);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(integer.incrementAndGet());

		ByteBuf in = (ByteBuf) msg;
		//将消息记录到控制台
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);	//将接收到的消息写给发送者，而不冲刷出站消息
	}
 
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//将为决消息冲刷到远程节点，并且关闭该Channel:把消息冲刷到client端
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();		//打印异常栈跟踪
		ctx.close();					//关闭该Channel
	}
 
}

