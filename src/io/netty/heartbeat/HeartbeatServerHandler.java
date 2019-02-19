package io.netty.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

	//定义要发送的内容。unreleasable:忽略释放，保持可调用
	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8));
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		//判断是否为IdleStateEvent事件
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String type = "";
			if(event.state() == IdleState.READER_IDLE) {
				type = "read idle";
			}else if(event.state() == IdleState.WRITER_IDLE) {
				type = "write idle";
			}else if(event.state() == IdleState.ALL_IDLE) {
				type = "all idle";
			}
			ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			System.out.println(ctx.channel().remoteAddress() + "超时：" + type);
		}else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
