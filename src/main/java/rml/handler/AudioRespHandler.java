package rml.handler;

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import jersey.repackaged.com.google.common.collect.Maps;
import rml.model.User;
import rml.server.HouseKeeper;
import rml.util.JsonMapper;

import java.util.List;
import java.util.Map;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/19 22:40
 */
public class AudioRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message= (String) msg;
        JsonNode jsonNode= JsonMapper.getInstance().readValue(message, JsonNode.class);
        String token= jsonNode.get("sessionid").asText();
        if("audio".equals(jsonNode.get("type").asText())){
            Map<String,String> audioList=null;
            if("send".equals(jsonNode.get("action").asText()))
                audioList=HouseKeeper.getAudioMapSend(ctx.channel().localAddress().toString());
            else if("receive".equals(jsonNode.get("action").asText()))
                audioList=HouseKeeper.getAudioMapReceive(ctx.channel().localAddress().toString());
            else
                audioList = Maps.newConcurrentMap();
            User user=HouseKeeper.getCurrentUser(ctx.channel().localAddress().toString(),token);
            String userStr=JsonMapper.getInstance().toJson(user);
            String pre=message.substring(0,message.length()-1);
            String newMsg=pre+",\"user\":"+userStr+"}";
            audioList.put(token,newMsg);
            notifyAllUser(ctx.channel().localAddress().toString(), token, newMsg);
        }else {
            ctx.fireChannelRead(msg);
        }


    }

    private void notifyAllUser(String s, String token, String newMsg) {
        Map<String,User> sessions= HouseKeeper.getSessions(s);
        for(User user:sessions.values()){
            if(user.getToken().equals(token)){
                continue;
            }
            user.getNettyChannel().writeAndFlush(newMsg);
        }
    }
}
