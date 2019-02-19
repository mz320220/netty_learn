package io.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleChatServer {
	
	private int port;
	
	public SimpleChatServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.childHandler(new SimpleChatServerInitializer())
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			System.out.println("SimpleChatServer 启动了");
			
			//绑定端口接收连接
			ChannelFuture f = b.bind(port).sync();
			//等待服务器socket关闭(这个例子不会出现~)
			f.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
			System.out.println("SimpleChatServer 关闭了");
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port;
		if(args.length > 0) {
			port = Integer.parseInt(args[0]);
		}else {
			port = 8080;
		}
		new SimpleChatServer(port).run();
	}

}
