package io.netty.example.unixtime;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("TimeDecoder In");
		//������ȡ���ڲ�������С��4��ֱ�ӷ��ز�����
		if(in.readableBytes() < 4) {
			return;
		}
		//�ۼƵ�4�����4�����out��������Ѿ���ȡ�Ļ���:Gets an unsigned 32-bit integer at the current
		out.add(new UnixTime(in.readUnsignedInt()));
	}

}
