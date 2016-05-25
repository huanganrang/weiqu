package rml.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rml.model.RecordClient;
import rml.util.LimitedQueue;
import rml.util.Server;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/21 0021.
 */

/**
 *
 * @author Sergi Almar
 */
@Controller("userChatController")
@RequestMapping("/UserChat")
public class UserChatController {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/message")
    public void filterPrivateMessage(RecordClient client) {
        String houseToken = client.getHouseToken();
        simpMessagingTemplate.convertAndSend("/topic/message/"+houseToken,client);
        Server server = new Server(12345);
        String jsonString = JSONObject.toJSONString(client);
        server.loopServer(houseToken,jsonString);
    }

    @MessageMapping("/participants")
    public void paticipants(RecordClient client) {
        String houseToken = client.getHouseToken();
        Server server = new Server(12346);
        String jsonString = JSONObject.toJSONString(client);
        server.loopServer(houseToken,jsonString);
        simpMessagingTemplate.convertAndSend("/topic/participants/" + houseToken, client);
    }

    @RequestMapping(value="/Socket",method = RequestMethod.GET)
    public void sendSocketMessage(String houseToken,String userToken,String content,String action,int type) {
        RecordClient recordClient = new RecordClient();
        recordClient.setAction(action);
        recordClient.setContent(content);
        recordClient.setHouseToken(houseToken);
        recordClient.setUserToken(userToken);
        recordClient.setType(type);
        simpMessagingTemplate.convertAndSend("/topic/message/" + houseToken, recordClient);
    }

    public static Object getBean(String jsonString, Class clazz){
        JSONObject jsonObject = null;
        try{
            jsonObject = JSONObject.parseObject(jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
        return JSONObject.toJavaObject(jsonObject, clazz);
    }
}




