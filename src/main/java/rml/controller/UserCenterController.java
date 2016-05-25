package rml.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.httpclient.apidemo.EasemobIMUsers;
import rml.model.*;
import rml.service.*;
import rml.util.ReturnJson;

import java.util.Date;
import java.util.List;


/**
 * Created by edward-echo on 2016/2/25.
 */
@Controller
@RequestMapping("/UserCenter")
public class UserCenterController {

    @Autowired
    UserServiceI userService;

    @Autowired
    ValidCodeService validCodeService;


    @RequestMapping(value="/Profile",method = RequestMethod.POST)
    @ResponseBody
    public Object paticipants(@RequestBody User user) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(60000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(user.getIcon())||StringUtils.isEmpty(user.getToken())||StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getValidCode())||StringUtils.isEmpty(user.getNickName())){
            returnJson.setErrorCode(60002);
            returnJson.setReturnMessage("传入参数为空" + user.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        ValidCode code = new ValidCode();
        code.setValidCode(user.getValidCode());
        code.setMobile(user.getMobile());
        int flag =   validCodeService.checkValidCode(code);
        User user1 = userService.selectByToken(user.getToken());
        if(flag==1){
            returnJson.setErrorCode(60003);
            returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user2 = userService.selectByMobile(user.getMobile());
        if(user2!=null){
            returnJson.setErrorCode(60004);
            returnJson.setReturnMessage("该手机已被绑定，请勿重复绑定" + user.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            user.setId(user1.getId());
            user.setUpdateTime(new Date());
            userService.updateProfile(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(60005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


    @RequestMapping(value="/Password",method = RequestMethod.POST)
         @ResponseBody
         public Object password(@RequestBody User user) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(61000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getValidCode())){
            returnJson.setErrorCode(61002);
            returnJson.setReturnMessage("传入参数为空" + user.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        ValidCode code = new ValidCode();
        code.setValidCode(user.getValidCode());
        code.setMobile(user.getMobile());
        int flag =   validCodeService.checkValidCode(code);
        User user1 = userService.selectByMobile(user.getMobile());
        if(flag==1){
            returnJson.setErrorCode(61003);
            returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            user.setId(user1.getId());
            user.setUpdateTime(new Date());
            userService.updatePassword(user);
            ObjectNode json2 = JsonNodeFactory.instance.objectNode();
            json2.put("newpassword",user.getPassword());
            ObjectNode modifyIMUserPasswordWithAdminTokenNode =	EasemobIMUsers.modifyIMUserPasswordWithAdminToken(user1.getHuanxinUid(),json2);
            if (null != modifyIMUserPasswordWithAdminTokenNode) {
                //LOGGER.info("重置IM用户密码 提供管理员token: " + modifyIMUserPasswordWithAdminTokenNode.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(61004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        user1.setPassword(user.getPassword());
        returnJson.setReturnObject(user1);
        return returnJson;
    }


    @Autowired
    CommentService commentService;

    @RequestMapping(value="/Comment",method = RequestMethod.POST)
    @ResponseBody
    public Object Comment(@RequestBody Comment comment) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(62000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(comment.getContent())||StringUtils.isEmpty(comment.getUserToken())){
            returnJson.setErrorCode(62002);
            returnJson.setReturnMessage("传入参数为空" + comment.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user1 = userService.selectByToken(comment.getUserToken());
        try {
            comment.setUserId(user1.getId());
            commentService.createComment(comment);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(62004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


    @Autowired
    LessonServiceI lessonService;

    @RequestMapping(value="/Files",method = RequestMethod.GET)
    @ResponseBody
    public Object lessons(String userToken,int pageNo,int pageSize) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(63000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(userToken)) {
            returnJson.setErrorCode(63002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByToken(userToken);
            Lesson lesson = new Lesson();
            lesson.setUserId(user.getId());
            lesson.setPageSize(pageSize);
            lesson.setPageNo(pageNo);
            returnJson.setReturnObject(lessonService.getUserLesson(lesson));
            List<Lesson> lessons= lessonService.getUserLessonTotal(lesson);
            returnJson.setReturnValue(new Integer(lessons.size()).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(63004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }

    @RequestMapping(value="/File/Delete",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteLessons(String token) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(63000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(token)) {
            returnJson.setErrorCode(63002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            lessonService.deleteLesson(token);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(63004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


    @Autowired
    private CommunityServiceI communityService;

    @Autowired
    private CommunityBbsServiceI communityBbsService;

    @RequestMapping(value="/Communities",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserCommunities(String userToken,int pageSize,int pageNo) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(64000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(userToken)||pageSize==0||pageNo==0) {
            returnJson.setErrorCode(64002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByToken(userToken);
            Community community1 = new Community();
            community1.setUserId(user.getId());
            community1.setPageNo(pageNo);
            community1.setPageSize(pageSize);
            List<Community> communities = communityService.getUserCommunities(community1);
            for(Community community:communities){
                if(community.getUserId()==user.getId()){
                    community.setIsAdmin(true);
                }else{
                    community.setIsJoin(true);
                }
            }
            List<Community> communities1 = communityService.getUserCommunitiesTotal(community1);
            returnJson.setReturnValue(new Integer(communities1.size()).toString());
            returnJson.setReturnObject(communities);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(64004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


    @RequestMapping(value="/Community/Details",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserCommunitiyDetails(String userToken,String communityToken) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(65000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(userToken)||StringUtils.isEmpty(communityToken)) {
            returnJson.setErrorCode(65002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByToken(userToken);

            Community community = communityService.getCommunityByToken(communityToken);
                Lesson lesson = new Lesson();
                lesson.setUserId(user.getId());
                lesson.setCommunityId(community.getId());
                List<Lesson> lessons = lessonService.getLessons(lesson);
                for(Lesson lesson1:lessons){
                    lesson1.setCommunityToken(community.getToken());
                    lesson1.setUserToken(user.getToken());
                }
                CommunityBBS communityBBS = new CommunityBBS();
                communityBBS.setCommunityId(community.getId());
                communityBBS.setUserId(user.getId());
                List<CommunityBBS> communityBBSList= communityBbsService.getBlogs(communityBBS);
                for(CommunityBBS communityBBS1:communityBBSList){
                    communityBBS1.setUserIcon(user.getIcon());
                    if(communityBBS1.getTopTime()!=null){
                        communityBBS1.setIsTop(1);
                    }

                }
                if(community.getUserId()==user.getId()){
                    community.setIsAdmin(true);
                }else{
                    community.setIsJoin(true);
                }
                community.setBbses(communityBBSList);
                community.setLessons(lessons);
            returnJson.setReturnObject(community);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(65004);
            returnJson.setErrorCode(65004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }

    @Autowired
    private ChannelServiceI channelService;

    @RequestMapping(value="/Channels",method = RequestMethod.GET)
    @ResponseBody
    public Object getChannel(String userToken,int pageNo,int pageSize) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(66000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(userToken)) {
            returnJson.setErrorCode(66002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByToken(userToken);
            Channel channel = new Channel();
            channel.setPageSize(pageSize);
            channel.setPageNo(pageNo);
            if(user==null){
                return returnJson;
            }
            channel.setUserId(user.getId());

            List<Channel> channels = channelService.getUserChannel(channel);
            returnJson.setReturnObject(channels);
            List<Channel> list = channelService.getUserChannelTotal(channel);
            returnJson.setReturnValue(new Integer(list.size()).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(66004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


    @Autowired
    HouseServiceI houseService;

    @RequestMapping(value="/Channel/Houses",method = RequestMethod.GET)
    @ResponseBody
    public Object getChannelHouse(String channelToken,String userToken) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(67000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(channelToken)||StringUtils.isEmpty(userToken)) {
            returnJson.setErrorCode(67002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Channel channel = channelService.getChannel(channelToken);
        User user = userService.selectByToken(userToken);
        try {
            House house = new House();
            house.setUserId(user.getId());
            house.setChannelId(channel.getId());
            if(user==null||channel==null){
                return returnJson;
            }
            List<House> list = houseService.getUserHouse(house);
            returnJson.setReturnObject(list);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(67004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }
}
