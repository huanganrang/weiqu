package rml.handler;



import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import rml.model.User;
import rml.server.HouseKeeper;
import rml.service.UserServiceI;
import rml.util.JsonMapper;
import rml.util.SpringContextsUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/14 11:15
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message= (String) msg;
        JsonNode jsonNode= JsonMapper.getInstance().readValue(message, JsonNode.class);
        String token= jsonNode.get("sessionid").asText();
        if("login".equals(jsonNode.get("type").asText())){
            User user= HouseKeeper.getCurrentUser(ctx.channel().localAddress().toString(),token);
            if(user!=null){
                //TODO 重复登陆处理
            }else{
                UserServiceI userService= (UserServiceI) SpringContextsUtil.getBean("userService");
                user=userService.selectByToken(token);
                if(user==null){
                    String ret="{\"type\":\"login\",\"result\":\"fail\"}";
                    ctx.writeAndFlush(ret);
                }else{
                    user.setNettyChannel(ctx.channel());
                    HouseKeeper.login(user);
                    String ret="{\"type\":\"login\",\"result\":\"success\"}";
                    ctx.writeAndFlush(ret);
                }
            }
        }else {
            if(!HouseKeeper.getSessions(ctx.channel().localAddress().toString()).containsKey(token)){//未登陆
                ctx.writeAndFlush("{\"type\":\"login\",\"result\":\"NotLogin\"}");//未登陆，返回登陆失败
            }else{
                ctx.fireChannelRead(msg);
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        HouseKeeper.logout(ctx.channel());//session断开连接
    }
}
