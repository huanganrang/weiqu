package rml.dao;

import rml.model.Video;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
public interface VideoMapper {

     int createVideo(Video video);

     int updateVideo(Video video);

     Video getVideoInfo(String token);

     int deleteVideo(String token);

     List<Video> getHouseVideo(int houseId);

}
