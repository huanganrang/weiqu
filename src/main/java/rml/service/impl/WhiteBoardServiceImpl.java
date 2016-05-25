package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.VideoMapper;
import rml.dao.WhiteBoardMapper;
import rml.model.Video;
import rml.model.WhiteBoard;
import rml.service.VideoServiceI;
import rml.service.WhiteBoardServiceI;
import rml.util.RandomGUID;

/**
 * Created by Administrator on 2015/9/20.
 */
@Service("whiteBoardService")
public class WhiteBoardServiceImpl implements WhiteBoardServiceI {

    RandomGUID guid = new RandomGUID(true);

    private String pushUrl =  "s1.weiqu168.com/live/";
    private String pullUrl =  "s2.weiqu168.com/live/";

    @Autowired
    private WhiteBoardMapper videoMapper;

    @Override
    public WhiteBoard createVideo(WhiteBoard video) {
        video.setPushUrl(pushUrl + video.getName());
        video.setPullUrl(pullUrl + video.getName());
        video.setToken(guid.valueAfterMD5);
        int n = videoMapper.createVideo(video);
        return video;
    }

    @Override
    public WhiteBoard getVideoInfo(String token) {
        return videoMapper.getVideoInfo(token);
    }
}
