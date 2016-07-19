package rml.handler;

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import rml.model.User;
import rml.server.HouseKeeper;
import rml.util.JsonMapper;

import java.util.List;
import java.util.Map;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/19 22:41
 */
public class VideoRespHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message= (String) msg;
        JsonNode jsonNode= JsonMapper.getInstance().readValue(message, JsonNode.class);
        String token= jsonNode.get("sessionid").asText();
        if("video".equals(jsonNode.get("type").asText())){
            Map<String,String> videoList= HouseKeeper.getVideoMap(ctx.channel().localAddress().toString());
            User user=HouseKeeper.getCurrentUser(ctx.channel().localAddress().toString(),token);
            String userStr=JsonMapper.getInstance().toJson(user);
            String pre=message.substring(0,message.length()-1);
            String newMsg=pre+",\"user\":"+userStr+"}";
            videoList.put(token,newMsg);
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
