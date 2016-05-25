package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.BBSComment;
import rml.model.Community;
import rml.model.CommunityBBS;
import rml.model.User;
import rml.service.BBSCommentServiceI;
import rml.service.CommunityBbsServiceI;
import rml.service.UserServiceI;
import rml.util.ReturnJson;

/**
 * Created by Administrator on 2015/10/11 0011.
 */


public class BBSCommentController {



   @Autowired
   BBSCommentServiceI bbsCommentService;

   @Autowired
   CommunityBbsServiceI communityBbsService;

   @Autowired
   UserServiceI userService;

    public Object createComment(@RequestBody BBSComment bbsComment){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(36000);
        returnJson.setReturnMessage("调用成功"+bbsComment.toString());
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
            returnJson.setErrorCode(34005);
            returnJson.setReturnMessage("communityBBS token不存在" + communityBBS.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = null;

        try {
            user = userService.selectByToken(communityBBS.getUserToken());
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
}
