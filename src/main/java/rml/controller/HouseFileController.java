package rml.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import rml.model.File;
import rml.model.House;
import rml.model.HouseFile;
import rml.model.User;
import rml.service.HouseFileServiceI;
import rml.service.HouseServiceI;
import rml.service.UserServiceI;
import rml.util.ReturnJson;
import rml.util.SerializeUtil;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/22 0022.
 */

@Controller
@RequestMapping("/HouseFile")
public class HouseFileController {

    @Autowired
    private UserServiceI userService;

    @Autowired
    private HouseServiceI houseService;

    @Autowired
    private HouseFileServiceI houseFileService;

    @Autowired
    RedisTemplate jedisTemplate;

    static String constr = "139.196.12.99" ;

    private Jedis jedis = new Jedis(constr) ;

    @RequestMapping(value="/HouseFile",method = RequestMethod.POST)
    @ResponseBody
    public Object createHouseFile(@RequestBody HouseFile houseFile){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(45000);
        returnJson.setReturnMessage("调用成功" + houseFile.toString());
        returnJson.setServerStatus(0);

        if(StringUtils.isEmpty(houseFile.getUserToken())||StringUtils.isEmpty(houseFile.getHouseToken())||StringUtils.isEmpty(houseFile.getToken())||houseFile.getType()==0){
            returnJson.setErrorCode(45002);
            returnJson.setReturnMessage("传入参数为空" + houseFile.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        User user =null;
        try{
            user = userService.selectByToken(houseFile.getUserToken());
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(45003);
            returnJson.setReturnMessage("服务器错误" + houseFile.toString());
            returnJson.setServerStatus(2);

        }
        House house = null;
        try{
           house =  houseService.getHouse(houseFile.getHouseToken());
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(45003);
            returnJson.setReturnMessage("服务器错误" + houseFile.toString());
            returnJson.setServerStatus(2);

        }
        houseFile.setHouseId(house.getId());
        houseFile.setUserId(user.getId());
        houseFile.setCreateTime(new Date());
        rml.model.File file = (rml.model.File)SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize(houseFile.getToken())));
        file.setName(file.getName());
        try{
            houseFileService.createHouseFile(houseFile);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(45003);
            returnJson.setReturnMessage("服务器错误" + houseFile.toString());
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


}
