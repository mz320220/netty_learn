package io.netty.example.echo;

import java.util.Scanner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


@Sharable				//��Ǹ����ʵ�����Ա����Channel����
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
 
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Scanner sc = new Scanner(System.in);
        System.out.println("�����룺"); 
		String msg = sc.nextLine();
		//ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));	//����֪ͨChannel�ǻ�Ծ��ʱ�򣬷���һ����Ϣ
		ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("Client received: " + msg.toString(CharsetUtil.UTF_8));		//��¼�ѽ�����Ϣת��
	}
	
	//�ڷ����쳣ʱ����¼���󲢹ر�Channel
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
 

}
