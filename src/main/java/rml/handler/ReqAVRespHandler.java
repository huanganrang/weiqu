package rml.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.util.Lists;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import rml.server.HouseKeeper;
import rml.util.JsonMapper;

import java.util.List;
import java.util.Map;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/19 23:01
 */
public class ReqAVRespHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message= (String) msg;
        JsonNode jsonNode= JsonMapper.getInstance().readValue(message, JsonNode.class);
        String token= jsonNode.get("sessionid").asText();
        if("request".equals(jsonNode.get("type").asText())){
            Map<String,String> audioMapSend=HouseKeeper.getAudioMapSend(ctx.channel().localAddress().toString());
            Map<String,String> audioMapReceive=HouseKeeper.getAudioMapReceive(ctx.channel().localAddress().toString());
            Map<String,String> videoMapSend=HouseKeeper.getVideoMapSend(ctx.channel().localAddress().toString());
            Map<String,String> videoMapReceive=HouseKeeper.getVideoMapReceive(ctx.channel().localAddress().toString());
            List<String> retList= Lists.newArrayList();
            retList.addAll(audioMapSend.values());
            retList.addAll(audioMapReceive.values());
            retList.addAll(videoMapSend.values());
            retList.addAll(videoMapReceive.values());
            ctx.writeAndFlush(JsonMapper.getInstance().toJson(retList));
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
