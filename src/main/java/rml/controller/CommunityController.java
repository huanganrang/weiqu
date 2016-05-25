package rml.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.*;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/10/5 0005.
 */

@Controller
@RequestMapping("/Community")
public class CommunityController {

    @Autowired
    private LessonServiceI lessonService;

    @Autowired
    private CommunityBbsServiceI communityBbsService;


    @Autowired
    private CommunityServiceI  communityService;


    @Autowired
    private UserServiceI userService;

    @Autowired
    private BBSCommentServiceI bbsCommentService;

    @Autowired
    private CommunityMemberService communityMemberService;


    @Autowired
    CommunityCategoryServiceI communityCategoryService;

    @RequestMapping(value="/Community",method = RequestMethod.POST)
    @ResponseBody
    public Object createCommnity(@RequestBody Community community) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(17000);
        returnJson.setReturnMessage("调用成功"+community.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(community.getDefaultImg())||StringUtils.isEmpty(community.getName())||StringUtils.isEmpty(community.getIcon())||StringUtils.isEmpty(community.getShortDesc())||community.getCategoryId()==0||StringUtils.isEmpty(community.getUserToken())){
            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("传入参数为空" + community.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        CommunityCategory communityCategory = communityCategoryService.getCategoryById(community.getCategoryId());
        if(communityCategory==null){
            returnJson.setErrorCode(17002);
            returnJson.setReturnMessage("categoryId不存在"+community);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = null;
        try{
            user = userService.selectByToken(community.getUserToken());
        }catch (Exception ex){
            ex.printStackTrace();
            ex.printStackTrace();
            returnJson.setErrorCode(17004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        if(user==null){
            returnJson.setErrorCode(17003);
            returnJson.setReturnMessage("userToken不存在"+community);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Community community1 = null;
        try {
            community1 = communityService.getUserCommunity(user.getId());
            if(community1!=null){
                returnJson.setErrorCode(17004);
                returnJson.setReturnMessage("请勿重复创建社区"+community);
                returnJson.setServerStatus(1);
                return returnJson;
            }
            community.setUserId(user.getId());
            community.setCategoryName(communityCategory.getName());
            community1 = communityService.createCommunity(community);
            Community community2 = communityService.getCommunityByToken(community.getToken());
            community1.setUserToken(community.getUserToken());
            community1.setCategoryName(communityCategory.getName());
            CommunityMember communityMember = new CommunityMember();
            communityMember.setUserId(user.getId());
            communityMember.setCommunityId(community2.getId());
            communityMember.setCreateTime(new Date());
            communityMemberService.createMember(communityMember);

        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(17005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(community1);
        return returnJson;
    }

    @RequestMapping(value="/Community/User",method = RequestMethod.GET)
    @ResponseBody
    public Object getCommunities(Community community) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(18000);
        returnJson.setReturnMessage("调用成功"+community.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(community.getUserToken())||StringUtils.isEmpty(community.getCommunityToken())){
            returnJson.setErrorCode(18001);
            returnJson.setReturnMessage("传入参数为空" + community.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = null;
        try{
            user = userService.selectByToken(community.getUserToken());
        }catch (Exception ex){
            ex.printStackTrace();
            ex.printStackTrace();
            returnJson.setErrorCode(18003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        if(user==null){
            returnJson.setErrorCode(18002);
            returnJson.setReturnMessage("userToken不存在"+community);
            returnJson.setServerStatus(1);
            return returnJson;
        }

        try {
            Community community1 = communityService.getCommunityByToken(community.getCommunityToken());
            CommunityMember communityMember = new CommunityMember();
            communityMember.setUserId(user.getId());
            communityMember.setCommunityId(community1.getId());
            CommunityMember communityMember1 = communityMemberService.getCommunityMember(communityMember);
            if(communityMember1!=null){
                community1.setIsJoin(true);
            }else{
                community1.setIsJoin(false);
            }
            community1.setTotalPeople(communityMember.getTotalPeople());
            community1.setUserToken(user.getToken());
            if(user.getId()==community1.getUserId()){
                community1.setIsAdmin(true);
            }else{
                community1.setIsAdmin(false);
            }
            returnJson.setReturnObject(community1);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(18003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Member",method = RequestMethod.POST)
    @ResponseBody
    public Object joinMember(@RequestBody CommunityMember communityMember) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(17000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if (StringUtils.isEmpty(communityMember.getCommunityToken()) || StringUtils.isEmpty(communityMember.getUserToken())){
            returnJson.setErrorCode(17001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
    }
    Community community = communityService.getCommunityByToken(communityMember.getCommunityToken());
    if(community==null){
        returnJson.setErrorCode(17002);
        returnJson.setReturnMessage("communityToken不存在");
        returnJson.setServerStatus(1);
        return returnJson;
    }
    communityMember.setCommunityId(community.getId());
    User user = userService.selectByToken(communityMember.getUserToken());
   if(user==null){
            returnJson.setErrorCode(17003);
            returnJson.setReturnMessage("userToken不存在");
            returnJson.setServerStatus(1);
            return returnJson;
        }
   communityMember.setUserId(user.getId());
   communityMember.setToken(UUID.randomUUID().toString());
   communityMember.setCreateTime(new Date());
   communityMemberService.createMember(communityMember);
   returnJson.setReturnObject(communityMember);
   return returnJson;
    }



    @RequestMapping(value="/Community",method = RequestMethod.GET)
    @ResponseBody
    public Object getCommunity(Community community) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(19000);
        returnJson.setReturnMessage("调用成功" + community.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(community.getToken())){
            returnJson.setErrorCode(19001);
            returnJson.setReturnMessage("传入参数为空" + community.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Community community1 = null;
        try {
            community1 = communityService.getCommunityByToken(community.getToken());
            User user = userService.selectByPrimaryKey(community1.getUserId());
            community1.setUserToken(user.getToken());
            community1.setCommunityToken(community1.getToken());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(19002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(community1);
        return returnJson;
    }


    @RequestMapping(value="/Communities",method = RequestMethod.GET)
    @ResponseBody
    public Object getCommunitiess(Community community) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(19000);
        returnJson.setReturnMessage("调用成功" + community.toString());
        returnJson.setServerStatus(0);
        if(community.getPageNo()==0||community.getPageSize()==0){
            returnJson.setErrorCode(19001);
            returnJson.setReturnMessage("传入参数为空" + community.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<Community> communities = null;
        try {
            communities = communityService.getALLCommunities(community);
            returnJson.setReturnValue(new Integer(communityService.getALLCommunities(community).size()).toString());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(19002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(communities);
        return returnJson;
    }




    @RequestMapping(value="/Categories",method = RequestMethod.GET)
    @ResponseBody
    public Object getCategory(){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(16000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<CommunityCategory> list = new ArrayList<CommunityCategory>();
        try {
            list = communityCategoryService.getCategory();
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(16001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(list);
        return returnJson;
    }

}
