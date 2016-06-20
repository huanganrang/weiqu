package rml.service;

import rml.model.Channel;
import rml.model.House;
import rml.model.Role;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
public interface HouseServiceI {
    public House createHouse(House house);

    public House getHouse(String token);

    public House getHouseById(int id);

    int updateHouseUser(House house);

    List<House> getChannelHouses(int channelId);

    House getHouseDetail(String token);

    int  minusHouseUser(House house);

    List<House> getUserHouse(House House);

    public int updateHouse(House houHse);

    void updateHouseRole(House house, List<Role> roleList);
}
