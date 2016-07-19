package rml.server;

import com.google.api.client.util.Lists;
import io.netty.channel.Channel;
import jersey.repackaged.com.google.common.collect.Maps;
import org.springframework.util.SocketUtils;
import rml.model.User;
import sun.nio.ch.Net;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/19 14:36
 */
public class HouseKeeper {
    public static Map<String,NettyServer> houseServer= Maps.newConcurrentMap();//
    public static Map<String,Map<String,Object>> serverData=Maps.newConcurrentMap();//
    public static Queue<NettyServer> uriServer= new ConcurrentLinkedDeque<NettyServer>();//回收缓存
    public static NettyServer getServer(String token){
        NettyServer nettyServer=houseServer.get(token);
        if(nettyServer==null){
            if(uriServer.isEmpty()){
                nettyServer=initServer();
                Map<String,Object> shareData=Maps.newConcurrentMap();
                serverData.put(nettyServer.getUrl(),shareData);//初始化server共享缓存
            }else {
                nettyServer=uriServer.poll();
            }
            houseServer.put(token,nettyServer);
            Map<String,User> session=Maps.newConcurrentMap();
            Map<String,String> channelBind=Maps.newConcurrentMap();
            Map<String,String> audioMap=Maps.newConcurrentMap();
            Map<String,String> videoMap=Maps.newConcurrentMap();
            serverData.get(nettyServer.getUrl()).put("session",session);//初始化session容器
            serverData.get(nettyServer.getUrl()).put("channelRelation",channelBind);//初始化channelRelation容器
            serverData.get(nettyServer.getUrl()).put("audioMap",audioMap);//语音列表
            serverData.get(nettyServer.getUrl()).put("videoMap",videoMap);//视频列表
        }
        return nettyServer;
    }
    private static NettyServer initServer(){
        NettyServer  nettyServer=new NettyServer();
        int result= SocketUtils.findAvailableTcpPort();
        nettyServer.setHost("localhost");
        nettyServer.setPort(result);
        try {
            nettyServer.bind();
        } catch (Exception e) {
            nettyServer=initServer();
            e.printStackTrace();
        }
        return nettyServer;
    }
    public static void closeServer(String token){
       NettyServer nettyServer= houseServer.remove(token);
        serverData.get(nettyServer.getUrl()).clear();//清空房间共享数据
        uriServer.add(nettyServer);
    }

    public static Map<String,Object> getServerData(String key){
        return serverData.get(key);
    }

    public static Map<String,User>  getSessions(String serverUri){
        Map<String,Object> serverData=getServerData(serverUri);
        return (Map<String, User>) serverData.get("session");
    }
    public static Map<String,String> getChannelRelation(String serverUri){
        Map<String,Object> serverData=getServerData(serverUri);
        return (Map<String, String>) serverData.get("channelRelation");
    }

    public static Map<String,String> getAudioMap(String serverUri){
        return (Map<String,String>) getServerData(serverUri).get("audioMap");
    }

    public static Map<String,String> getVideoMap(String serverUri){
        return (Map<String,String>) getServerData(serverUri).get("videoMap");
    }

    public static User getCurrentUser(String serverUri,String token){
        return getSessions(serverUri).get(token);
    }
    public static void login(User user){
        getSessions(user.getNettyChannel().localAddress().toString()).put(user.getToken(),user);
        getChannelRelation(user.getNettyChannel().localAddress().toString()).put(user.getNettyChannel().remoteAddress().toString(),user.getToken());
;    }

    public static void logout(Channel channel){
        String token=getChannelRelation(channel.localAddress().toString()).remove(channel.remoteAddress().toString());
        getSessions(channel.localAddress().toString()).remove(token);
        getAudioMap(channel.localAddress().toString()).remove(token);
        getVideoMap(channel.localAddress().toString()).remove(token);
    }
}
