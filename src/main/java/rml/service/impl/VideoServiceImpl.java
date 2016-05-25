package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.HouseMapper;
import rml.dao.UserMapper;
import rml.dao.VideoMapper;
import rml.model.House;
import rml.model.User;
import rml.model.Video;
import rml.service.VideoServiceI;
import rml.util.RandomGUID;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/20.
 */
@Service("videoService")
public class VideoServiceImpl implements VideoServiceI {


    @Autowired
    private VideoMapper videoMapper;


    @Override
    public Video createVideo(Video video) {
        video.setToken(UUID.randomUUID().toString());
        int n = videoMapper.createVideo(video);
        return video;
    }

    @Override
    public Video getVideoInfo(String token) {
        return videoMapper.getVideoInfo(token);
    }

    @Override
    public int deleteVideo(String token) {
        return videoMapper.deleteVideo(token);
    }

    @Override
    public List<Video> getHouseVideo(int houseId) {
        return videoMapper.getHouseVideo(houseId);
    }
}
