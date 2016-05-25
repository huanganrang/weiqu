package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.dao.HouseFileMapper;
import rml.model.HouseFile;
import rml.service.HouseFileServiceI;
import rml.service.HouseServiceI;
import rml.service.UserServiceI;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
@Service("houseFileService")
public class HouseFileServiceImpl implements HouseFileServiceI {


    @Autowired
    HouseFileMapper houseFileMapper;


    @Override
    public int createHouseFile(HouseFile houseFile) {
        return houseFileMapper.createHouseFile(houseFile);
    }
}
