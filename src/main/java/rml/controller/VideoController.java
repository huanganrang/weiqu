package rml.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.House;
import rml.model.User;
import rml.model.Video;
import rml.service.HouseServiceI;
import rml.service.UserServiceI;
import rml.service.VideoServiceI;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
@Controller
@RequestMapping("/House")
public class VideoController {

    @Autowired
    VideoServiceI videoService;

    @Autowired
    HouseServiceI houseService;

    @RequestMapping(value="/Video",method = RequestMethod.POST)
          @ResponseBody
          public Object createVideo(@RequestBody Video video){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(50000);
        returnJson.setReturnMessage("调用成功" + video.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(video.getHouseToken())||StringUtils.isEmpty(video.getPullUrl())){
            returnJson.setErrorCode(50001);
            returnJson.setReturnMessage("传入参数为空" + video.toString());
            returnJson.setServerStatus(1);
        }
        House house = null;

        try{
            house = houseService.getHouse(video.getHouseToken());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(house==null){
            returnJson.setErrorCode(50002);
            returnJson.setReturnMessage("houseToken不存在" + video.toString());
            returnJson.setServerStatus(1);
        }
        Video video1 = null;
        try{
            video.setHouseId(house.getId());
            video.setStatus(0);
            video1 =  videoService.createVideo(video);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(50003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(1);
        }
        returnJson.setReturnValue(video.getToken());
        returnJson.setReturnObject(video1);
        return returnJson;
    }

    @RequestMapping(value="/Videos",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideo(String houseToken){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(52000);
        returnJson.setReturnMessage("调用成功 " + houseToken);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(houseToken)){
            returnJson.setErrorCode(52001);
            returnJson.setReturnMessage("传入参数为空 " + houseToken);
            returnJson.setServerStatus(1);
        }
        House house = null;

        try{
            house = houseService.getHouse(houseToken);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if(house==null){
            returnJson.setErrorCode(52002);
            returnJson.setReturnMessage("houseToken不存在" + houseToken);
            returnJson.setServerStatus(1);
        }
        List<Video> videos = new ArrayList<Video>();
        try{
           videos =  videoService.getHouseVideo(house.getId());
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(52003);
            returnJson.setReturnMessage("服务器错误 " + houseToken);
            returnJson.setServerStatus(2);

        }
        returnJson.setReturnObject(videos);
        return returnJson;
    }





    @RequestMapping(value="/Video/Close",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteVideo(String videoToken){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(51000);
        returnJson.setReturnMessage("调用成功 " + videoToken);
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(videoToken)){
            returnJson.setErrorCode(51001);
            returnJson.setReturnMessage("传入参数为空" + videoToken);
            returnJson.setServerStatus(1);
        }
        try{
            videoService.deleteVideo(videoToken);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(51002);
            returnJson.setReturnMessage("服务器错误 " + videoToken);
            returnJson.setServerStatus(2);

        }
        returnJson.setReturnValue(videoToken);
        return returnJson;
    }
}
