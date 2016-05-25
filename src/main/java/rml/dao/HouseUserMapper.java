package rml.dao;

import rml.model.HouseUser;

import java.util.List;

/**
 * Created by Administrator on 2015/9/26.
 */
public interface HouseUserMapper {

    public List<HouseUser> getUsers(int houseId);

    public int insertUser(HouseUser houseUser);

    public HouseUser getHouseUser(HouseUser user);

    public int deleteHouseUser(HouseUser user);

    public int updateUser(HouseUser user);

    List<HouseUser> getHouseUsers(int houseId);

}
