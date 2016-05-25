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

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/10/5 0005.
 */

@Controller
@RequestMapping("/Lesson")
public class LessonController {

    @Autowired
    private LessonServiceI lessonService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private CommunityServiceI communityService;

    @Autowired
    private LessonCategoryServiceI lessonCategoryService;

    @Autowired
    LessonCommentServiceI lessonCommentService;

    @Autowired
    LessonGoodService lessonGoodService;

    @RequestMapping(value="/Lesson",method = RequestMethod.POST)
    @ResponseBody
    public Object createLesson(@RequestBody Lesson lesson) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(19000);
        returnJson.setReturnMessage("调用成功" + lesson.toString());
        returnJson.setServerStatus(0);
        if(lesson.getType()==0||StringUtils.isEmpty(lesson.getTitle())||StringUtils.isEmpty(lesson.getFile())||StringUtils.isEmpty(lesson.getLdesc())||StringUtils.isEmpty(lesson.getCommunityToken())||StringUtils.isEmpty(lesson.getUserToken())){
            returnJson.setErrorCode(19002);
            returnJson.setReturnMessage("传入参数为空" + lesson.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = userService.selectByToken(lesson.getUserToken());
        if(user==null){
            returnJson.setErrorCode(19003);
            returnJson.setReturnMessage("userToken不存在" + lesson.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Community community = communityService.getCommunityByToken(lesson.getCommunityToken());
        if(community==null){
            returnJson.setErrorCode(19004);
            returnJson.setReturnMessage("communityToken不存在" + lesson.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            lesson.setUserId(user.getId());
            lesson.setCommunityId(community.getId());
            lesson.setCreateTime(new Date());
            if(lesson.getIcon()==null){
                lesson.setIcon(community.getDefaultImg());
            }
             lessonService.createLesson(lesson);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(19005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(lesson);
        return returnJson;
    }

    @RequestMapping(value="/Lesson",method = RequestMethod.GET)
    @ResponseBody
    public Object getLesson(String lessonToken,String userToken) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(20000);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(userToken)||StringUtils.isEmpty(lessonToken)){
            returnJson.setErrorCode(20002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Lesson lesson = null;
        try {
            User user = userService.selectByToken(userToken);
            lesson = lessonService.getLessonByToken(lessonToken);
            LessonGood lessonGood = new LessonGood();
            lessonGood.setLessonId(lesson.getId());
            lessonGood.setUserId(user.getId());
            LessonGood lessonGood1 = lessonGoodService.getLessonGood(lessonGood);
            Community community = communityService.getCommunityById(lesson.getCommunityId());
            lesson.setCommunityToken(community.getToken());
            lesson.setUserToken(user.getToken());
            if(lessonGood1!=null){
                lesson.setIsGood(1);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(20003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnMessage(lesson.toString());
        returnJson.setReturnObject(lesson);
        return returnJson;
    }


    @RequestMapping(value="/Lessons",method = RequestMethod.GET)
    @ResponseBody
    public Object getLessons(Lesson lesson) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(21000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(lesson.getCommunityToken())||lesson.getPageNo()==0||lesson.getPageSize()==0){
            returnJson.setErrorCode(21001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Community community = communityService.getCommunityByToken(lesson.getCommunityToken());
        if(community==null){
            returnJson.setErrorCode(21003);
            returnJson.setReturnMessage("communityToken不存在");
            returnJson.setServerStatus(1);
            return returnJson;
        }
       List<Lesson>  lessons = null;
       List<Lesson>  values = new ArrayList<Lesson>();
        try {
            lesson.setCommunityId(community.getId());
            lessons = lessonService.getLessonByCommunity(lesson);
            for(Lesson lesson1:lessons){
                lesson1.setCommunityToken(community.getToken());
                User user = userService.selectByPrimaryKey(lesson1.getUserId());
                lesson1.setUserToken(user.getToken());
                values.add(lesson1);
            }
        returnJson.setReturnValue(new Integer(lessonService.getLessonByCommunityTotal(lesson).size()).toString());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(21004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(values);
        return returnJson;
    }



    @RequestMapping(value="/Comments",method = RequestMethod.GET)
    @ResponseBody
    public Object getLessonComments(LessonComment lessonComment) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(24000);
        returnJson.setServerStatus(0);
        returnJson.setReturnMessage("调用成功");
        if(StringUtils.isEmpty(lessonComment.getLessonToken())||lessonComment.getPageSize()==0||lessonComment.getPageNo()==0){
            returnJson.setErrorCode(24002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Lesson lesson = lessonService.getLessonByToken(lessonComment.getLessonToken());
        if(lesson==null){
            returnJson.setErrorCode(24003);
            returnJson.setReturnMessage("lesson token不存在");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<LessonComment> comments = null;
        try {
            lessonComment.setLessonId(lesson.getId());
            comments = lessonCommentService.getLessonComments(lessonComment);
            for(LessonComment comment:comments){
                User user = userService.selectByPrimaryKey(comment.getUserId());
                comment.setUserName(user.getNickName());
                comment.setUserToken(user.getToken());
                comment.setLessonToken(lesson.getToken());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(24005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnValue(new Integer(lessonCommentService.getLessonCommentsTotal(lessonComment).size()).toString());
        returnJson.setReturnObject(comments);
        return returnJson;
    }

    @RequestMapping(value="/Comment",method = RequestMethod.POST)
    @ResponseBody
    public Object createComment(@RequestBody LessonComment comment) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(23000);
        returnJson.setReturnMessage("调用成功" + comment.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(comment.getUserToken())||StringUtils.isEmpty(comment.getLessonToken())){

            returnJson.setErrorCode(23002);
            returnJson.setReturnMessage("传入参数为空" + comment.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = userService.selectByToken(comment.getUserToken());
        if(user==null){
            returnJson.setErrorCode(23004);
            returnJson.setReturnMessage("userToken不存在" + comment.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Lesson lesson = lessonService.getLessonByToken(comment.getLessonToken());
        if(lesson==null){
            returnJson.setErrorCode(23005);
            returnJson.setReturnMessage("lessonToken不存在");
            returnJson.setServerStatus(1);
            return returnJson;
        }

        LessonComment comment1 = new LessonComment();
        try {
            comment.setUserId(user.getId());
            comment.setLessonId(lesson.getId());
            comment.setUserName(user.getNickName());
            comment.setCreateTime(new Date());
            comment1 = lessonCommentService.createLessonComment(comment);
            lessonService.updateLessonComment(lesson.getId());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(23006);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(comment1);
        return returnJson;
    }


    @RequestMapping(value="/Comment/Delete",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteComment(String token) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(29000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(token)){

            returnJson.setErrorCode(29002);
            returnJson.setReturnMessage("传入参数为空" + token);
            returnJson.setServerStatus(1);
            return returnJson;
        }

        try {
            LessonComment comment = new LessonComment();
            comment.setToken(token);
            lessonCommentService.deleteComment(comment);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(29003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }



    @RequestMapping(value="/Comment",method = RequestMethod.GET)
    @ResponseBody
    public Object getComments(String token) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(24000);
        returnJson.setServerStatus(0);
        if (StringUtils.isEmpty(token)) {
            returnJson.setErrorCode(24002);
            returnJson.setReturnMessage("传入参数为空" + token);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        LessonComment comment = null;
        try {
            comment = lessonCommentService.getCommentByToken(token);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(24001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(comment);
        return returnJson;
    }


    @RequestMapping(value="/Good",method = RequestMethod.POST)
    @ResponseBody
    public Object createGood(@RequestBody LessonGood lessonGood) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(25000);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(lessonGood.getUserToken())||StringUtils.isEmpty(lessonGood.getLessonToken())){
            returnJson.setErrorCode(25002);
            returnJson.setReturnMessage("传入参数为空" + lessonGood);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = userService.selectByToken(lessonGood.getUserToken());
        if(user==null){
            returnJson.setErrorCode(25003);
            returnJson.setReturnMessage("userToken不存在" + lessonGood);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Lesson lesson = lessonService.getLessonByToken(lessonGood.getLessonToken());
        if(lesson==null){
            returnJson.setErrorCode(25004);
            returnJson.setReturnMessage("lessonToken不存在" + lessonGood);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            lessonGood.setUserId(user.getId());
            lessonGood.setLessonId(lesson.getId());
            lessonGood.setCreateTime(new Date());
            lessonGood.setToken(UUID.randomUUID().toString());
            LessonGood good =  lessonGoodService.getLessonGood(lessonGood);
            if(good!=null){
                returnJson.setErrorCode(25006);
                returnJson.setReturnMessage("请勿重复点赞");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            lessonGoodService.createLessonGood(lessonGood);
            lessonService.updateLessonUpNum(lesson.getId());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(25005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnValue(lessonGood.getToken());
        return returnJson;
    }

    @RequestMapping(value="/Good/Cancel",method = RequestMethod.GET)
    @ResponseBody
    public Object cancelGood(String userToken,String lessonToken) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(26000);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(userToken)||StringUtils.isEmpty(lessonToken)){
            returnJson.setErrorCode(26002);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByToken(userToken);
            if(user==null){
                returnJson.setErrorCode(25003);
                returnJson.setReturnMessage("userToken不存在" + userToken);
                returnJson.setServerStatus(1);
                return returnJson;
            }
            Lesson lesson = lessonService.getLessonByToken(lessonToken);
            if(lesson==null){
                returnJson.setErrorCode(25004);
                returnJson.setReturnMessage("lessonToken不存在" + lessonToken);
                returnJson.setServerStatus(1);
                return returnJson;
            }
            LessonGood lessonGood = new LessonGood();
            lessonGood.setUserId(user.getId());
            lessonGood.setLessonId(lesson.getId());
            lessonGoodService.cancelLessonGood(lessonGood);
            lessonService.cancelLessonGood(lessonGood.getId());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(26005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }

}
