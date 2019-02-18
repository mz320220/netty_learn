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
//��ʾһ��ChannelHandler���Ա����Channel��ȫ�Ĺ���
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
 
    private AtomicInteger integer = new AtomicInteger(0);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(integer.incrementAndGet());

		ByteBuf in = (ByteBuf) msg;
		//����Ϣ��¼������̨
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);	//�����յ�����Ϣд�������ߣ�������ˢ��վ��Ϣ
	}
 
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//��Ϊ����Ϣ��ˢ��Զ�̽ڵ㣬���ҹرո�Channel:����Ϣ��ˢ��client��
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();		//��ӡ�쳣ջ����
		ctx.close();					//�رո�Channel
	}
 
}

