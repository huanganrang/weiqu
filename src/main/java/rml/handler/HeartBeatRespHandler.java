package rml.handler;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.json.Json;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.groovy.GJson;
import net.sf.json.util.JSONBuilder;
import rml.model.User;
import rml.server.HouseKeeper;
import rml.util.JsonMapper;

import java.util.Map;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/14 11:16
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String message = (String) msg;
        JsonNode jsonNode= JsonMapper.getInstance().readValue(message, JsonNode.class);
        if("heart".equals(jsonNode.get("type").asText())){
            Map<String,Object> serverData= HouseKeeper.getServerData(ctx.channel().localAddress().toString());
            Map<String,User> sessions= (Map<String, User>) serverData.get("session");
            String ret="{\"type\":\"member\",\"count\":"+sessions.size()+"}";
            ctx.writeAndFlush(ret);
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
