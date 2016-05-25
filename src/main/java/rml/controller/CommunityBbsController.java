package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.BBSCommentServiceI;
import rml.service.CommunityBbsServiceI;
import rml.service.CommunityServiceI;
import rml.service.UserServiceI;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/10/11 0011.
 */

@Controller
@RequestMapping("/Blog")
public class CommunityBbsController {

    @Autowired
    private CommunityBbsServiceI communityBbsService;


    @Autowired
    private CommunityServiceI  communityService;


    @Autowired
    private UserServiceI userService;

    @Autowired
    BBSCommentServiceI bbsCommentService;


    @RequestMapping(value="/Blog",method = RequestMethod.POST)
    @ResponseBody
    public Object createBlog(@RequestBody CommunityBBS communityBBS){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(34000);
        returnJson.setReturnMessage("调用成功"+communityBBS.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(communityBBS.getUserToken())||StringUtils.isEmpty(communityBBS.getTitle())||StringUtils.isEmpty(communityBBS.getCommunityToken())){
            returnJson.setErrorCode(34001);
            returnJson.setReturnMessage("传入参数为空" + communityBBS.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Community community = null;
        try {
            community = communityService.getCommunityByToken(communityBBS.getCommunityToken());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(34004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        if(community==null){
            returnJson.setErrorCode(34002);
            returnJson.setReturnMessage("community token不存在" + communityBBS.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = null;

        try {
            user = userService.selectByToken(communityBBS.getUserToken());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(34004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        if(user==null){
            returnJson.setErrorCode(34003);
            returnJson.setReturnMessage("user token不存在" + communityBBS.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            communityBBS.setUserId(user.getId());
            communityBBS.setCommunityId(community.getId());
            communityBBS.setUserName(user.getNickName());
            communityBBS.setUserIcon(user.getIcon());
            communityBbsService.createBlog(communityBBS);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(34004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }

        returnJson.setReturnObject(communityBBS);
        return returnJson;
    }




    @RequestMapping(value="/Blog",method = RequestMethod.GET)
    @ResponseBody
    public Object getBlog(String token) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(35000);
        returnJson.setServerStatus(0);
        if(org.apache.commons.lang.StringUtils.isEmpty(token)){
            returnJson.setErrorCode(35002);
            returnJson.setReturnMessage("传入参数为空" + token);
            returnJson.setServerStatus(1);
            return returnJson;
        }

       CommunityBBS communityBBS = null;
        try{
            communityBBS= communityBbsService.getBbsByToken(token);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(35003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(communityBBS);
        return returnJson;
    }


    @RequestMapping(value="/Blogs",method = RequestMethod.GET)
    @ResponseBody
    public Object getBlogs(CommunityBBS communityBBS) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(35000);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(communityBBS.getCommunityToken())||communityBBS.getPageSize()==0||communityBBS.getPageNo()==0){
            returnJson.setErrorCode(35002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Community community = communityService.getCommunityByToken(communityBBS.getCommunityToken());

        if(community==null){
            returnJson.setErrorCode(35003);
            returnJson.setReturnMessage("token不存在或者为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<CommunityBBS> values = new ArrayList<CommunityBBS>();
        List<CommunityBBS> communityBBSList = null;
        try{
            communityBBS.setCommunityId(community.getId());
            communityBBSList= communityBbsService.getCommunityBlogs(communityBBS);
            for(CommunityBBS communityBBS1:communityBBSList){
                User user = userService.selectByPrimaryKey(communityBBS1.getUserId());
                communityBBS1.setUserIcon(user.getIcon());
                if(communityBBS1.getTopTime()!=null){
                    communityBBS1.setIsTop(1);
                }
                values.add(communityBBS1);
            }
            returnJson.setReturnValue(new Integer(communityBbsService.getCommunityBlogsTotal(communityBBS).size()).toString());
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(35003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(values);
        return returnJson;
    }

    @RequestMapping(value="/Comment",method = RequestMethod.POST)
    @ResponseBody
    public Object createComment(@RequestBody BBSComment bbsComment){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(36000);
        returnJson.setReturnMessage("调用成功" + bbsComment.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(bbsComment.getUserToken())||StringUtils.isEmpty(bbsComment.getBbsToken())){
            returnJson.setErrorCode(36002);
            returnJson.setReturnMessage("传入参数为空" + bbsComment.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        CommunityBBS communityBBS = null;
        try {
            communityBBS = communityBbsService.getBbsByToken(bbsComment.getBbsToken());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(36003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        if(communityBBS==null){
            returnJson.setErrorCode(36005);
            returnJson.setReturnMessage("communityBBS token不存在" + communityBBS.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = null;
        try {
            user = userService.selectByToken(bbsComment.getUserToken());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(34003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        if(user==null){
            returnJson.setErrorCode(34004);
            returnJson.setReturnMessage("user token不存在" + communityBBS.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            bbsComment.setUserId(user.getId());
            bbsComment.setBbsId(communityBBS.getId());
            bbsComment.setCreateTime(new Date());
            bbsComment.setUserName(user.getNickName());
            bbsCommentService.createComment(bbsComment);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(34003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(bbsComment);
        return returnJson;
    }

    @RequestMapping(value="/Comments",method = RequestMethod.GET)
    @ResponseBody
    public Object getComments(BBSComment bbsComment) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(37000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(org.apache.commons.lang.StringUtils.isEmpty(bbsComment.getBbsToken())||bbsComment.getPageSize()==0||bbsComment.getPageSize()==0){
            returnJson.setErrorCode(37002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        CommunityBBS communityBBS = null;
        try {
            communityBBS = communityBbsService.getBbsByToken(bbsComment.getBbsToken());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(37005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        if(communityBBS==null){
            returnJson.setErrorCode(37003);
            returnJson.setReturnMessage("bbsToken不存在");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<BBSComment> list = new ArrayList<BBSComment>();
        try{
            bbsComment.setBbsId(communityBBS.getId());
            list= bbsCommentService.getBbsComments(bbsComment);
            returnJson.setReturnValue(new Integer(bbsCommentService.getBbsCommentsTotal(bbsComment).size()).toString());
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(37005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(list);
        return returnJson;
    }

    @RequestMapping(value="/Comment",method = RequestMethod.GET)
    @ResponseBody
    public Object getComment(String token) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(38000);
        returnJson.setServerStatus(0);
        if(org.apache.commons.lang.StringUtils.isEmpty(token)){
            returnJson.setErrorCode(38002);
            returnJson.setReturnMessage("传入参数为空" + token);
            returnJson.setServerStatus(1);
            return returnJson;
        }

       BBSComment bbsComment = null;
        try{
            bbsComment= bbsCommentService.getCommentByToken(token);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(38003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(bbsComment);
        return returnJson;
    }


    @RequestMapping(value="/Top",method = RequestMethod.GET)
    @ResponseBody
    public Object doTop(CommunityBBS communityBBS) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(39000);
        returnJson.setServerStatus(0);
        if(org.apache.commons.lang.StringUtils.isEmpty(communityBBS.getToken())){
            returnJson.setErrorCode(39002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try{
            communityBBS.setTopTime(new Date());
            communityBbsService.top(communityBBS);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(39003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


    @RequestMapping(value="/Top/Cancel",method = RequestMethod.GET)
    @ResponseBody
    public Object cancelTop(CommunityBBS communityBBS) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(40000);
        returnJson.setServerStatus(0);
        if(org.apache.commons.lang.StringUtils.isEmpty(communityBBS.getToken())){
            returnJson.setErrorCode(40002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try{
            communityBBS.setTopTime(new Date());
            communityBbsService.topCancel(communityBBS);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(40003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }

    public static void main(String[]args){
        Long x = 1448953355845l-1448953340471l;
        System.err.print(x);
    }
}
