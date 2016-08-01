package rml.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import rml.model.Message;

import java.nio.charset.Charset;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/30 23:12
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {
    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        Message message = new Message();
        message.setHead(frame.readByte());
        message.setLength(frame.readInt());
        byte[] data=new byte[message.getLength()-1];
        frame.readBytes(data);
        message.setData(new String(data, Charset.forName("UTF-8")));
        message.setTail(frame.readByte());
        return message;
    }
}
