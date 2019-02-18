package io.netty.example.time;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//������ȡ���ڲ�������С��4��ֱ�ӷ��ز�����
		if(in.readableBytes() < 4) {
			return;
		}
		//�ۼƵ�4�����4�����out��������Ѿ���ȡ�Ļ���
		out.add(in.readBytes(4));
	}

}
