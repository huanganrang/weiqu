package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rml.dao.HouseMapper;
import rml.model.Channel;
import rml.model.House;
import rml.service.HouseServiceI;
import rml.util.MD5;
import rml.util.RandomGUID;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/20.
 */
@Service("houseService")
public class HouseServiceImpl implements HouseServiceI {

    @Autowired
    HouseMapper houseMapper;


    @Override
    public House createHouse(House house) {
        house.setToken(UUID.randomUUID().toString());
        houseMapper.createHouse(house);
        return house;
    }
    private Date convertDate(String date){
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date returnValue = null;
        try {
            returnValue = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    @Override
    public House getHouse(String token) {
       House house = houseMapper.getHouse(token);
       return house;
    }

    @Override
    public House getHouseById(int id) {
        return houseMapper.getHouseById(id);
    }

    @Override
    public int updateHouseUser(House house) {
        return houseMapper.updateHouseUser(house);
    }

    @Override
    public List<House> getChannelHouses(int channelId) {
        return houseMapper.getChannelHouses(channelId);
    }

    @Override
    public House getHouseDetail(String token) {
        return houseMapper.getHouseDetail(token);
    }

    @Override
    public int minusHouseUser(House house) {
        return houseMapper.minusHouseUser(house);
    }

    @Override
    public List<House> getUserHouse(House House) {
        return houseMapper.getUserHouse(House);
    }

    @Override
    public int updateHouse(House house) {
        return houseMapper.updateHouse(house);
    }
}
