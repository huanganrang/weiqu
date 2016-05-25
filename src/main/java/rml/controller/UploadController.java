package rml.controller;

import com.chinanetcenter.api.util.EncodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.util.ReturnJson;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
@Controller
@RequestMapping("/Upload")
public class UploadController {
    @RequestMapping(value={"/Path/Decode"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public Object decodeSecret(String encode){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(55000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(encode)){
            returnJson.setErrorCode(55001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(0);
            return returnJson;
        }
        String value = "";
        try {
            byte[] bytes =  EncodeUtils.urlsafeBase64Decode(encode);
            value = new String(bytes,"UTF-8");
            int position = value.lastIndexOf("&");
            value = value.substring(0,position);
            value = value.substring(4,value.length());
        }catch (Exception ex){
            returnJson.setErrorCode(55002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        System.err.println(value);
        returnJson.setReturnValue(value);
        return returnJson;
    }


}
