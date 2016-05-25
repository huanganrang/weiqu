package rml.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import rml.model.Channel;
import rml.model.File;
import rml.model.User;
import rml.service.ChannelServiceI;
import rml.service.UserServiceI;
import rml.util.ReturnJson;
import rml.util.SerializeUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */


@Controller
@RequestMapping("/Channel")
public class ChannelController {

    static String constr =  "10.174.139.99" ;

    private String destUrl = "file.weiqu168.com/";

    Jedis jedis = new Jedis(constr) ;

    @Autowired
    UserServiceI userService;

    @Autowired
    ChannelServiceI  channelService;

    @RequestMapping(value="/Channel",method = RequestMethod.POST)
    @ResponseBody
    public Object createChannel(@RequestBody Channel channel){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(8000);
        returnJson.setReturnMessage("调用成功" + channel.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(channel.getName())||StringUtils.isEmpty(channel.getChannelIcon())||StringUtils.isEmpty(channel.getShortDesc())||channel.getCategoryId()==0||StringUtils.isEmpty(channel.getUserToken())){
            System.err.println(channel.toString());
            returnJson.setErrorCode(8002);
            returnJson.setReturnMessage("传入参数为空" + channel.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        channel.setDisplayIconUrl(channel.getChannelIcon());
        User user = null;
        try{
            user = userService.selectByToken(channel.getUserToken());
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(8001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        if(user==null){
            returnJson.setErrorCode(8003);
            returnJson.setReturnMessage("userToken不存在"+channel);
            returnJson.setServerStatus(1);

        }
        Channel channel1 = null;
        try{
            channel.setUserId(user.getId());
            channel1 = channelService.createChannel(channel);
        } catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(8001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(channel1);
        return returnJson;
    }


    @RequestMapping(value="/Page",method = RequestMethod.GET)
    @ResponseBody
    public Object getPage(Channel channel){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(37000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(channel.getPageNo()==0){
            returnJson.setErrorCode(37002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(1);
            return returnJson;
        }
       List< Channel> channels = null;
        try{
            channels = channelService.getChannelPage(channel);
        } catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(37001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnValue(new Integer(channelService.count()).toString());
        returnJson.setReturnObject(channels);
        return returnJson;
    }

    @RequestMapping(value="/Houses",method = RequestMethod.GET)
    @ResponseBody
    public Object getHouses(Channel channel){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(37000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        List< Channel> channels = null;
        try{
            channels = channelService.getChannelPage(channel);
        } catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(37001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnValue(new Integer(channelService.count()).toString());
        returnJson.setReturnObject(channels);
        return returnJson;
    }

    public static void main(String[]args){

        String constr = "139.196.12.99" ;

        Jedis jedis = new Jedis(constr) ;

        rml.model.File file = (File)SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize("84a5950d-8fb6-4fe6-be77-041f04568cb1")));
        System.err.println(file.getGroupName());
        System.err.println(file.getRemoteFileName());
    }
}

