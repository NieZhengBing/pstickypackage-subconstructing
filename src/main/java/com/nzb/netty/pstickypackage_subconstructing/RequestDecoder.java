package com.nzb.netty.pstickypackage_subconstructing;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.omg.CORBA.Request;

public class RequestDecoder extends FrameDecoder {

	public static int BASE_LENGTH = 4 + 2 + 2 + 4;

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1, ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() >= BASE_LENGTH) {
			if (buffer.readableBytes() > 2048) {
				buffer.skipBytes(buffer.readableBytes());
			}
			int beginReader;
			while (true) {
				beginReader = buffer.readerIndex();
				buffer.markReaderIndex();
				if (buffer.readInt() == ConstantValue.FLAG) {
					break;
				}
				buffer.resetReaderIndex();
				buffer.readByte();

				if (buffer.readableBytes() < BASE_LENGTH) {
					return null;
				}
			}
			short module = buffer.readShort();
			short cmd = buffer.readShort();
			int length = buffer.readInt();

			if (buffer.readableBytes() < length) {
				buffer.readerIndex(beginReader);
				return null;
			}
			byte[] data = new byte[length];
			buffer.readBytes(data);

			Request request = new Request();

			return request;
		}
		return null;
	}

}
