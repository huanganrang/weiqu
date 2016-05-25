package rml.service;

import rml.model.Video;
import rml.model.WhiteBoard;

/**
 * Created by Administrator on 2015/9/20.
 */
public interface WhiteBoardServiceI {

    public WhiteBoard createVideo(WhiteBoard video);

    public WhiteBoard getVideoInfo(String token);
}
