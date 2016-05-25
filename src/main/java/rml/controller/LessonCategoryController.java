package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.LessonCategory;
import rml.service.LessonCategoryServiceI;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/5 0005.
 */

@Controller
@RequestMapping("/LessonCategory")
public class LessonCategoryController {

    @Autowired
    LessonCategoryServiceI LessonCategoryService;

    @RequestMapping(value="/LessonCategory",method = RequestMethod.GET)
    @ResponseBody
    public Object getLessonCategory(){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(18000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<LessonCategory> list = new ArrayList<LessonCategory>();
        try {
            list = LessonCategoryService.getCategory();
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(18001);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(list);
        return returnJson;

    }
}
