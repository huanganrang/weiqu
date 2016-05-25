package rml.dao;

import rml.model.Lesson;
import rml.model.LessonComment;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public interface LessonCommentMapper {
    public int createComment(LessonComment lessonComment);

    public LessonComment getCommentById(int id);

    public LessonComment getCommentByToken(String token);

    public List<LessonComment> getLessonComments(LessonComment lessonComment);

    int deleteComment(LessonComment lessonComment);

    List<LessonComment> getLessonCommentsTotal(LessonComment lessonComment);
}
