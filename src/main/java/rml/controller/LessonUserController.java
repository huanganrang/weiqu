package rml.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.House;
import rml.model.Lesson;
import rml.model.LessonUser;
import rml.model.User;
import rml.service.HouseServiceI;
import rml.service.LessonServiceI;
import rml.service.LessonUserServiceI;
import rml.service.UserServiceI;
import rml.util.ReturnJson;

/**
 * Created by Administrator on 2015/10/5 0005.
 */

@Controller
@RequestMapping("/Lesson")
public class LessonUserController {

    @Autowired
    private LessonUserServiceI lessonUserService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private LessonServiceI lessonService;

    @RequestMapping(value="/User",method = RequestMethod.POST)
    @ResponseBody
    public Object createHouse(@RequestBody LessonUser lessonUser) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(25000);
        returnJson.setReturnMessage("调用成功"+lessonUser.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(lessonUser.getUserToken())||StringUtils.isEmpty(lessonUser.getLessonToken())){
            returnJson.setErrorCode(25001);
            returnJson.setReturnMessage("传入参数为空" + lessonUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user = userService.selectByToken(lessonUser.getUserToken());
        if(user==null){
            returnJson.setErrorCode(25002);
            returnJson.setReturnMessage("userToken不存在" + lessonUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Lesson lesson = lessonService.getLessonByToken(lessonUser.getLessonToken());
        if(user==null){
            returnJson.setErrorCode(25003);
            returnJson.setReturnMessage("lessonToken不存在" + lessonUser.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            lessonUser.setUserId(user.getId());
            lessonUser.setLessonId(lesson.getId());
            lessonUserService.createLessonUser(lessonUser);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(25004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(lessonUser);
        return returnJson;
    }


    @RequestMapping(value="/Users",method = RequestMethod.GET)
    @ResponseBody
    public Object getHouse(String token) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(27000);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(token)){
            returnJson.setErrorCode(27002);
            returnJson.setReturnMessage("传入参数为空" + token);
            returnJson.setServerStatus(1);
            return returnJson;
        }
        House house = null;
        try {
//            house = houseService.getHouse(token);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(27001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnMessage(house.toString());
        returnJson.setReturnObject(house);
        return returnJson;
    }
}
