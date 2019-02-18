package io.netty.example.echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	 
	private final String host;
	private final int port;
	
	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();				//����bootstrap
			b.group(group)								//ָ��EventLoopGroup�Դ���ͻ����¼�����Ҫ������NIO��ʵ��
			.channel(NioSocketChannel.class)			//������NIO�����Channel����
			.remoteAddress(new InetSocketAddress(host, port))			//���÷�������InetSocketAddress
			.handler(new ChannelInitializer<SocketChannel>() {			//�ڴ���Channelʱ����ChannelPipeline�����һ��EchoClientHandlerʵ��
 
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new EchoClientHandler());
				}
			});
			ChannelFuture f = b.connect().sync();		//���ӵ�Զ�̽ڵ㣬�����ȴ�ֱ���������
			f.channel().closeFuture().sync();			//����ֱ��Channel�ر�
		} finally {	
			group.shutdownGracefully().sync();			//�ر��̳߳أ������ͷ����е���Դ
		}
	}
	
	public static void main(String[] args) throws Exception {
		if(args.length != 2) {
			System.out.println("Usage:" + EchoClient.class.getSimpleName() + "<host><port>");
			return;
		}
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		new EchoClient(host, port).start();
	}

}
