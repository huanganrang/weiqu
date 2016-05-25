package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.LessonCommentMapper;
import rml.model.Lesson;
import rml.model.LessonComment;
import rml.service.LessonCommentServiceI;
import rml.util.RandomGUID;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
@Service("lessonCommentService")
public class LessonCommentServiceImpl implements LessonCommentServiceI {

    @Autowired
    LessonCommentMapper lessonCommentMapper;

    @Override
    public LessonComment createLessonComment(LessonComment lessonComment) {
         lessonComment.setToken(UUID.randomUUID().toString());
         lessonCommentMapper.createComment(lessonComment);
         return lessonComment;
    }

    @Override
    public LessonComment getCommentById(int id) {
        return lessonCommentMapper.getCommentById(id);
    }

    @Override
    public LessonComment getCommentByToken(String token) {
        return null;
    }

    @Override
    public List<LessonComment> getLessonComments(LessonComment lessonComment) {
        return lessonCommentMapper.getLessonComments(lessonComment);
    }

    @Override
    public int deleteComment(LessonComment lessonComment) {
        return lessonCommentMapper.deleteComment(lessonComment);
    }

    @Override
    public List<LessonComment> getLessonCommentsTotal(LessonComment lessonComment) {
        return lessonCommentMapper.getLessonCommentsTotal(lessonComment);
    }
}
