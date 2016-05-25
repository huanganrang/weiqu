package rml.service;

import rml.model.Video;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
public interface VideoServiceI {

    public Video createVideo(Video video);

    public Video getVideoInfo(String token);

    int deleteVideo(String token);

    List<Video> getHouseVideo(int houseId);

}
