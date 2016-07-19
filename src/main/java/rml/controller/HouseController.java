package rml.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.client.util.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.httpclient.apidemo.EasemobChatGroups;
import rml.httpclient.apidemo.EasemobIMUsers;
import rml.httpclient.utils.HTTPClientUtils;
import rml.model.*;
import rml.server.HouseKeeper;
import rml.server.NettyServer;
import rml.service.*;
import rml.util.HTTPMethod;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
@Controller
@RequestMapping("/House")
public class HouseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseController.class);

    @Autowired
    private HouseServiceI houseService;

    @Autowired
    private ChannelServiceI channelService;

    @Autowired
    VideoServiceI videoService;

    @Autowired
    UserServiceI userService;

    @Autowired
    RoleServiceI roleService;

    @Autowired
    private HouseUserServiceI houseUserService;

    @Autowired
    private ResourceServiceI resourceService;

    @RequestMapping(value="/Create",method = RequestMethod.POST)
    @ResponseBody
    public Object createHouse(@RequestBody House house) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功" + house.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(house.getHouseIcon())||StringUtils.isEmpty(house.getTitle())||StringUtils.isEmpty(house.getChannelToken())||StringUtils.isEmpty(house.getUserToken())){
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("传入参数为空" + house.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = userService.selectByToken(house.getUserToken());
        try{
            user = userService.selectByToken(house.getUserToken());
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        if(user==null){
            returnJson.setErrorCode(7003);
            returnJson.setReturnMessage("userToken不存在");
            returnJson.setServerStatus(2);
            return returnJson;
        }

        House house1 = null;
        try {
            house.setUserId(user.getId());
            ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
            dataObjectNode.put("groupname",house.getTitle());
            dataObjectNode.put("desc", house.getTitle());
            dataObjectNode.put("approval", false);
            dataObjectNode.put("public", true);
            dataObjectNode.put("maxusers", 9999);
            dataObjectNode.put("owner", user.getHuanxinUid());
            ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
            dataObjectNode.put("members", arrayNode);
            ObjectNode creatChatGroupNode = EasemobChatGroups.creatChatGroups(dataObjectNode);
            if (null != creatChatGroupNode) {
                LOGGER.info("注册群组[单个]: " + creatChatGroupNode.toString());
            }
            Channel channel = channelService.getChannel(house.getChannelToken());
            if(channel==null){
                returnJson.setErrorCode(7003);
                returnJson.setReturnMessage("channelToken不存在");
                returnJson.setServerStatus(2);
            }
            house.setHuanxinRoomId(creatChatGroupNode.findValue("groupid").textValue());
            if(house.getPassword()!=null) {
                house.setPassword(MD5.GetMD5Code(house.getPassword()));
            }
            house.setChannelId(channel.getId());
            houseService.createHouse(house);
            House house2 = houseService.getHouse(house.getToken());
            HouseUser houseUser = new HouseUser();
            houseUser.setHouseId(house2.getId());
            houseUser.setIdentification(4);
            houseUser.setUserId(user.getId());
            List<HouseSchedule> schedules = house.getSchedules();

            houseUserService.insertUser(houseUser);
            houseService.updateHouseUser(house);
            returnJson.setReturnValue(creatChatGroupNode.findValue("groupid").textValue());
            house1 = houseService.getHouseDetail(house.getToken());
            if(schedules!=null){
            for(HouseSchedule schedule:schedules) {
                schedule.setCreateTime(new Date());
                schedule.setHouseId(house2.getId());
                houseScheduleService.createSchedule(schedule);
            }
            }
            house1.setHuanxinRoomId(house.getHuanxinRoomId());
            house1.setDesc(house.getDesc());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(house1);
        return returnJson;
    }



    @Autowired
    private HouseScheduleService houseScheduleService;

    @RequestMapping(value="/Houses",method = RequestMethod.GET)
    @ResponseBody
    public Object getHouse(String channelToken) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(41000);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(channelToken)){
            returnJson.setErrorCode(41002);
            returnJson.setReturnMessage("传入参数为空" + channelToken);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Channel channel =channelService.getChannel(channelToken);
        List<House> houses = null;
        try {
            houses = houseService.getChannelHouses(channel.getId());
            for(House house:houses) {
                if (StringUtils.isEmpty(house.getDesc())) {
                    house.setIsDescNull(1);
                }
                if (StringUtils.isEmpty(house.getPassword())) {
                    house.setIsPasswordNull(1);
                }
                if (StringUtils.isEmpty(house.getLessonDesc())) {
                    house.setIsLessonDescNull(1);
                }
                List<HouseSchedule> houseSchedules = houseScheduleService.getHouseSchedules(house.getId());
                house.setSchedules(houseSchedules);
                if(houseSchedules==null){
                    house.setIsScheduleNull(1);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(41001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(houses);
        return returnJson;
    }


    @RequestMapping(value="/House",method = RequestMethod.GET)
    @ResponseBody
    public Object getHouses(String houseToken) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(41000);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseToken)){
            returnJson.setErrorCode(41002);
            returnJson.setReturnMessage("传入参数为空" + houseToken);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            House house = houseService.getHouseDetail(houseToken);
            List<HouseSchedule> houseSchedules = houseScheduleService.getHouseSchedules(house.getId());
            house.setSchedules(houseSchedules);
            if(StringUtils.isEmpty(house.getDesc())){
                house.setIsDescNull(1);
            }
            if(StringUtils.isEmpty(house.getPassword())){
                house.setIsPasswordNull(1);
            }
            if(StringUtils.isEmpty(house.getLessonDesc())){
                house.setIsLessonDescNull(1);
            }
            if(houseSchedules==null){
                house.setIsScheduleNull(1);
            }
            returnJson.setReturnObject(house);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(41001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }

        return returnJson;
    }


    @RequestMapping(value="/Update",method = RequestMethod.POST)
    @ResponseBody
    public Object updateHouse(@RequestBody House house) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功" + house.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(house.getToken())||StringUtils.isEmpty(house.getTitle())||StringUtils.isEmpty(house.getChannelToken())){
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("传入参数为空" + house.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        House house1 = houseService.getHouse(house.getToken());

        try {
            if(house.getPassword()!=null) {
                house.setPassword(MD5.GetMD5Code(house.getPassword()));
            }
            house.setId(house1.getId());
            houseService.updateHouse(house);
            houseScheduleService.deleteScheduls(house.getId());
            List<HouseSchedule> schedules = house.getSchedules();
            house1 = houseService.getHouseDetail(house.getToken());
            for(HouseSchedule schedule:schedules){
                schedule.setCreateTime(new Date());
                schedule.setHouseId(house1.getId());
                houseScheduleService.createSchedule(schedule);
            }
            house1.setSchedules(schedules);
            house1.setHuanxinRoomId(house.getHuanxinRoomId());
            house1.setDesc(house.getDesc());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(house1);
        return returnJson;
    }
    @RequestMapping(value="/getHouseUsers",method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson getHouseUsers(Integer houseId){
         ReturnJson returnJson = new ReturnJson();
         returnJson.setErrorCode(41000);
         returnJson.setServerStatus(0);
         if(houseId==null||houseId<=0){
             returnJson.setErrorCode(41002);
             returnJson.setReturnMessage("传入参数为" + houseId);
             returnJson.setServerStatus(1);
             return returnJson;
         }
         try {
             List<HouseUser> users = houseUserService.getHouseUsers(houseId);
             returnJson.setReturnObject(users);
         }catch(Exception ex){
             ex.printStackTrace();
             returnJson.setErrorCode(41001);
             returnJson.setReturnMessage("服务器异常");
             returnJson.setServerStatus(2);
         }

         return returnJson;
     }

    @RequestMapping(value="/enterHouse",method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson enterHouse(String houseToken,String pwd){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功" + houseToken);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseToken)){
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            House house=houseService.getHouse(houseToken);
            if(StringUtils.isNotEmpty(pwd)){
                String enc= MD5.GetMD5Code(pwd);
                if(!(house.getPassword()!=null&&house.getPassword().equals(enc))){
                    returnJson.setErrorCode(7003);
                    returnJson.setReturnMessage("密码错误！");
                    returnJson.setServerStatus(1);

                    return returnJson;
                }
            }
            //TODO 待完善用户权限验证
            List<Role> roleList=roleService.findRoleByHouse(house);
            house.setRoleList(roleList);
            NettyServer nettyServer=HouseKeeper.getServer(houseToken);
            String url=nettyServer.getUrl();
            house.setUrl(url);//nettyServer 地址
            returnJson.setReturnObject(house);

        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("服务器异常"+e.getMessage());
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }
    @RequestMapping(value="/getHouseSetting",method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson getHouseSetting(String houseToken){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功" + houseToken);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseToken)){
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            House house = houseService.getHouse(houseToken);
            List<Role> roleList = roleService.findRoleByHouse(house);
            house.setRoleList(roleList);
            returnJson.setReturnObject(house);
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("服务器异常"+e.getMessage());
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }
    @RequestMapping(value = "/getResources")
    @ResponseBody
    public ReturnJson getResources(String token){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功" + token);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(token)){
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            List<Resource> list=resourceService.findResourcesByHouseToken(token);
            returnJson.setReturnObject(list);
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("服务器异常"+e.getMessage());
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }

    @RequestMapping(value = "/changeRole")
    @ResponseBody
    public ReturnJson changeRole(String token,String openlisten,Integer openTime,String roleIds){
        ReturnJson returnJson=new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功" + token);
        returnJson.setServerStatus(0);
        //TODO 判断用户是否有权限修改房间设置

        if(StringUtils.isEmpty(token)){
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<Role> roleList= Lists.newArrayList();
        if(StringUtils.isNotBlank(roleIds)){
            String rIds[]=roleIds.split(",");
            for (String rid:rIds){
                roleList.add(new Role(rid));
            }
        }
        House house=houseService.getHouse(token);
        house.setOpenListen(openlisten);
        house.setOpenTime(openTime);
        houseService.updateHouse(house);
        houseService.updateHouseRole(house,roleList);
        if(roleList.size()<=0){
            returnJson.setErrorCode(7003);
            returnJson.setReturnMessage("房间没有配置角色！");
            returnJson.setServerStatus(1);
        }
        return returnJson;
    }
}
