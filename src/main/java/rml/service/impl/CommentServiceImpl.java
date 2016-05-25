package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CommentMapper;
import rml.model.Comment;
import rml.service.CommentService;

import java.util.Date;

/**
 * Created by edward-echo on 2016/2/26.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public int createComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.createComment(comment);
    }
}
