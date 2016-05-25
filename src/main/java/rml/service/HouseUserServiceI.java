package rml.service;

import rml.model.HouseUser;

import java.util.List;

/**
 * Created by Administrator on 2015/9/26.
 */
public interface HouseUserServiceI  {

    public int insertUser(HouseUser houseUser);

    public List<HouseUser> getUsers(int houseId);

    public HouseUser getHouseUser(HouseUser houseUser);

    public int deleteUser(HouseUser houseUser);

    public int updateUser(HouseUser houseUser);

    List<HouseUser> getHouseUsers(int houseId);

}
