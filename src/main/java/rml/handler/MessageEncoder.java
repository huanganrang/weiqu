package rml.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import rml.model.Message;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/30 23:26
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getHead());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getData().getBytes("utf-8"));
        out.writeByte(msg.getTail());
    }
}
