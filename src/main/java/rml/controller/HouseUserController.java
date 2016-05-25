package rml.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.httpclient.apidemo.EasemobChatGroups;
import rml.httpclient.utils.HTTPClientUtils;
import rml.model.House;
import rml.model.HouseUser;
import rml.model.RecordClient;
import rml.model.User;
import rml.service.ChannelServiceI;
import rml.service.HouseServiceI;
import rml.service.HouseUserServiceI;
import rml.service.UserServiceI;
import rml.util.HTTPMethod;
import rml.util.ReturnJson;
import rml.util.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/26.
 */

@Controller
@RequestMapping("/HouseUser")
public class HouseUserController {

    @Autowired
    private HouseUserServiceI houseUserService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private HouseServiceI houseService;

    @Autowired
    private ChannelServiceI channelService;


    @RequestMapping(value="/HouseUser",method = RequestMethod.POST)
    @ResponseBody
    public Object insertUser(@RequestBody HouseUser houseUser){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(9000);
        returnJson.setReturnMessage("调用成功" + houseUser.toString());
        returnJson.setServerStatus(0);
        if(houseUser.getIdentification()==0||StringUtils.isEmpty(houseUser.getUserToken())||StringUtils.isEmpty(houseUser.getHouseToken())){
            returnJson.setErrorCode(9002);
            returnJson.setReturnMessage("传入参数为空" + houseUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = null;
        try{
          user = userService.selectByToken(houseUser.getUserToken());
            if(user==null){
                returnJson.setErrorCode(9003);
                returnJson.setReturnMessage("对应User token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }

            House house =houseService.getHouse(houseUser.getHouseToken());
            if(house==null){
                returnJson.setErrorCode(9004);
                returnJson.setReturnMessage("对应House token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            HouseUser houseUser1 = null;
            houseUser.setUserId(user.getId());
            houseUser.setHouseId(house.getId());
            try{
                houseUser1 = houseUserService.getHouseUser(houseUser);
            }catch (Exception ex){
                ex.printStackTrace();
                returnJson.setErrorCode(9005);
                returnJson.setReturnMessage("服务器异常" + houseUser.toString());
                returnJson.setServerStatus(1);
            }
            if(houseUser1!=null){
                return returnJson;
            }
            houseService.updateHouseUser(house);
            channelService.updateChannelUser(house.getChannelId());
            houseUserService.insertUser(houseUser);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("服务器异常" + houseUser.toString());
            returnJson.setServerStatus(1);
        }
        returnJson.setReturnObject(houseUser);
        return returnJson;
    }


    @RequestMapping(value="/HouseUser/Web",method = RequestMethod.POST)
    @ResponseBody
    public Object insertUserWeb(@RequestBody HouseUser houseUser){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(9000);
        returnJson.setReturnMessage("调用成功" + houseUser.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseUser.getUserToken())||StringUtils.isEmpty(houseUser.getHouseToken())){
            returnJson.setErrorCode(9002);
            returnJson.setReturnMessage("传入参数为空" + houseUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = null;
        try{
            user = userService.selectByToken(houseUser.getUserToken());
            if(user==null){
                returnJson.setErrorCode(9003);
                returnJson.setReturnMessage("对应User token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }

            House house =houseService.getHouse(houseUser.getHouseToken());
            if(house==null){
                returnJson.setErrorCode(9004);
                returnJson.setReturnMessage("对应House token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            HouseUser houseUser1 = null;
            houseUser.setUserId(user.getId());
            houseUser.setHouseId(house.getId());
            try{
                houseUser1 = houseUserService.getHouseUser(houseUser);
            }catch (Exception ex){
                ex.printStackTrace();
                returnJson.setErrorCode(9005);
                returnJson.setReturnMessage("服务器异常" + houseUser.toString());
                returnJson.setServerStatus(1);
            }
            if(houseUser1!=null){
                return returnJson;
            }
            houseService.updateHouseUser(house);
            channelService.updateChannelUser(house.getChannelId());
            houseUserService.insertUser(houseUser);
            String addToChatgroupid = house.getHuanxinRoomId();
            String toAddUsername = user.getHuanxinUid();
            ObjectNode addUserToGroupNode = EasemobChatGroups.addUserToGroup(addToChatgroupid, toAddUsername);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("服务器异常" + houseUser.toString());
            returnJson.setServerStatus(1);
        }
        returnJson.setReturnObject(houseUser);
        return returnJson;
    }





    @RequestMapping(value="/HouseUsers",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers(HouseUser houseUser){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(10000);
        returnJson.setReturnMessage("调用成功"+houseUser.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseUser.getHouseToken())){
            returnJson.setErrorCode(10001);
            returnJson.setReturnMessage("传入参数为空" + houseUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        House house = houseService.getHouse(houseUser.getHouseToken());
        if(house==null){
            returnJson.setErrorCode(10002);
            returnJson.setReturnMessage("houseToken不存在" + houseUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;

        }
        List<HouseUser> result = new ArrayList<HouseUser>();
        try {
           result = houseUserService.getUsers(house.getId());
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(10003);
            returnJson.setReturnMessage("服务器错误" + houseUser.toString());
            returnJson.setServerStatus(1);
        }
        returnJson.setReturnObject(result);
        return returnJson;
    }

    @RequestMapping(value="/HouseUser/Leave",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteUser(@RequestBody  HouseUser houseUser){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(12000);
        returnJson.setReturnMessage("调用成功" + houseUser.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseUser.getUserToken())||StringUtils.isEmpty(houseUser.getHouseToken())){
            returnJson.setErrorCode(12002);
            returnJson.setReturnMessage("传入参数为空" + houseUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }

        try{
            User user = userService.selectByToken(houseUser.getUserToken());
            if(user==null){
                returnJson.setErrorCode(12003);
                returnJson.setReturnMessage("对应User token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }


            House house =houseService.getHouse(houseUser.getHouseToken());
            if(house==null){
                returnJson.setErrorCode(12004);
                returnJson.setReturnMessage("对应House token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            houseUser.setUserId(user.getId());
            houseUser.setHouseId(house.getId());
            if(houseUserService.getHouseUser(houseUser)==null){
                return returnJson;
            }
            channelService.minusChannelUser(house.getChannelId());
            houseUser.setUserId(user.getId());
            houseService.minusHouseUser(house);
            houseUserService.deleteUser(houseUser);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(12005);
            returnJson.setReturnMessage("服务器异常" + houseUser.toString());
            returnJson.setServerStatus(1);
        }
        Server server = new Server(12338);
        RecordClient client = new RecordClient();
        client.setAction("3");
        client.setHouseToken(houseUser.getHouseToken());
        client.setUserToken(houseUser.getUserToken());
        server.loopServer(houseUser.getHouseToken(), JSONObject.toJSONString(client));
        returnJson.setReturnObject(houseUser);
        return returnJson;
    }



    @RequestMapping(value="/HouseUser",method = RequestMethod.PUT)
    @ResponseBody
    public Object updateUser(@RequestBody HouseUser houseUser){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(29000);
        returnJson.setReturnMessage("调用成功" + houseUser.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseUser.getUserToken())||houseUser.getIdentification()==0||StringUtils.isEmpty(houseUser.getHouseToken())){
            returnJson.setErrorCode(29002);
            returnJson.setReturnMessage("传入参数为空" + houseUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }

        try{
            User user = userService.selectByToken(houseUser.getUserToken());
            if(user==null){
                returnJson.setErrorCode(29003);
                returnJson.setReturnMessage("对应User token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            House house =houseService.getHouse(houseUser.getHouseToken());
            if(house==null){
                returnJson.setErrorCode(29004);
                returnJson.setReturnMessage("对应House token不存在" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            houseUser.setHouseId(house.getId());
            houseUser.setUserId(user.getId());
            if(houseUserService.getHouseUser(houseUser)==null){
                returnJson.setErrorCode(29006);
                returnJson.setReturnMessage("该用户未加入房间" + houseUser.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }

            houseUser.setUserId(user.getId());
            houseUser.setHouseId(house.getId());
            houseUserService.updateUser(houseUser);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(29005);
            returnJson.setReturnMessage("服务器异常" + houseUser.toString());
            returnJson.setServerStatus(1);
        }
        return returnJson;
    }

    @RequestMapping(value="/Users",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers(String houseToken){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(46000);
        returnJson.setReturnMessage("调用成功" + houseToken);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseToken)){
            returnJson.setErrorCode(46000);
            returnJson.setReturnMessage("传入参数为空" + houseToken);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<HouseUser> houseUsers = null;
        try{

            House house =houseService.getHouse(houseToken);
            if(house==null){
                returnJson.setErrorCode(46004);
                returnJson.setReturnMessage("对应House     token不存在" + houseToken);
                returnJson.setServerStatus(1);
                return returnJson;
            }
            houseUsers = houseUserService.getHouseUsers(house.getId());
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(46005);
            returnJson.setReturnMessage("服务器异常" +houseToken);
            returnJson.setServerStatus(1);
        }
        returnJson.setReturnValue(new Integer(houseUsers.size()).toString());
        returnJson.setReturnObject(houseUsers);
        return returnJson;
    }

}
