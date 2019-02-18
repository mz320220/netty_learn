package io.netty.example.time;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//持续读取，内部缓冲区小于4则直接返回不处理
		if(in.readableBytes() < 4) {
			return;
		}
		//累计到4或大于4输出到out，并清空已经读取的缓存
		out.add(in.readBytes(4));
	}

}
