package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.HouseUserMapper;
import rml.dao.UserMapper;
import rml.model.HouseUser;
import rml.model.User;
import rml.service.HouseServiceI;
import rml.service.HouseUserServiceI;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/26.
 */
@Service("houseUserService")
public class HouseUserServiceImpl implements HouseUserServiceI {

    @Autowired
    HouseUserMapper houseUserMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public int insertUser(HouseUser houseUser){
        houseUser.setCreateTime(new Date());
        return houseUserMapper.insertUser(houseUser);
    }

    @Override
    public List<HouseUser> getUsers(int houseId) {
        List<HouseUser> list = houseUserMapper.getUsers(houseId);
        return list;
    }

    @Override
    public HouseUser getHouseUser(HouseUser houseUser) {
        return houseUserMapper.getHouseUser(houseUser);
    }

    @Override
    public int deleteUser(HouseUser houseUser) {
        return houseUserMapper.deleteHouseUser(houseUser);
    }

    @Override
    public int updateUser(HouseUser houseUser) {
        return houseUserMapper.updateUser(houseUser);
    }

    @Override
    public List<HouseUser> getHouseUsers(int houseId) {
        return houseUserMapper.getHouseUsers(houseId);
    }


}