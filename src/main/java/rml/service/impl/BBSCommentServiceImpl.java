package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.BBSCommentMapper;
import rml.model.BBSComment;
import rml.service.BBSCommentServiceI;
import rml.util.RandomGUID;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/10/11 0011.
 */
@Service("bbsCommentService")
public class BBSCommentServiceImpl implements BBSCommentServiceI {

    @Autowired
    BBSCommentMapper bbsCommentMapper;

    @Override
    public int createComment(BBSComment bbsComment) {
        RandomGUID guid = new RandomGUID(true);
        bbsComment.setToken(guid.valueAfterMD5);
        return  bbsCommentMapper.createComment(bbsComment);
    }

    @Override
    public List<BBSComment> getBbsComments(BBSComment BBSComment) {
       return  bbsCommentMapper.getBbsComments(BBSComment);
    }

    @Override
    public BBSComment getCommentByToken(String token) {
       BBSComment bbsComment = bbsCommentMapper.getCommentByToken(token);
       return bbsComment;
    }

    @Override
    public List<BBSComment> getBbsCommentsTotal(BBSComment bbsComment) {
        return bbsCommentMapper.getBbsCommentsTotal(bbsComment);
    }
}
