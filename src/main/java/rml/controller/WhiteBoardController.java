package rml.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.User;
import rml.model.Video;
import rml.model.WhiteBoard;
import rml.service.HouseServiceI;
import rml.service.UserServiceI;
import rml.service.VideoServiceI;
import rml.service.WhiteBoardServiceI;
import rml.util.ReturnJson;

/**
 * Created by Administrator on 2015/9/30.
 */

@Controller
@RequestMapping("/WhiteBoard")
public class WhiteBoardController {

    @Autowired
    WhiteBoardServiceI whiteBoardService;

    @Autowired
    UserServiceI userService;

    @Autowired
    HouseServiceI houseService;

    @RequestMapping(value="/WhiteBoard",method = RequestMethod.POST)
    @ResponseBody
    public Object createVideo(@RequestBody WhiteBoard video){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(14000);
        returnJson.setReturnMessage("调用成功" + video.toString());
        returnJson.setServerStatus(0);
        if(video.getHouseId()==0|| StringUtils.isEmpty(video.getName())||StringUtils.isEmpty(video.getUserToken())||StringUtils.isEmpty(video.getStartTime())){
            returnJson.setErrorCode(14002);
            returnJson.setReturnMessage("传入参数为空" + video.toString());
            returnJson.setServerStatus(1);
        }
        User user = userService.selectByToken(video.getUserToken());
        if(user==null){
            returnJson.setErrorCode(14003);
            returnJson.setReturnMessage("user token 不存在" + video.getUserToken());
            returnJson.setServerStatus(1);
        }
        video.setUserId(user.getId());
        try{
            video.setType(1);
            whiteBoardService.createVideo(video);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(14005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(1);
        }
        returnJson.setReturnObject(video);
        return returnJson;
    }

    @RequestMapping(value="/WhiteBoard",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideo(String token){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(15000);
        returnJson.setReturnMessage("调用成功 " + token);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(token)){
            returnJson.setErrorCode(15001);
            returnJson.setReturnMessage("调用成功 " + token);
            returnJson.setServerStatus(1);
        }
        WhiteBoard video = null;
        try{
            video = whiteBoardService.getVideoInfo(token);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(15002);
            returnJson.setReturnMessage("服务器错误 " + token);
            returnJson.setServerStatus(2);

        }
        returnJson.setReturnObject(video);
        return returnJson;
    }
}
