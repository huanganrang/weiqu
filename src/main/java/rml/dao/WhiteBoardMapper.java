package rml.dao;

import rml.model.WhiteBoard;

/**
 * Created by Administrator on 2015/9/20.
 */
public interface WhiteBoardMapper {
     int createVideo(WhiteBoard video);

     WhiteBoard getVideoInfo(String token);

}
