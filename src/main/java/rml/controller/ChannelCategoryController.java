package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.ChannelCategory;
import rml.service.ChannelCategoryServiceI;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
@Controller
@RequestMapping("/Category")
public class ChannelCategoryController {

    @Autowired
    ChannelCategoryServiceI channelCategoryService;

    @RequestMapping(value="/Category",method = RequestMethod.GET)
    @ResponseBody
    public Object getChannelCategory(){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<ChannelCategory> list = new ArrayList<ChannelCategory>();
        try {
            list = channelCategoryService.getCategory();
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(list);
        return returnJson;

    }
}
