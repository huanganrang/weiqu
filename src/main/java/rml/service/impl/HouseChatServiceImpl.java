package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rml.model.HouseChat;
import rml.service.HouseChatServiceI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/29.
 */
@Service("chatService")
public class HouseChatServiceImpl implements HouseChatServiceI {

    @Autowired
    RedisTemplate jedisTemplate;


    @Override
    public void pushChat(HouseChat chat) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        jedisTemplate.opsForList().leftPush(chat.getHouseId()+" "+chat.getUserId(),chat.getContent()+","+dateString);
    }

    @Override
    public List<HouseChat> pullChat(HouseChat chat) {
       List<String> list = (List<String>)jedisTemplate.opsForList().range(chat.getHouseId()+" "+chat.getUserId(),0,-1);
       List<HouseChat> results = new ArrayList<HouseChat>();
       for(String content:list){
          HouseChat tmpVal = new HouseChat();
           tmpVal.setHouseId(chat.getHouseId());
           tmpVal.setUserId(chat.getUserId());
           tmpVal.setContent(content);
           results.add(tmpVal);
       }
        return results;
    }
}
