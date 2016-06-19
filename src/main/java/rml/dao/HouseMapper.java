package rml.dao;

import rml.model.Channel;
import rml.model.House;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public interface HouseMapper {
    public int createHouse(House house);

    public House getHouse(String token);

    public int updateHouse(House houHse);

    public House getHouseById(int id);

    int updateHouseUser(House house);

    int  minusHouseUser(House house);

    List<House> getChannelHouses(int channelId);

    House getHouseDetail(String token);

    List<House> getUserHouse(House House);
    int deleteHouseRole(House house);
    int insertHouseRole(House house);
}
